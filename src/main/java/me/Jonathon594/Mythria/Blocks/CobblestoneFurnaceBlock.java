package me.Jonathon594.Mythria.Blocks;

import me.Jonathon594.Mythria.TileEntity.CobblestoneFurnaceTileEntity;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import org.jetbrains.annotations.Nullable;

public class CobblestoneFurnaceBlock extends AbstractMythriaFurnaceBlock {
    public CobblestoneFurnaceBlock(Material material, String name, double weight, SoundType sound) {
        super(material, name, weight, sound);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new CobblestoneFurnaceTileEntity();
    }
}
