package me.Jonathon594.Mythria.WorldGen.Feature.Placement;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;
import net.minecraft.world.gen.placement.SimplePlacement;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class GroundCoverPlacement extends SimplePlacement<FeatureSpreadConfig> {
    public GroundCoverPlacement(Codec<FeatureSpreadConfig> codec) {
        super(codec);
    }

    public Stream<BlockPos> getPositions(Random random, FeatureSpreadConfig config, BlockPos pos) {
        List<BlockPos> list = Lists.newArrayList();

        int bound = config.func_242799_a().func_242259_a(random);
        for (int i = 0; i < random.nextInt(bound); ++i) {
            int j = random.nextInt(16) + pos.getX();
            int k = random.nextInt(16) + pos.getZ();
            int l = 256;
            list.add(new BlockPos(j, l, k));
        }

        return list.stream();
    }
}
