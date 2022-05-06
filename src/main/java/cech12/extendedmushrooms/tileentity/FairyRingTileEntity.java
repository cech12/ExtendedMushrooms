package cech12.extendedmushrooms.tileentity;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.api.recipe.ExtendedMushroomsRecipeTypes;
import cech12.extendedmushrooms.api.recipe.FairyRingMode;
import cech12.extendedmushrooms.api.recipe.FairyRingRecipe;
import cech12.extendedmushrooms.api.tileentity.ExtendedMushroomsTileEntities;
import cech12.extendedmushrooms.block.FairyRingBlock;
import cech12.extendedmushrooms.init.ModParticles;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Random;

public class FairyRingTileEntity extends TileEntity implements IInventory, ITickableTileEntity {

    public static final Vector3d CENTER_TRANSLATION_VECTOR = new Vector3d(1, 0, 1);
    public static final int INVENTORY_SIZE = 16;

    private static final int EFFECT_EVENT = 0;

    private boolean hasMaster;
    private boolean isMaster;
    private BlockPos masterPos;

    private NonNullList<ItemStack> items = NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);
    private FairyRingMode mode = FairyRingMode.NORMAL;
    private int recipeTime;
    private int recipeTimeTotal;

    //recipe caching
    protected FairyRingRecipe currentRecipe;

    public FairyRingTileEntity() {
        super(ExtendedMushroomsTileEntities.FAIRY_RING);
        this.hasMaster = false;
        this.isMaster = false;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        //use onLoad only on server (setup of clients happens via nbt sync)
        if (this.getWorld() != null && !this.getWorld().isRemote) {
            //if master value is already set, do nothing
            if (this.isMaster() || this.hasMaster()) {
                this.loadRecipe();
                return;
            }
            //initial loading of this tile entity.
            //search for a master, when not found, I am the master
            World world = this.getWorld();
            BlockPos pos = this.getPos();
            if (world != null) {
                for (Direction direction : FairyRingBlock.DIRECTIONS) {
                    TileEntity tileEntity = world.getTileEntity(pos.offset(direction));
                    if (tileEntity instanceof FairyRingTileEntity) {
                        this.setMaster(((FairyRingTileEntity) tileEntity).getMaster());
                        break;
                    }
                }
            }
            if (!this.hasMaster()) {
                this.setAsMaster();
            }
        }
    }

    /**
     * Should only be called by master!
     */
    public Vector3d getCenter() {
        BlockPos position = this.getPos();
        return new Vector3d(position.getX(), position.getY(), position.getZ()).add(CENTER_TRANSLATION_VECTOR);
    }

    public boolean hasMaster() {
        return hasMaster;
    }

    public boolean isMaster() {
        return isMaster;
    }

    public void setMaster(FairyRingTileEntity tileEntity) {
        this.isMaster = false;
        this.hasMaster = true;
        this.masterPos = tileEntity.getPos();
        this.sendUpdates();
    }

    public void setAsMaster() {
        this.isMaster = true;
        this.hasMaster = false;
        this.masterPos = this.getPos();
        this.sendUpdates();
    }

    public FairyRingTileEntity getMaster() {
        if (this.isMaster()) {
            return this;
        }
        if (this.hasMaster() && this.getWorld() != null) {
            TileEntity tileEntity = this.getWorld().getTileEntity(this.masterPos);
            if (tileEntity instanceof FairyRingTileEntity) {
                return ((FairyRingTileEntity) tileEntity);
            }
        }
        return null;
    }

    @Override
    public void read(@Nonnull BlockState state, @Nonnull CompoundNBT compound) {
        super.read(state, compound);
        this.masterPos = new BlockPos(compound.getInt("MasterX"), compound.getInt("MasterY"), compound.getInt("MasterZ"));
        this.hasMaster = compound.getBoolean("HasMaster");
        this.isMaster = compound.getBoolean("IsMaster");
        if (this.isMaster()) {
            this.items = NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);
            ItemStackHelper.loadAllItems(compound, this.items);
            this.mode = FairyRingMode.byName(compound.getString("Mode"));
            this.recipeTime = compound.getInt("RecipeTime");
            this.recipeTimeTotal = compound.getInt("RecipeTimeTotal");
        }
    }

    @Override
    @Nonnull
    public CompoundNBT write(@Nonnull CompoundNBT compound) {
        super.write(compound);
        compound.putInt("MasterX", this.masterPos.getX());
        compound.putInt("MasterY", this.masterPos.getY());
        compound.putInt("MasterZ", this.masterPos.getZ());
        compound.putBoolean("HasMaster", hasMaster);
        compound.putBoolean("IsMaster", isMaster);
        if (this.isMaster()) {
            ItemStackHelper.saveAllItems(compound, this.items);
            compound.putString("Mode", this.mode.getName());
            compound.putInt("RecipeTime", this.recipeTime);
            compound.putInt("RecipeTimeTotal", this.recipeTimeTotal);
        }
        return compound;
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.getPos(), 3, this.getUpdateTag());
    }

    @Nonnull
    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        if (this.world != null) {
            this.read(this.world.getBlockState(pkt.getPos()), pkt.getNbtCompound());
        }
    }

    /**
     * Informs clients about state changes of this tile entity.
     * Should be called every time when a state value is updated. (in setters)
     */
    private void sendUpdates() {
        if (this.getWorld() != null) {
            BlockState state = this.getWorld().getBlockState(this.getPos());
            this.getWorld().notifyBlockUpdate(this.getPos(), state, state, 3);
        }
    }

    /**
     * Collect Item Entities.
     */
    public void onEntityCollision(Entity entity) {
        if (!this.isMaster()) {
            FairyRingTileEntity master = this.getMaster();
            if (master != null) {
                master.onEntityCollision(entity);
            }
        } else {
            if (entity instanceof ItemEntity) {
                this.onItemEntityCollision((ItemEntity) entity);
            } else if (entity instanceof PlayerEntity) {
                //Give entering player all stored items.
                PlayerEntity playerEntity = (PlayerEntity) entity;
                for (int i = 0; i < this.getSizeInventory(); i++) {
                    ItemStack stack = this.getStackInSlot(i);
                    if (playerEntity.inventory.addItemStackToInventory(stack)) {
                        this.decrStackSize(i, stack.getCount());
                    }
                }
                boolean dirty = false;
                // reset mode
                if (this.mode != FairyRingMode.NORMAL) {
                    this.mode = FairyRingMode.NORMAL;
                    dirty = true;
                }
                // check and update recipe
                dirty |= this.updateRecipe();
                if (dirty) {
                    this.sendUpdates();
                }
            }
        }
    }

    private void onItemEntityCollision(ItemEntity itemEntity) {
        //Collect Item Entities.
        ItemStack remainingStack = this.addItemStack(itemEntity.getItem());
        if (remainingStack == ItemStack.EMPTY) {
            //when fully added, remove entity
            itemEntity.remove();
        } else {
            //when not or partly added, set new stack
            itemEntity.setItem(remainingStack);
            //itemEntity shouldn't stay inside of FairyRingTileEntity (performance issue)
            //so, push remaining stack to border.
            Vector3d centerToStack = itemEntity.getPositionVec().subtract(this.getCenter());
            double scaleFactor = (1.8 - centerToStack.length()) * 0.08; //1.8 is sqrt(3) | 0.08 is speed
            Vector3d calculatedMotion = new Vector3d(centerToStack.x, 0, centerToStack.z).normalize().scale(scaleFactor);
            itemEntity.setMotion(itemEntity.getMotion().add(calculatedMotion));
        }

        // check and update recipe
        if (this.updateRecipe()) {
            this.sendUpdates();
        }
    }

    /**
     * Loads recipe. Should only be called in onLoad method.
     * Hint: Sends no updates to client.
     */
    protected void loadRecipe() {
        if (this.isMaster()) {
            this.currentRecipe = this.getRecipe();
            if (this.currentRecipe == null) {
                this.recipeTime = 0;
                this.recipeTimeTotal = 0;
            }
        }
    }

    /**
     * reset cached recipe and recipe times.
     * Hint: Sends no updates to client.
     */
    protected void resetRecipe() {
        this.currentRecipe = null;
        this.recipeTime = 0;
        this.recipeTimeTotal = 0;
    }

    /**
     * Updates the current recipe.
     * Hint: Sends no updates to client.
     * @return true when recipe changed. false otherwise.
     */
    protected boolean updateRecipe() {
        if (this.isMaster()) {
            FairyRingRecipe oldRecipe = this.currentRecipe;
            FairyRingRecipe newRecipe = this.getRecipe();
            if (oldRecipe != newRecipe) {
                this.recipeTime = 0;
                if (newRecipe != null) {
                    this.recipeTimeTotal = newRecipe.getRecipeTime();
                } else {
                    this.recipeTimeTotal = 0;
                }
                this.currentRecipe = newRecipe;
                return true;
            }
        }
        return false;
    }

    /**
     * Get valid recipe related to items in inventory.
     * Cached recipe is used but not set in this method. For updating recipe cache use updateRecipe() method.
     * @return a valid FairyRingRecipe or null when no valid recipe exist.
     */
    protected FairyRingRecipe getRecipe() {
        if (!this.isMaster() || this.getWorld() == null || this.isEmpty()) {
            return null;
        }
        if (this.currentRecipe != null && this.currentRecipe.isValid(this.mode, this)) {
            return this.currentRecipe;
        } else {
            Collection<IRecipe<?>> recipes = ExtendedMushrooms.getRecipes(ExtendedMushroomsRecipeTypes.FAIRY_RING, this.getWorld().getRecipeManager()).values();
            for (IRecipe<?> recipe : recipes) {
                if (recipe instanceof FairyRingRecipe && ((FairyRingRecipe) recipe).isValid(this.mode, this)) {
                    return (FairyRingRecipe) recipe;
                }
            }
        }
        return null;
    }

    @Override
    public void tick() {
        boolean dirty = false;
        if (this.isMaster() && this.getWorld() != null && !this.getWorld().isRemote) {
            FairyRingRecipe recipe = this.getRecipe();
            if (recipe != null) {
                //increase recipe time
                if (this.recipeTime < this.recipeTimeTotal) {
                    this.recipeTime++;
                    dirty = true;
                }
                //detect finished recipe
                if (this.recipeTime >= this.recipeTimeTotal) {
                    //update fairy ring mode
                    if (this.mode != this.currentRecipe.getResultMode()) {
                        this.mode = this.currentRecipe.getResultMode();
                    }
                    //clear inventory and pop out result itemStack
                    this.clear();
                    Vector3d center = this.getCenter();
                    ItemStack resultStack = this.currentRecipe.getResultItemStack();
                    if (resultStack != null && resultStack != ItemStack.EMPTY) {
                        ItemEntity itemEntity = new ItemEntity(this.getWorld(), center.x, center.y + 1.1, center.z, resultStack);
                        itemEntity.setMotion(new Vector3d(0, 0.2, 0));
                        this.getWorld().addEntity(itemEntity);
                    }
                    //reset and update recipe
                    this.resetRecipe();
                    this.updateRecipe();
                    dirty = true;
                }
            }
        }
        if (dirty) {
            this.sendUpdates();
        }
    }

    /**
     * Called periodically client side by FairyRingBlocks near the player to show effects.
     */
    @OnlyIn(Dist.CLIENT)
    public void animateTick(World worldIn, Random rand) {
        if (this.isMaster()) {
            Vector3d center = this.getCenter();
            boolean runningRecipe = this.recipeTime < this.recipeTimeTotal;
            if (this.mode == FairyRingMode.NORMAL && !runningRecipe) {
                //normal mode without recipe process: some mycelium particles on ground
                for (int i = 0; i < 4; i++) {
                    worldIn.addParticle(ParticleTypes.MYCELIUM, -1.5 + center.x + rand.nextFloat() * 3, center.y + 0.1F, -1.5 + center.z + rand.nextFloat() * 3, 0, 0, 0);
                }
            } else {
                //normal mode with recipe process or fairy/witch mode: white mycelium particles on ground
                for (int i = 0; i < 4; i++) {
                    worldIn.addParticle(ModParticles.FAIRY_RING, -1.5 + center.x + rand.nextFloat() * 3, center.y, -1.5 + center.z + rand.nextFloat() * 3, (rand.nextDouble() - 0.5) * 0.001, 0.0005, (rand.nextDouble() - 0.5) * 0.001);
                }
            }

            if (this.mode == FairyRingMode.NORMAL && runningRecipe) {
                //normal mode with recipe process: in center some sprinkling white particles
                for (int i = 0; i < 4; i++) {
                    worldIn.addParticle(ModParticles.FAIRY_RING, center.x, center.y, center.z, (rand.nextDouble() - 0.5) * 0.05, 0.05D + rand.nextDouble() * 0.05, (rand.nextDouble() - 0.5) * 0.05);
                }
            }

            if (this.mode != FairyRingMode.NORMAL) {
                //fairy/witch mode outer ring with high white particles (with recipe higher)
                double factor = 1;
                if (runningRecipe) {
                    factor = 2;
                }
                int particleCount = 12;
                double anglePerParticle = 360.0D / particleCount;
                double variance = anglePerParticle / 2;
                for (int i = 0; i < particleCount; i++) {
                    double rad = Math.toRadians(anglePerParticle * i + (rand.nextDouble() - variance / 2) * variance);
                    double x = 1.5 * Math.cos(rad) + center.x;
                    double z = 1.5 * Math.sin(rad) + center.z;
                    worldIn.addParticle(ModParticles.FAIRY_RING, x, center.y, z, 0, 0.05D * factor, 0);
                }
            }
        }
    }

    /**
     * Is called by client when server sends a block event via World#addBlockEvent.
     * @return Should return true, when event is correct and has an effect.
     */
    @Override
    public boolean receiveClientEvent(int id, int param) {
        //in tick() method
        //this.getWorld().addBlockEvent(this.getPos(), ExtendedMushroomsBlocks.FAIRY_RING, EFFECT_EVENT, 0);
        if (id == EFFECT_EVENT) {
            if (this.getWorld() != null && this.getWorld().isRemote) {
                Vector3d center = this.getCenter();
                //TODO some nice effects!
                this.getWorld().addParticle(ParticleTypes.MYCELIUM, center.x, center.y, center.z, 0.0D, 0.0D, 0.0D);
            }
            return true;
        }
        return super.receiveClientEvent(id, param);
    }

    public FairyRingMode getMode() {
        return this.mode;
    }

    public int getRecipeTime() {
        return this.recipeTime;
    }

    public int getRecipeTimeTotal() {
        return this.recipeTimeTotal;
    }

    /**
     *
     * @param stack ItemStack which should be added.
     * @return The remaining ItemStack, which cannot be added or ItemStack.EMPTY when fully added
     */
    public ItemStack addItemStack(ItemStack stack) {
        FairyRingTileEntity master = this.getMaster();
        if (stack != null && master != null && !stack.isEmpty()) {
            boolean dirty = false;
            //each slot has only a stack size of 1
            for (int i = 0; i < master.items.size(); i++) {
                if (master.items.get(i).isEmpty()) {
                    master.setInventorySlotContents(i, stack.split(1));
                    dirty = true;
                    if (stack.isEmpty()) {
                        break;
                    }
                }
            }
            if (dirty) {
                this.sendUpdates();
            }
        }
        return stack;
    }

    @Override
    public int getSizeInventory() {
        FairyRingTileEntity master = this.getMaster();
        if (master != null) {
            return master.items.size();
        }
        return 0;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        FairyRingTileEntity master = this.getMaster();
        if (master != null) {
            for (ItemStack itemstack : master.items) {
                if (!itemstack.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    @Nonnull
    public ItemStack getStackInSlot(int slot) {
        FairyRingTileEntity master = this.getMaster();
        if (master != null && slot >= 0 && slot < master.items.size()) {
            return master.items.get(slot);
        }
        return ItemStack.EMPTY;
    }

    @Override
    @Nonnull
    public ItemStack decrStackSize(int slot, int count) {
        FairyRingTileEntity master = this.getMaster();
        ItemStack stack = ItemStack.EMPTY;
        if (master != null && count > 0 && slot >= 0 && slot < master.items.size()) {
            stack = ItemStackHelper.getAndSplit(master.items, slot, count);
            this.sendUpdates();
        }
        return stack;
    }

    @Override
    @Nonnull
    public ItemStack removeStackFromSlot(int slot) {
        FairyRingTileEntity master = this.getMaster();
        if (master != null && slot < master.items.size()) {
            return ItemStackHelper.getAndRemove(master.items, slot);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void setInventorySlotContents(int slot, @Nonnull ItemStack itemStack) {
        FairyRingTileEntity master = this.getMaster();
        if (master != null && slot >= 0 && slot < master.items.size()) {
            master.items.set(slot, itemStack);
        }
    }

    @Override
    public boolean isUsableByPlayer(@Nonnull PlayerEntity playerEntity) {
        return false;
    }

    @Override
    public void clear() {
        FairyRingTileEntity master = this.getMaster();
        if (master != null) {
            master.items.clear();
        }
    }
}
