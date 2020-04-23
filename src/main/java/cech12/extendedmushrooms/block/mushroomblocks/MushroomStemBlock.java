package cech12.extendedmushrooms.block.mushroomblocks;

import cech12.extendedmushrooms.item.MushroomWoodType;
import net.minecraft.block.HugeMushroomBlock;

/**
 * To make a difference for data generation.
 * Only mushroom stem blocks should be an object of this class. Stripped variants not!
 */
public class MushroomStemBlock extends HugeMushroomBlock {

    private final MushroomWoodType type;

    public MushroomStemBlock(MushroomWoodType type, Properties properties) {
        super(properties);
        this.type = type;
    }

    public MushroomWoodType getType() {
        return this.type;
    }
}
