package me.Jonathon594.Mythria.Blocks;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.DataTypes.StoneProperty;
import me.Jonathon594.Mythria.Enum.EnumStone;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateContainer;
import net.minecraftforge.event.world.BlockEvent;

import java.util.Arrays;
import java.util.function.Supplier;

public class MythriaOre extends MythriaBlock {
    public static final StoneProperty STONE_TYPE = new StoneProperty("stone", Arrays.asList(EnumStone.values()));
    private final Supplier<Block> surfaceBlockSupplier;

    public MythriaOre(Material material, String name, double weight, Supplier<Block> surfaceBlockSupplier) {
        super(name, weight, Block.Properties.create(material).hardnessAndResistance(3.0f));
        this.surfaceBlockSupplier = surfaceBlockSupplier;
        setDefaultState(this.stateContainer.getBaseState().with(STONE_TYPE, EnumStone.STONE));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(STONE_TYPE);
    }

    public Block getSurfaceBlock() {
        return surfaceBlockSupplier == null ? null : surfaceBlockSupplier.get();
    }
}
