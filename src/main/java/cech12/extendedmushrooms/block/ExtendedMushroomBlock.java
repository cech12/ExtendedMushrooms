package cech12.extendedmushrooms.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * This class changes the MushroomBlock to change the growing mechanism.
 * It adds a tree like automatic growing.
 */
public class ExtendedMushroomBlock extends MushroomBlock implements IBlockItemGetter {

    private ResourceLocation registryName;

    /**
     * Constructor for the mushroom block.
     * @param mushroomBlock MushroomBlock to override.
     */
    public ExtendedMushroomBlock(Block mushroomBlock) {
        super(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.PLANT).lightValue(1));
        this.registryName = mushroomBlock.getRegistryName();
        if (this.registryName != null) {
            this.setRegistryName(this.registryName);
        }
    }

    /**
     * Add a tree like automatic growing.
     * The automatic multiplication still remaining.
     */
    @Override
    public void tick(@Nonnull BlockState state, @Nonnull ServerWorld worldIn, @Nonnull BlockPos pos, @Nonnull Random random) {
        //automatic multiplication
        super.tick(state, worldIn, pos, random);

        //automatic growing of mushrooms
        if (!worldIn.isAreaLoaded(pos, 7)) return; // Forge: prevent loading unloaded chunks
        if (random.nextInt(1) == 0) {
            this.grow(worldIn, random, pos, state);
        }
    }

    @Override
    public Item getBlockItem() {
        Item item = new BlockItem(this, (new Item.Properties()).group(ItemGroup.DECORATIONS));
        item.setRegistryName(this.registryName);
        return item;
    }
}
