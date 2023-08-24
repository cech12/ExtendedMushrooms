package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.blockentity.MushroomHangingSignBlockEntity;
import cech12.extendedmushrooms.client.renderer.blockentity.FairyRingBlockEntityRenderer;
import cech12.extendedmushrooms.item.MushroomWoodType;
import cech12.extendedmushrooms.blockentity.FairyRingBlockEntity;
import cech12.extendedmushrooms.blockentity.MushroomSignBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.world.level.block.Block;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Arrays;

public class ModBlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ExtendedMushrooms.MOD_ID);

    public static RegistryObject<BlockEntityType<FairyRingBlockEntity>> FAIRY_RING = register("fairy_ring", FairyRingBlockEntity::new, ModBlocks.FAIRY_RING);
    public static RegistryObject<BlockEntityType<MushroomSignBlockEntity>> MUSHROOM_SIGN = register("mushroom_sign", MushroomSignBlockEntity::new, generateSignBlocks());

    public static RegistryObject<BlockEntityType<MushroomHangingSignBlockEntity>> MUSHROOM_HANGING_SIGN = register("mushroom_hanging_sign", MushroomHangingSignBlockEntity::new, generateHangingSignBlocks());

    private static RegistryObject<?>[] generateSignBlocks() {
        ArrayList<RegistryObject<?>> list = new ArrayList<>();
        for (MushroomWoodType woodType : MushroomWoodType.values()) {
            list.add(ModBlocks.getMushroomBlock(woodType.getName(), ModBlocks.BlockType.STANDING_SIGN));
            list.add(ModBlocks.getMushroomBlock(woodType.getName(), ModBlocks.BlockType.WALL_SIGN));
        }
        return list.toArray(new RegistryObject<?>[0]);
    }

    private static RegistryObject<?>[] generateHangingSignBlocks() {
        ArrayList<RegistryObject<?>> list = new ArrayList<>();
        for (MushroomWoodType woodType : MushroomWoodType.values()) {
            list.add(ModBlocks.getMushroomBlock(woodType.getName(), ModBlocks.BlockType.HANGING_SIGN));
            list.add(ModBlocks.getMushroomBlock(woodType.getName(), ModBlocks.BlockType.WALL_HANGING_SIGN));
        }
        return list.toArray(new RegistryObject<?>[0]);
    }

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String registryName, BlockEntityType.BlockEntitySupplier<T> supplier, RegistryObject<?>... blocks) {
        return BLOCK_ENTITY_TYPES.register(registryName, () -> BlockEntityType.Builder.of(supplier, Arrays.stream(blocks).map(RegistryObject::get).toArray(Block[]::new)).build(null));
    }

    /**
     * Setup renderers for entities. Is called at mod initialisation.
     */
    @OnlyIn(Dist.CLIENT)
    public static void setupRenderers(final FMLClientSetupEvent event) {
        BlockEntityRenderers.register(FAIRY_RING.get(), FairyRingBlockEntityRenderer::new);
        BlockEntityRenderers.register(MUSHROOM_SIGN.get(), SignRenderer::new);
        BlockEntityRenderers.register(MUSHROOM_HANGING_SIGN.get(), HangingSignRenderer::new);
        event.enqueueWork(() -> {
            for (MushroomWoodType type : MushroomWoodType.values()) {
                Sheets.addWoodType(type.getWoodType());
            }
        });
    }


}
