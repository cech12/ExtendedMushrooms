package cech12.extendedmushrooms.block.mushroomblocks;

import cech12.extendedmushrooms.item.MushroomType;
import net.minecraft.block.HugeMushroomBlock;

/**
 * To make a difference for data generation.
 * Only mushroom cap blocks should be an object of this class.
 */
public class MushroomCapBlock extends HugeMushroomBlock {

    private final MushroomType type;

    public MushroomCapBlock(MushroomType type, Properties properties) {
        super(properties);
        this.type = type;
    }

    public MushroomType getType() {
        return this.type;
    }
}
