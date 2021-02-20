package me.Jonathon594.Mythria.WorldGen.Feature.Config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class GroundCoverConfig extends ProbabilityConfig {
    public static final Codec<GroundCoverConfig> CODEC = RecordCodecBuilder.create((builder) -> {
        return builder.group(
                Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter((config) -> config.probability),
                BlockState.CODEC.fieldOf("block").forGetter((config) -> config.block))
                .apply(builder, (probability, block) -> new GroundCoverConfig(probability, block));
    });
    public final BlockState block;

    public GroundCoverConfig(float probability, BlockState block) {
        super(probability);
        this.block = block;
    }
}
