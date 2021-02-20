package me.Jonathon594.Mythria.WorldGen.Feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraftforge.common.Tags;

public abstract class MythriaFeature<FC extends IFeatureConfig> extends Feature<FC> {
    public MythriaFeature(Codec codec) {
        super(codec);
    }

    protected boolean isSand(Block block) {
        return BlockTags.SAND.contains(block);
    }

    protected boolean isGravel(Block block) {
        return Tags.Blocks.GRAVEL.contains(block);
    }

    protected boolean isNetherrack(Block block) {
        return Tags.Blocks.NETHERRACK.contains(block);
    }

    protected boolean isNylium(Block block) {
        //todo Tag
        return block.equals(Blocks.CRIMSON_NYLIUM) || block.equals(Blocks.WARPED_NYLIUM);
    }

    protected boolean isNetherStone(Block block) {
        //todo Tag
        return block.equals(Blocks.BASALT) || block.equals(Blocks.BLACKSTONE);
    }
}
