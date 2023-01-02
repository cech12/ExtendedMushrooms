package cech12.extendedmushrooms.client.renderer.blockentity;

import cech12.extendedmushrooms.client.ClientTickObserver;
import cech12.extendedmushrooms.blockentity.FairyRingBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

import javax.annotation.Nonnull;

public class FairyRingBlockEntityRenderer implements BlockEntityRenderer<FairyRingBlockEntity> {

    //private FairyRingWitchRenderer witchRenderer;
    //private WitchEntity witchEntity;

    public FairyRingBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    /*
    private EntityRendererManager getEntityRendererManager() {
        return Minecraft.getInstance().getRenderManager();
    }

    private FairyRingWitchRenderer getWitchRenderer() {
        return (this.witchRenderer == null) ? (this.witchRenderer = new FairyRingWitchRenderer(this.getEntityRendererManager())) : this.witchRenderer;
    }

    private WitchEntity getWitchEntity(World world) {
        if (this.witchEntity == null) {
            this.witchEntity = new WitchEntity(EntityType.WITCH, world);
            this.witchEntity.setNoAI(true);
        }
        return this.witchEntity;
    }
     */

    @Override
    public void render(@Nonnull FairyRingBlockEntity fairyRing, float partticks, @Nonnull PoseStack matrixStack, @Nonnull MultiBufferSource iRenderTypeBuffer, int p1, int p2) {
        //only render inventory of master
        if (fairyRing.isMaster()) {
            matrixStack.pushPose();
            //move to ring center
            Vec3 centerTranslation = FairyRingBlockEntity.CENTER_TRANSLATION_VECTOR;
            matrixStack.translate(centerTranslation.x, centerTranslation.y, centerTranslation.z);

            int itemCount = 0;
            for (int i = 0; i < fairyRing.getContainerSize(); i++) {
                if (!fairyRing.getItem(i).isEmpty()) {
                    itemCount++;
                }
            }

            double time = ClientTickObserver.ticksSinceStart + partticks;

            //get recipe progress
            float quadraticRecipeProgress = 0;
            float quadraticRecipeProgressInverse = 1;
            if (fairyRing.getRecipeTimeTotal() > 0) {
                float recipeProgress = (float) fairyRing.getRecipeTime() / (float) fairyRing.getRecipeTimeTotal();
                float recipeProgressInverse = 1 - recipeProgress;
                quadraticRecipeProgress = recipeProgress * recipeProgress;
                quadraticRecipeProgressInverse = recipeProgressInverse * recipeProgressInverse;
            }
            float anglePerItem = 360F / itemCount;
            Minecraft mc = Minecraft.getInstance();
            mc.textureManager.bindForSetup(InventoryMenu.BLOCK_ATLAS);
            for(int i = 0; i < fairyRing.getContainerSize(); i++) {
                matrixStack.pushPose();
                if (itemCount > 1) {
                    //deposit items in a circle, when more than one items are in inventory
                    matrixStack.mulPose(new Quaternionf(Axis.YP.rotation((float) Math.toRadians(anglePerItem * i))));
                    matrixStack.translate(0.75 * quadraticRecipeProgressInverse, 0, 0);
                }
                //add some (slow) motion
                matrixStack.mulPose(new Quaternionf(Axis.YP.rotation((float) Math.toRadians((time / 3 + i * 10) % 360))));
                matrixStack.translate(0, Math.sin((time + i * 10) / 10.0) * 0.01 + 0.05 + (1.1 * quadraticRecipeProgress), 0);
                //render item
                ItemStack stack = fairyRing.getItem(i);
                if(!stack.isEmpty()) {
                    mc.getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.GROUND, p1, p2, matrixStack, iRenderTypeBuffer, 0);
                }
                matrixStack.popPose();
            }

            //render entity inside of ring
            /*
            if (fairyRing.getMode() != FairyRingMode.NORMAL) {
                matrixStack.push();
                //let entity look into players direction
                if (fairyRing.getWorld() != null) {
                    Vector3d center = fairyRing.getCenter();
                    PlayerEntity player = fairyRing.getWorld().getClosestPlayer(EntityPredicate.DEFAULT, center.x, center.y, center.z);
                    if (player != null) {
                        Vector3d playerPos = player.getPositionVec();
                        if (center.distanceTo(playerPos) < 6) {
                            Vector3d flattenedPlayerPos = new Vector3d(playerPos.x, center.y, playerPos.z);
                            Vector3d south = new Vector3d(0, 0, 1);
                            Vector3d direction = flattenedPlayerPos.subtract(center).normalize();
                            double angle = Math.acos(direction.dotProduct(south));
                            if (playerPos.x < center.x) {
                                angle = -angle;
                            }
                            matrixStack.rotate(new Quaternion(yAxis, (float) angle, false));
                        }
                    }
                }
                if (fairyRing.getMode() == FairyRingMode.FAIRY) {
                    FairyEntity entity = this.getFairyEntity(fairyRing.getWorld());
                    this.getFairyRenderer().render(entity, 0, partticks, matrixStack, iRenderTypeBuffer, this.getEntityRendererManager().getPackedLight(entity, partticks));
                } else if (fairyRing.getMode() == FairyRingMode.WITCH) {
                    WitchEntity entity = this.getWitchEntity(fairyRing.getWorld());
                    this.getWitchRenderer().render(entity, 0, partticks, matrixStack, iRenderTypeBuffer, this.getEntityRendererManager().getPackedLight(entity, partticks));
                }
                matrixStack.pop();
            }
             */

            matrixStack.popPose();
        }
    }

    /*
    private static class FairyRingWitchRenderer extends WitchRenderer {

        private final ResourceLocation WITCH_TEXTURES = new ResourceLocation(ExtendedMushrooms.MOD_ID, "textures/entity/witch.png");

        public FairyRingWitchRenderer(EntityRendererManager p_i46131_1_) {
            super(p_i46131_1_);
        }

        @Nonnull
        @Override
        public ResourceLocation getEntityTexture(WitchEntity p_110775_1_) {
            return WITCH_TEXTURES;
        }
    }
     */

}
