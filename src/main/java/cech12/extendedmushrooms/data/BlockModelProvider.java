package cech12.extendedmushrooms.data;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.block.EMMushroomBlock;
import cech12.extendedmushrooms.block.MushroomPlanksBlock;
import cech12.extendedmushrooms.block.MushroomWoodButtonBlock;
import cech12.extendedmushrooms.block.mushroomblocks.HoneyWaxcapCap;
import cech12.extendedmushrooms.block.mushroomblocks.MushroomCapBlock;
import cech12.extendedmushrooms.block.mushroomblocks.MushroomStemBlock;
import cech12.extendedmushrooms.block.mushroomblocks.ParrotWaxcapCap;
import cech12.extendedmushrooms.init.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.WoolCarpetBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;

public class BlockModelProvider extends net.minecraftforge.client.model.generators.BlockModelProvider {

    public BlockModelProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, ExtendedMushrooms.MOD_ID, existingFileHelper);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Extended Mushrooms Block Models";
    }

    private static ResourceLocation getResourceLocation(String path) {
        return new ResourceLocation(ExtendedMushrooms.MOD_ID, path);
    }

    private static ResourceLocation getBlockResourceLocation(String name) {
        return getResourceLocation("block/" + name);
    }


    private static ResourceLocation getBlockResourceLocation(String name, String removeSuffix, String addSuffix) {
        return getBlockResourceLocation(name.substring(0, name.length() - removeSuffix.length()) + addSuffix);
    }

    private static ResourceLocation getBlockResourceLocation(String name, String removeSuffix, String addPrefix, String addSuffix) {
        return getBlockResourceLocation(addPrefix + name.substring(0, name.length() - removeSuffix.length()) + addSuffix);
    }

    private static ResourceLocation getCapResourceLocation(String name, String removeSuffix) {
        if (name.startsWith("brown_mushroom_")) {
            return new ResourceLocation("block/brown_mushroom_block");
        } else if (name.startsWith("red_mushroom_")) {
            return new ResourceLocation("block/red_mushroom_block");
        }
        return getBlockResourceLocation(name, removeSuffix, "");
    }

    @Override
    protected void registerModels() {
        //block models
        for (RegistryObject<Block> blockSupplier : ModBlocks.BLOCKS.getEntries()) {
            Block block = blockSupplier.get();
            String name = ForgeRegistries.BLOCKS.getKey(block).getPath();

            if (block instanceof EMMushroomBlock) {
                getBuilder(name)
                        .parent(getExistingFile(new ResourceLocation("block/cross")))
                        .texture("cross", getBlockResourceLocation(name))
                        .renderType("cutout");
            } else if (block instanceof MushroomWoodButtonBlock) {
                buttonBlock(name, getBlockResourceLocation(name, "_button", "_planks"));
            } else if (block instanceof MushroomCapBlock) {
                if (block instanceof HoneyWaxcapCap || block instanceof ParrotWaxcapCap) {
                    translucentHugeMushroomBlock(name, getBlockResourceLocation(name));
                    translucentHugeMushroomInsideBlock(getBlockResourceLocation(name, "_cap", "_cap_inside"));
                } else {
                    hugeMushroomBlock(name, getBlockResourceLocation(name));
                    hugeMushroomInsideBlock(getBlockResourceLocation(name, "_cap", "_cap_inside"));
                }
            } else if (block instanceof WoolCarpetBlock) {
                ResourceLocation texture = getCapResourceLocation(name, "_carpet");
                getBuilder(name)
                        .parent(getExistingFile(new ResourceLocation("block/carpet")))
                        .texture("particle", texture)
                        .texture("wool", texture);
            } else if (block instanceof DoorBlock) {
                ResourceLocation bottom = getBlockResourceLocation(name + "_bottom");
                ResourceLocation top = getBlockResourceLocation(name + "_top");
                getBuilder(name + "_bottom_left").parent(getExistingFile(new ResourceLocation("block/door_bottom_left"))).texture("bottom", bottom).texture("top", top);
                getBuilder(name + "_bottom_left_open").parent(getExistingFile(new ResourceLocation("block/door_bottom_left_open"))).texture("bottom", bottom).texture("top", top);
                getBuilder(name + "_bottom_right").parent(getExistingFile(new ResourceLocation("block/door_bottom_right"))).texture("bottom", bottom).texture("top", top);
                getBuilder(name + "_bottom_right_open").parent(getExistingFile(new ResourceLocation("block/door_bottom_right_open"))).texture("bottom", bottom).texture("top", top);
                getBuilder(name + "_top_left").parent(getExistingFile(new ResourceLocation("block/door_top_left"))).texture("bottom", bottom).texture("top", top);
                getBuilder(name + "_top_left_open").parent(getExistingFile(new ResourceLocation("block/door_top_left_open"))).texture("bottom", bottom).texture("top", top);
                getBuilder(name + "_top_right").parent(getExistingFile(new ResourceLocation("block/door_top_right"))).texture("bottom", bottom).texture("top", top);
                getBuilder(name + "_top_right_open").parent(getExistingFile(new ResourceLocation("block/door_top_right_open"))).texture("bottom", bottom).texture("top", top);
            } else if (block instanceof FenceGateBlock) {
                ResourceLocation texture = getBlockResourceLocation(name, "_fence_gate", "_planks");
                simpleTexturedBlock(name, "template_fence_gate", texture);
                simpleTexturedBlock(name + "_open", "template_fence_gate_open", texture);
                simpleTexturedBlock(name + "_wall", "template_fence_gate_wall", texture);
                simpleTexturedBlock(name + "_wall_open", "template_fence_gate_wall_open", texture);
            } else if (block instanceof FenceBlock) {
                ResourceLocation texture = getBlockResourceLocation(name, "_fence", "_planks");
                simpleTexturedBlock(name + "_inventory", "fence_inventory", texture);
                simpleTexturedBlock(name + "_post", "fence_post", texture);
                simpleTexturedBlock(name + "_side", "fence_side", texture);
            } else if (block instanceof FlowerPotBlock) {
                getBuilder(name)
                        .parent(getExistingFile(new ResourceLocation("block/flower_pot_cross")))
                        .texture("plant", getBlockResourceLocation(name, "_potted", ""))
                        .renderType("cutout");
            } else if (block instanceof PressurePlateBlock) {
                pressurePlateBlock(name, getBlockResourceLocation(name, "_pressure_plate", "_planks"));
            } else if (block instanceof SlabBlock) {
                ResourceLocation texture = getBlockResourceLocation(name, "_slab", "_planks");
                getBuilder(name)
                        .parent(getExistingFile(new ResourceLocation("block/slab")))
                        .texture("bottom", texture)
                        .texture("top", texture)
                        .texture("side", texture);
                getBuilder(name + "_top")
                        .parent(getExistingFile(new ResourceLocation("block/slab_top")))
                        .texture("bottom", texture)
                        .texture("top", texture)
                        .texture("side", texture);
            } else if (block instanceof StairBlock) {
                ResourceLocation texture = getBlockResourceLocation(name, "_stairs", "_planks");
                getBuilder(name)
                        .parent(getExistingFile(new ResourceLocation("block/stairs")))
                        .texture("bottom", texture)
                        .texture("top", texture)
                        .texture("side", texture);
                getBuilder(name + "_inner")
                        .parent(getExistingFile(new ResourceLocation("block/inner_stairs")))
                        .texture("bottom", texture)
                        .texture("top", texture)
                        .texture("side", texture);
                getBuilder(name + "_outer")
                        .parent(getExistingFile(new ResourceLocation("block/outer_stairs")))
                        .texture("bottom", texture)
                        .texture("top", texture)
                        .texture("side", texture);
            } else if (block instanceof StandingSignBlock || block instanceof WallSignBlock) {
                //only one model for both signs
                if (block instanceof StandingSignBlock) {
                    getBuilder(name).texture("particle", getBlockResourceLocation(name, "_sign", "_planks"));
                }
            } else if (block instanceof CeilingHangingSignBlock || block instanceof WallHangingSignBlock) {
                //only one model for both signs
                if (block instanceof CeilingHangingSignBlock) {
                    ResourceLocation location = getBlockResourceLocation(name, "_hanging_sign", "stripped_", "_stem");
                    getBuilder(name).texture("particle", location);
                }
            } else if (block instanceof HugeMushroomBlock) { // Stems & Stripped Stems. MushroomCapBlock is checked before
                hugeMushroomBlock(name, getBlockResourceLocation(name));
                if (block instanceof MushroomStemBlock) {
                    hugeMushroomInsideBlock(getBlockResourceLocation(name, "_stem", "_stem_inside"));
                }
            } else if (block instanceof TrapDoorBlock) {
                ResourceLocation texture = getBlockResourceLocation(name);
                simpleTexturedBlock(name + "_bottom", "template_orientable_trapdoor_bottom", texture);
                simpleTexturedBlock(name + "_open", "template_orientable_trapdoor_open", texture);
                simpleTexturedBlock(name + "_top", "template_orientable_trapdoor_top", texture);
            } else if (block instanceof BushBlock) {
                //flower & grass
                getBuilder(name)
                        .parent(getExistingFile(new ResourceLocation("block/tinted_cross")))
                        .texture("cross", getBlockResourceLocation(name))
                        .renderType("cutout");
            } else if (block instanceof MushroomPlanksBlock) {
                //planks
                cubeBlock(name, getBlockResourceLocation(name));
            }
            // ignored: FairyRingBlock
        }

        //special models
        //triggered deadly fibrecap & its cap
        /*
        ResourceLocation triggeredDeadlyFibrecap = getBlockResourceLocation("poisonous_mushroom_triggered");
        getBuilder(triggeredDeadlyFibrecap.getPath())
                .parent(getExistingFile(new ResourceLocation("block/cross")))
                .texture("cross", triggeredDeadlyFibrecap)
                .renderType("cutout");
        ResourceLocation triggeredDeadlyFibrecapCap = getBlockResourceLocation("poisonous_mushroom_cap_triggered");
        getBuilder(triggeredDeadlyFibrecapCap.getPath())
                .texture("texture", triggeredDeadlyFibrecapCap)
                .texture("particle", triggeredDeadlyFibrecapCap)
                .element().from(0, 0, 0).to(16, 16, 0).face(Direction.NORTH).texture("#texture").cullface(Direction.NORTH);
         */


    }


    private void cubeBlock(String name, ResourceLocation texture) {
        getBuilder(name)
                .parent(getExistingFile(new ResourceLocation("block/cube_all")))
                .texture("all", texture);
    }

    private void simpleTexturedBlock(String name, String parent, ResourceLocation texture) {
        getBuilder(name)
                .parent(getExistingFile(new ResourceLocation("block/" + parent)))
                .texture("texture", texture);
    }

    private void buttonBlock(String name, ResourceLocation texture) {
        simpleTexturedBlock(name, "button", texture);
        simpleTexturedBlock(name + "_inventory", "button_inventory", texture);
        simpleTexturedBlock(name + "_pressed", "button_pressed", texture);
    }

    private void pressurePlateBlock(String name, ResourceLocation texture) {
        simpleTexturedBlock(name, "pressure_plate_up", texture);
        simpleTexturedBlock(name + "_down", "pressure_plate_down", texture);
    }

    private void hugeMushroomBlock(String name, ResourceLocation texture) {
        getBuilder(name)
                .texture("texture", texture)
                .texture("particle", texture)
                .element().from(0, 0, 0).to(16, 16, 0).face(Direction.NORTH).texture("#texture").cullface(Direction.NORTH);
        cubeBlock(name + "_inventory", texture);
    }

    private void hugeMushroomInsideBlock(ResourceLocation texture) {
        getBuilder(texture.getPath())
                .ao(false)
                .texture("texture", texture)
                .texture("particle", texture)
                .element().from(0, 0, 0).to(16, 16, 0).face(Direction.NORTH).texture("#texture").cullface(Direction.NORTH);
    }

    private void translucentHugeMushroomBlock(String name, ResourceLocation texture) {
        getBuilder(name)
                .texture("texture", texture)
                .texture("particle", texture)
                .renderType("translucent")
                .element().from(0, 0, 0).to(16, 16, 0).face(Direction.NORTH).texture("#texture").cullface(Direction.NORTH);
        cubeBlock(name + "_inventory", texture);
    }

    private void translucentHugeMushroomInsideBlock(ResourceLocation texture) {
        getBuilder(texture.getPath())
                .ao(false)
                .texture("texture", texture)
                .texture("particle", texture)
                .renderType("translucent")
                .element().from(0, 0, 0).to(16, 16, 0).face(Direction.NORTH).texture("#texture").cullface(Direction.NORTH);
    }

}
