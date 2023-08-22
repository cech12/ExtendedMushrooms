package cech12.extendedmushrooms.data;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.block.FairyRingBlock;
import cech12.extendedmushrooms.init.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

import java.util.Map;

public class BlockStateProvider extends net.minecraftforge.client.model.generators.BlockStateProvider {

    public BlockStateProvider(PackOutput packOutput, ExistingFileHelper fileHelper) {
        super(packOutput, ExtendedMushrooms.MOD_ID, fileHelper);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Extended Mushrooms Blockstates";
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

    private static ResourceLocation getInsideResourceLocation(String name) {
        if (name.equals(ModBlocks.STRIPPED_MUSHROOM_STEM.getKey().location().getPath())) {
            return new ResourceLocation("block/mushroom_block_inside");
        }
        return getBlockResourceLocation(name
                .replace("_cap", "")
                .replace("_stem", "")
                .replace("_log", "")
                .replace("_stripped", "") + "_inside");
    }

    @Override
    protected void registerStatesAndModels() {
        for (Block block : ForgeRegistries.BLOCKS) {
            if (!ExtendedMushrooms.MOD_ID.equals(ForgeRegistries.BLOCKS.getKey(block).getNamespace())) {
                continue;
            }
            String name = ForgeRegistries.BLOCKS.getKey(block).getPath();

            if (block instanceof ButtonBlock) {
                // cap buttons, wood buttons
                ModelFile button = models().getExistingFile(getBlockResourceLocation(name));
                ModelFile pressed_button = models().getExistingFile(getBlockResourceLocation(name + "_pressed"));
                getVariantBuilder(block)
                        .forAllStates(state -> {
                            int xRot = 0;
                            AttachFace face = state.getValue(BlockStateProperties.ATTACH_FACE);
                            if (face == AttachFace.WALL) {
                                xRot = 90;
                            } else if (face == AttachFace.CEILING) {
                                xRot = 180;
                            }
                            return ConfiguredModel.builder()
                                    .modelFile((state.getValue(BlockStateProperties.POWERED)) ? pressed_button : button)
                                    .uvLock(face == AttachFace.WALL)
                                    .rotationX(xRot)
                                    .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360)
                                    .build();
                        });
                //Forge bug: VariantBlockStateBuilder.PartialBlockstate.toString method produces wrong values for AttachFace EnumProperty (CAPSLOCK)
                // see BugFixer class below
            } else if (block instanceof HugeMushroomBlock) {
                // caps, stems, stripped stems
                ModelFile outside = models().getExistingFile(getBlockResourceLocation(name));
                ModelFile inside = models().getExistingFile(getInsideResourceLocation(name));
                MultiPartBlockStateBuilder builder = getMultipartBuilder(block);
                for (boolean boolValue : new boolean[]{true, false}) {
                    for (Map.Entry<Direction, BooleanProperty> entry : PipeBlock.PROPERTY_BY_DIRECTION.entrySet()) {
                        int xRot = 0;
                        int yRot = 0;
                        switch (entry.getKey()) {
                            case EAST -> yRot = 90;
                            case SOUTH -> yRot = 180;
                            case WEST -> yRot = 270;
                            case UP -> xRot = 270;
                            case DOWN -> xRot = 90;
                            default -> {
                            }
                        }
                        builder.part()
                                .modelFile((boolValue) ? outside : inside).rotationX(xRot).rotationY(yRot).uvLock(boolValue).addModel()
                                .condition(entry.getValue(), boolValue).end();
                    }
                }
            } else if (block instanceof DoorBlock) {
                doorBlock((DoorBlock) block, name, getBlockResourceLocation(name + "_bottom"), getBlockResourceLocation(name + "_top"));
            } else if (block instanceof FenceGateBlock) {
                ModelFile gate = models().getExistingFile(getBlockResourceLocation(name));
                ModelFile gateOpen = models().getExistingFile(getBlockResourceLocation(name + "_open"));
                ModelFile wall = models().getExistingFile(getBlockResourceLocation(name + "_wall"));
                ModelFile wallOpen = models().getExistingFile(getBlockResourceLocation(name + "_wall_open"));
                fenceGateBlock((FenceGateBlock) block, gate, gateOpen, wall, wallOpen);
            } else if (block instanceof FenceBlock) {
                ModelFile post = models().getExistingFile(getBlockResourceLocation(name + "_post"));
                ModelFile side = models().getExistingFile(getBlockResourceLocation(name + "_side"));
                fourWayBlock((FenceBlock) block, post, side);
            } else if (block instanceof PressurePlateBlock) {
                // cap plates, wood plates
                ModelFile plate = models().getExistingFile(getBlockResourceLocation(name));
                ModelFile plate_down = models().getExistingFile(getBlockResourceLocation(name + "_down"));
                getVariantBuilder(block).forAllStatesExcept(state ->
                        ConfiguredModel.builder().modelFile((state.getValue(BlockStateProperties.POWERED)) ? plate_down : plate).build());
            } else if (block instanceof StandingSignBlock) {
                simpleBlock(block, models().getExistingFile(getBlockResourceLocation(name)));
            } else if (block instanceof WallSignBlock) {
                simpleBlock(block, models().getExistingFile(getBlockResourceLocation(name.replace("_wall", ""))));
            } else if (block instanceof CeilingHangingSignBlock) {
                simpleBlock(block, models().getExistingFile(getBlockResourceLocation(name)));
            } else if (block instanceof WallHangingSignBlock) {
                simpleBlock(block, models().getExistingFile(getBlockResourceLocation(name.replace("_wall", ""))));
            } else if (block instanceof SlabBlock) {
                ModelFile bottom = models().getExistingFile(getBlockResourceLocation(name));
                ModelFile top = models().getExistingFile(getBlockResourceLocation(name + "_top"));
                ModelFile doubleSlab = models().getExistingFile(getBlockResourceLocation(name, "_slab", "_planks"));
                slabBlock((SlabBlock) block, bottom, top, doubleSlab);
            } else if (block instanceof StairBlock) {
                ModelFile stair = models().getExistingFile(getBlockResourceLocation(name));
                ModelFile inner = models().getExistingFile(getBlockResourceLocation(name + "_inner"));
                ModelFile outer = models().getExistingFile(getBlockResourceLocation(name + "_outer"));
                stairsBlock((StairBlock) block, stair, inner, outer);
            } else if (block instanceof TrapDoorBlock) {
                ModelFile bottom = models().getExistingFile(getBlockResourceLocation(name + "_bottom"));
                ModelFile top = models().getExistingFile(getBlockResourceLocation(name + "_top"));
                ModelFile open = models().getExistingFile(getBlockResourceLocation(name + "_open"));
                trapdoorBlock((TrapDoorBlock) block, bottom, top, open, true);
            } else if (!(block instanceof FairyRingBlock)) { // Fairy Ring is hand made
                //mushrooms, (vertical) planks, carpets, flower, potted flower, grass
                simpleBlock(block, models().getExistingFile(getBlockResourceLocation(name)));
            }
        }
    }

}
