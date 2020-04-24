package cech12.extendedmushrooms.data;

import cech12.extendedmushrooms.ExtendedMushrooms;
import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SixWayBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.WoodButtonBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class BlockStateProvider extends net.minecraftforge.client.model.generators.BlockStateProvider {

    public BlockStateProvider(DataGenerator generator, ExistingFileHelper fileHelper) {
        super(generator, ExtendedMushrooms.MOD_ID, fileHelper);
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
        if ("stripped_mushroom_stem".equals(name)) {
            return new ResourceLocation("block/mushroom_block_inside");
        }
        return getBlockResourceLocation(name
                .replace("_cap", "")
                .replace("_stem", "")
                .replace("_stripped", "") + "_inside");
    }

    @Override
    protected void registerStatesAndModels() {
        for (Block block : ForgeRegistries.BLOCKS) {
            if (!ExtendedMushrooms.MOD_ID.equals(block.getRegistryName().getNamespace())) {
                continue;
            }
            String name = block.getRegistryName().getPath();

            if (block instanceof WoodButtonBlock) {
                // cap buttons, wood buttons
                ModelFile button = models().getExistingFile(getBlockResourceLocation(name));
                ModelFile pressed_button = models().getExistingFile(getBlockResourceLocation(name + "_pressed"));
                getVariantBuilder(block)
                        .forAllStates(state -> {
                            int xRot = 0;
                            AttachFace face = state.get(BlockStateProperties.FACE);
                            if (face == AttachFace.WALL) {
                                xRot = 90;
                            } else if (face == AttachFace.CEILING) {
                                xRot = 180;
                            }
                            return ConfiguredModel.builder()
                                    .modelFile((state.get(BlockStateProperties.POWERED)) ? pressed_button : button)
                                    .uvLock(face == AttachFace.WALL)
                                    .rotationX(xRot)
                                    .rotationY(((int) state.get(BlockStateProperties.HORIZONTAL_FACING).getHorizontalAngle() + 180) % 360)
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
                    for (Map.Entry<Direction, BooleanProperty> entry : SixWayBlock.FACING_TO_PROPERTY_MAP.entrySet()) {
                        int xRot = 0;
                        int yRot = 0;
                        switch (entry.getKey()) {
                            case EAST: yRot = 90; break;
                            case SOUTH: yRot = 180; break;
                            case WEST: yRot = 270; break;
                            case UP: xRot = 270; break;
                            case DOWN: xRot = 90; break;
                            default: break;
                        }
                        builder.part()
                                .modelFile((boolValue) ? outside : inside).rotationX(xRot).rotationY(yRot).uvLock(boolValue).addModel()
                                .condition(entry.getValue(), boolValue).end();
                    }
                }
            } else if (block instanceof DoorBlock) {
                ModelFile bottom = models().getExistingFile(getBlockResourceLocation(name + "_bottom"));
                ModelFile bottomHinge = models().getExistingFile(getBlockResourceLocation(name + "_bottom_hinge"));
                ModelFile top = models().getExistingFile(getBlockResourceLocation(name + "_top"));
                ModelFile topHinge = models().getExistingFile(getBlockResourceLocation(name + "_top_hinge"));
                doorBlock((DoorBlock) block, bottom, bottomHinge, top, topHinge);
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
                        ConfiguredModel.builder().modelFile((state.get(BlockStateProperties.POWERED)) ? plate_down : plate).build());
            } else if (block instanceof SlabBlock) {
                ModelFile bottom = models().getExistingFile(getBlockResourceLocation(name));
                ModelFile top = models().getExistingFile(getBlockResourceLocation(name + "_top"));
                ModelFile doubleSlab = models().getExistingFile(getBlockResourceLocation(name, "_slab", "_planks"));
                slabBlock((SlabBlock) block, bottom, top, doubleSlab);
            } else if (block instanceof StairsBlock) {
                ModelFile stair = models().getExistingFile(getBlockResourceLocation(name));
                ModelFile inner = models().getExistingFile(getBlockResourceLocation(name + "_inner"));
                ModelFile outer = models().getExistingFile(getBlockResourceLocation(name + "_outer"));
                stairsBlock((StairsBlock) block, stair, inner, outer);
            } else if (block instanceof TrapDoorBlock) {
                ModelFile bottom = models().getExistingFile(getBlockResourceLocation(name + "_bottom"));
                ModelFile top = models().getExistingFile(getBlockResourceLocation(name + "_top"));
                ModelFile open = models().getExistingFile(getBlockResourceLocation(name + "_open"));
                trapdoorBlock((TrapDoorBlock) block, bottom, top, open, true);
            } else {
                //mushrooms, planks, carpets, flower, potted flower, grass
                simpleBlock(block, models().getExistingFile(getBlockResourceLocation(name)));
            }
        }
    }

    /**
     * A bug in VariantBlockStateBuilder.PartialBlockstate.toString method produces wrong values for AttachFace EnumProperty (CAPSLOCK)
     * This class fixes this bug. A bit hacky but it works.
     */
    static class BugFixer extends net.minecraftforge.client.model.generators.BlockStateProvider {

        DataGenerator generator;

        public BugFixer(DataGenerator generator, ExistingFileHelper fileHelper) {
            super(generator, ExtendedMushrooms.MOD_ID, fileHelper);
            this.generator = generator;
        }

        @Override
        protected void registerStatesAndModels() {
            Path folder = this.generator.getOutputFolder();
            Charset charset = StandardCharsets.UTF_8;
            for (Block block : ForgeRegistries.BLOCKS) {
                if (!ExtendedMushrooms.MOD_ID.equals(block.getRegistryName().getNamespace())
                        || !(block instanceof WoodButtonBlock)) {
                    continue;
                }
                String name = block.getRegistryName().getPath();
                Path path = Paths.get(folder + "/assets/" + ExtendedMushrooms.MOD_ID + "/blockstates/" + name + ".json");
                try {
                    String content = new String(Files.readAllBytes(path), charset);
                    content = content.replaceAll("FLOOR", "floor");
                    content = content.replaceAll("WALL", "wall");
                    content = content.replaceAll("CEILING", "ceiling");
                    Files.write(path, content.getBytes(charset));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
