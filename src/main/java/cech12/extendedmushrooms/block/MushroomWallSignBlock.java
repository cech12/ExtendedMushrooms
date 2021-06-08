package cech12.extendedmushrooms.block;

import cech12.extendedmushrooms.tileentity.MushroomSignTileEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class MushroomWallSignBlock extends WallSignBlock {

    public MushroomWallSignBlock(AbstractBlock.Properties propertiesIn, WoodType woodTypeIn) {
        super(propertiesIn, woodTypeIn);
    }

    @Override
    public boolean hasTileEntity(BlockState stateIn) {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new MushroomSignTileEntity();
    }

}
