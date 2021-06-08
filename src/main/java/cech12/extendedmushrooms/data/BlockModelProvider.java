package cech12.extendedmushrooms.data;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.block.BookshelfBlock;
import cech12.extendedmushrooms.block.EMMushroomBlock;
import cech12.extendedmushrooms.block.MushroomCapButtonBlock;
import cech12.extendedmushrooms.block.MushroomCapPressurePlateBlock;
import cech12.extendedmushrooms.block.MushroomLadderBlock;
import cech12.extendedmushrooms.block.MushroomWoodButtonBlock;
import cech12.extendedmushrooms.block.VariantChestBlock;
import cech12.extendedmushrooms.block.VariantTrappedChestBlock;
import cech12.extendedmushrooms.block.VerticalPlanksBlock;
import cech12.extendedmushrooms.block.VerticalSlabBlock;
import cech12.extendedmushrooms.block.mushroomblocks.MushroomCapBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BushBlock;
import net.minecraft.block.CarpetBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

public class BlockModelProvider extends net.minecraftforge.client.model.generators.BlockModelProvider {

    public BlockModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ExtendedMushrooms.MOD_ID, existingFileHelper);
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
        //parent models
        ResourceLocation verticalPlanks = getBlockResourceLocation("vertical_planks");
        getBuilder(verticalPlanks.getPath())
                .parent(getExistingFile(new ResourceLocation("block/block")))
                .texture("particle", "#all")
                .element().from(0, 0, 0).to(16, 16, 16)
                .allFaces((direction, faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture("#all").cullface(direction).rotation(ModelBuilder.FaceRotation.CLOCKWISE_90));
        ResourceLocation verticalSlab = getBlockResourceLocation("vertical_slab");
        getBuilder(verticalSlab.getPath())
                .parent(getExistingFile(new ResourceLocation("block/block")))
                .texture("particle", "#side")
                .element().from(0, 0, 8).to(16, 16, 16)
                .allFaces((direction, faceBuilder) -> {
                    String texture = "#side";
                    if (direction == Direction.DOWN) {
                        texture = "#bottom";
                    } else if (direction == Direction.UP) {
                        texture = "#top";
                    }
                    int u1 = (direction == Direction.EAST || direction == Direction.WEST) ? 8 : 0;
                    int v1 = (direction == Direction.DOWN || direction == Direction.UP) ? 8 : 0;
                    faceBuilder.uvs(u1, v1, 16, 16).texture(texture);
                });

        //block models
        for (Block block : ForgeRegistries.BLOCKS) {
            if (!ExtendedMushrooms.MOD_ID.equals(block.getRegistryName().getNamespace())) {
                continue;
            }
            String name = block.getRegistryName().getPath();

            if (block instanceof EMMushroomBlock) {
                getBuilder(name)
                        .parent(getExistingFile(new ResourceLocation("block/cross")))
                        .texture("cross", getBlockResourceLocation(name));
            } else if (block instanceof BookshelfBlock) {
                ResourceLocation side = getBlockResourceLocation(name);
                ResourceLocation end = getBlockResourceLocation(name, "_bookshelf", "_planks");
                cubeColumn(name, side, end);
            } else if (block instanceof MushroomWoodButtonBlock) {
                buttonBlock(name, getBlockResourceLocation(name, "_button", "_planks"));
            } else if (block instanceof MushroomCapBlock) {
                hugeMushroomBlock(name, getBlockResourceLocation(name));
                // & inside variant
                ResourceLocation texture = getBlockResourceLocation(name, "_cap", "_inside");
                getBuilder(texture.getPath())
                        .ao(false)
                        .texture("texture", texture)
                        .texture("particle", texture)
                        .element().from(0, 0, 0).to(16, 16, 0).face(Direction.NORTH).texture("#texture").cullface(Direction.NORTH);
            } else if (block instanceof MushroomCapButtonBlock) {
                buttonBlock(name, getCapResourceLocation(name, "_button"));
            } else if (block instanceof CarpetBlock) {
                ResourceLocation texture = getCapResourceLocation(name, "_carpet");
                getBuilder(name)
                        .parent(getExistingFile(new ResourceLocation("block/carpet")))
                        .texture("particle", texture)
                        .texture("wool", texture);
            } else if (block instanceof MushroomCapPressurePlateBlock) {
                pressurePlateBlock(name, getCapResourceLocation(name, "_pressure_plate"));
            } else if (block instanceof VariantChestBlock) {
                getBuilder(name).texture("particle", getBlockResourceLocation(name, "_chest", "_planks"));
            } else if (block instanceof VariantTrappedChestBlock) {
                //ignore
            } else if (block instanceof DoorBlock) {
                ResourceLocation bottom = getBlockResourceLocation(name + "_bottom");
                ResourceLocation top = getBlockResourceLocation(name + "_top");
                getBuilder(name + "_bottom")
                        .parent(getExistingFile(new ResourceLocation("block/door_bottom")))
                        .texture("bottom", bottom)
                        .texture("top", top);
                getBuilder(name + "_bottom_hinge")
                        .parent(getExistingFile(new ResourceLocation("block/door_bottom_rh")))
                        .texture("bottom", bottom)
                        .texture("top", top);
                getBuilder(name + "_top")
                        .parent(getExistingFile(new ResourceLocation("block/door_top")))
                        .texture("bottom", bottom)
                        .texture("top", top);
                getBuilder(name + "_top_hinge")
                        .parent(getExistingFile(new ResourceLocation("block/door_top_rh")))
                        .texture("bottom", bottom)
                        .texture("top", top);
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
                        .texture("plant", getBlockResourceLocation(name, "_potted", ""));
            } else if (block instanceof MushroomLadderBlock) {
                ResourceLocation texture = getBlockResourceLocation(name);
                getBuilder(name)
                        .parent(getExistingFile(new ResourceLocation("block/ladder")))
                        .ao(false)
                        .texture("particle", texture)
                        .texture("texture", texture);
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
            } else if (block instanceof StairsBlock) {
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
            } else if (block instanceof HugeMushroomBlock) { // Stems & Stripped Stems. MushroomCapBlock is checked before
                hugeMushroomBlock(name, getBlockResourceLocation(name));
            } else if (block instanceof TrapDoorBlock) {
                ResourceLocation texture = getBlockResourceLocation(name);
                simpleTexturedBlock(name + "_bottom", "template_orientable_trapdoor_bottom", texture);
                simpleTexturedBlock(name + "_open", "template_orientable_trapdoor_open", texture);
                simpleTexturedBlock(name + "_top", "template_orientable_trapdoor_top", texture);
            } else if (block instanceof VerticalPlanksBlock) {
                ResourceLocation texture = getBlockResourceLocation(name, "_vertical_planks", "_planks");
                getBuilder(name)
                        .parent(getExistingFile(verticalPlanks))
                        .texture("all", texture);
            } else if (block instanceof VerticalSlabBlock) {
                ResourceLocation texture = getBlockResourceLocation(name, "_vertical_slab", "_planks");
                getBuilder(name)
                        .parent(getExistingFile(verticalSlab))
                        .texture("bottom", texture)
                        .texture("top", texture)
                        .texture("side", texture);
            } else if (block instanceof BushBlock) {
                //flower & grass
                getBuilder(name)
                        .parent(getExistingFile(new ResourceLocation("block/tinted_cross")))
                        .texture("cross", getBlockResourceLocation(name));
            } else {
                //planks
                cubeBlock(name, getBlockResourceLocation(name));
            }
        }

        //special models
        //triggered poisonous mushroom & its cap
        ResourceLocation triggeredPoisonousMushroom = getBlockResourceLocation("poisonous_mushroom_triggered");
        getBuilder(triggeredPoisonousMushroom.getPath())
                .parent(getExistingFile(new ResourceLocation("block/cross")))
                .texture("cross", triggeredPoisonousMushroom);
        ResourceLocation triggeredPoisonousMushroomCap = getBlockResourceLocation("poisonous_mushroom_cap_triggered");
        getBuilder(triggeredPoisonousMushroomCap.getPath())
                .texture("texture", triggeredPoisonousMushroomCap)
                .texture("particle", triggeredPoisonousMushroomCap)
                .element().from(0, 0, 0).to(16, 16, 0).face(Direction.NORTH).texture("#texture").cullface(Direction.NORTH);


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



}
