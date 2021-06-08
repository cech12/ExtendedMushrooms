package cech12.extendedmushrooms.block;

import cech12.extendedmushrooms.tileentity.MushroomSignTileEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;

public class MushroomStandingSignBlock extends StandingSignBlock {

    public MushroomStandingSignBlock(AbstractBlock.Properties propertiesIn, WoodType woodTypeIn) {
        super(propertiesIn, woodTypeIn);
    }

    @Override
    public boolean hasTileEntity(BlockState stateIn) {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(@Nonnull IBlockReader worldIn) {
        return new MushroomSignTileEntity();
    }

}
