package me.Jonathon594.Mythria.Blocks;

import me.Jonathon594.Mythria.TileEntity.StoneFurnaceTileEntity;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import org.jetbrains.annotations.Nullable;

public class NetherFurnaceBlock extends AbstractMythriaFurnaceBlock {
    public NetherFurnaceBlock(Material material, String name, double weight, SoundType sound) {
        super(material, name, weight, sound);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new StoneFurnaceTileEntity();
    }
}
