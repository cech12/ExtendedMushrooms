package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.api.block.ExtendedMushroomsBlocks;
import cech12.extendedmushrooms.client.renderer.tileentity.FairyRingTileEntityRenderer;
import cech12.extendedmushrooms.client.renderer.tileentity.VariantChestTileEntityRenderer;
import cech12.extendedmushrooms.client.renderer.tileentity.VariantTrappedChestTileEntityRenderer;
import cech12.extendedmushrooms.item.MushroomWoodType;
import cech12.extendedmushrooms.tileentity.FairyRingTileEntity;
import cech12.extendedmushrooms.tileentity.MushroomSignTileEntity;
import cech12.extendedmushrooms.tileentity.VariantChestTileEntity;
import cech12.extendedmushrooms.tileentity.VariantTrappedChestTileEntity;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.function.Supplier;

import static cech12.extendedmushrooms.api.tileentity.ExtendedMushroomsTileEntities.*;

@Mod.EventBusSubscriber(modid= ExtendedMushrooms.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModTileEntities {

    @SubscribeEvent
    public static void registerTileEntities(RegistryEvent.Register<TileEntityType<?>> event) {
        FAIRY_RING = register(event, FairyRingTileEntity::new, "fairy_ring", ExtendedMushroomsBlocks.FAIRY_RING);
        MUSHROOM_SIGN = register(event, MushroomSignTileEntity::new, "mushroom_sign",
                ModBlocks.MUSHROOM_STANDING_SIGN.get(),
                ModBlocks.MUSHROOM_WALL_SIGN.get(),
                ModBlocks.GLOWSHROOM_STANDING_SIGN.get(),
                ModBlocks.GLOWSHROOM_WALL_SIGN.get(),
                ModBlocks.POISONOUS_MUSHROOM_STANDING_SIGN.get(),
                ModBlocks.POISONOUS_MUSHROOM_WALL_SIGN.get());
        VARIANT_CHEST = register(event, VariantChestTileEntity::new, "variant_chest",
                ExtendedMushroomsBlocks.MUSHROOM_CHEST,
                ExtendedMushroomsBlocks.GLOWSHROOM_CHEST,
                ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_CHEST);
        VARIANT_TRAPPED_CHEST = register(event, VariantTrappedChestTileEntity::new, "variant_trapped_chest",
                ExtendedMushroomsBlocks.MUSHROOM_CHEST_TRAPPED,
                ExtendedMushroomsBlocks.GLOWSHROOM_CHEST_TRAPPED,
                ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_CHEST_TRAPPED);
    }


    private static <T extends TileEntity> TileEntityType<T> register(RegistryEvent.Register<TileEntityType<?>> registryEvent, Supplier<T> supplier, String registryName, Block... blocks) {
        TileEntityType<T> tileEntityType = TileEntityType.Builder.create(supplier, blocks).build(null);
        tileEntityType.setRegistryName(registryName);
        registryEvent.getRegistry().register(tileEntityType);
        return tileEntityType;
    }

    /**
     * Setup renderers for entities. Is called at mod initialisation.
     */
    @OnlyIn(Dist.CLIENT)
    public static void setupRenderers(final FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntityRenderer((TileEntityType<FairyRingTileEntity>) FAIRY_RING, FairyRingTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer((TileEntityType<SignTileEntity>) MUSHROOM_SIGN, SignTileEntityRenderer::new);
        event.enqueueWork(() -> {
            for (MushroomWoodType type : MushroomWoodType.values()) {
                Atlases.addWoodType(type.getWoodType());
            }
        });
        ClientRegistry.bindTileEntityRenderer((TileEntityType<VariantChestTileEntity>) VARIANT_CHEST, VariantChestTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer((TileEntityType<VariantTrappedChestTileEntity>) VARIANT_TRAPPED_CHEST, VariantTrappedChestTileEntityRenderer::new);
    }


}
