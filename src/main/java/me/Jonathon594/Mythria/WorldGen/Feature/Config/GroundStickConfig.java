package me.Jonathon594.Mythria.WorldGen.Feature.Config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class GroundStickConfig extends ProbabilityConfig {
    public static final Codec<GroundStickConfig> CODEC = RecordCodecBuilder.create((builder) -> {
        return builder.group(Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter((config) -> {
            return config.probability;
        }), BlockState.CODEC.fieldOf("leaves").forGetter(config -> {
            return config.leaves;
        }), BlockState.CODEC.fieldOf("stick").forGetter((config) -> {
            return config.stick;
        })).apply(builder, (probability1, leaves1, stick) -> new GroundStickConfig(probability1, leaves1, stick));
    });
    public final BlockState leaves;
    public final BlockState stick;

    public GroundStickConfig(float probability, BlockState leaves, BlockState stick) {
        super(probability);
        this.leaves = leaves;
        this.stick = stick;
    }
}
