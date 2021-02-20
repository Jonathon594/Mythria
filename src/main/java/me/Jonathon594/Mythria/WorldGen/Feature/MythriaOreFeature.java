package me.Jonathon594.Mythria.WorldGen.Feature;

import com.mojang.serialization.Codec;
import me.Jonathon594.Mythria.Blocks.BlockGroundCover;
import me.Jonathon594.Mythria.Blocks.MythriaBlocks;
import me.Jonathon594.Mythria.DataTypes.OreSpawnData;
import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.WorldGen.Data.OreVein;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.Tags;

import java.util.*;

public class MythriaOreFeature extends Feature<NoFeatureConfig> {
    public static final int GEN_RADIUS_CHUNKS = 6;
    public static final int MAX_RADIUS = 80;
    public static Map<String, OreSpawnData> spawnDataMap = new HashMap<>();

    public MythriaOreFeature(String name, Codec<NoFeatureConfig> codec) {
        super(codec);
        setRegistryName(Mythria.MODID, name);
    }

    static void init() {
        spawnDataMap.put("Topaz Ore", new OreSpawnData(MythriaBlocks.TOPAZ_ORE, OreSpawnData.OreSpawnType.CLUSTER, 50, 1,
                128, 3, 3, World.OVERWORLD, 6, 200));
        spawnDataMap.put("Onyx Ore", new OreSpawnData(MythriaBlocks.ONYX_ORE, OreSpawnData.OreSpawnType.CLUSTER, 80, 1,
                34, 3, 3, World.OVERWORLD, 4, 200));
        spawnDataMap.put("Ruby Ore", new OreSpawnData(MythriaBlocks.RUBY_ORE, OreSpawnData.OreSpawnType.CLUSTER, 50, 37,
                86, 3, 3, World.OVERWORLD, 4, 200));
        spawnDataMap.put("Sapphire Ore", new OreSpawnData(MythriaBlocks.RUBY_ORE, OreSpawnData.OreSpawnType.CLUSTER, 60, 24,
                48, 3, 3, World.OVERWORLD, 4, 200));
        spawnDataMap.put("Coal Ore", new OreSpawnData(Blocks.COAL_ORE, OreSpawnData.OreSpawnType.VEIN, 20, 44,
                54, 14, 30, World.OVERWORLD, 12, 200));
        spawnDataMap.put("Copper Ore", new OreSpawnData(MythriaBlocks.COPPER_ORE, OreSpawnData.OreSpawnType.VEIN, 60, 45,
                255, 32, 14, World.OVERWORLD, 5, 400));
        spawnDataMap.put("Tin Ore", new OreSpawnData(MythriaBlocks.TIN_ORE, OreSpawnData.OreSpawnType.CLUSTER, 60, 55,
                255, 6, 20, World.OVERWORLD, 6, 400));
        spawnDataMap.put("Emerald Ore", new OreSpawnData(Blocks.EMERALD_ORE, OreSpawnData.OreSpawnType.CLUSTER, 60, 55,
                255, 3, 3, World.OVERWORLD, 6, 400));
        spawnDataMap.put("Iron Ore", new OreSpawnData(MythriaBlocks.IRON_ORE, OreSpawnData.OreSpawnType.VEIN, 200, 1,
                70, 32, 28, World.OVERWORLD, 9, 240));
        spawnDataMap.put("Silver Ore", new OreSpawnData(MythriaBlocks.SILVER_ORE, OreSpawnData.OreSpawnType.CLUSTER, 400, 20,
                45, 22, 12, World.OVERWORLD, 8, 240));
        spawnDataMap.put("Gold Ore", new OreSpawnData(MythriaBlocks.GOLD_ORE, OreSpawnData.OreSpawnType.CLUSTER, 540, 15,
                55, 19, 20, World.OVERWORLD, 9, 280));
        spawnDataMap.put("Platinum Ore", new OreSpawnData(MythriaBlocks.PLATINUM_ORE, OreSpawnData.OreSpawnType.CLUSTER, 600, 1,
                38, 42, 10, World.OVERWORLD, 8, 275));
        spawnDataMap.put("Titanium Ore", new OreSpawnData(MythriaBlocks.TITANIUM_ORE, OreSpawnData.OreSpawnType.VEIN, 600, 1,
                45, 10, 32, World.OVERWORLD, 7, 290));
        spawnDataMap.put("Tungsten Ore", new OreSpawnData(MythriaBlocks.TUNGSTEN_ORE, OreSpawnData.OreSpawnType.VEIN, 800, 1,
                43, 12, 24, World.OVERWORLD, 7, 280));
        spawnDataMap.put("Diamond Ore", new OreSpawnData(Blocks.DIAMOND_ORE, OreSpawnData.OreSpawnType.CLUSTER, 780, 1,
                17, 6, 12, World.OVERWORLD, 6, 250));
        spawnDataMap.put("Lapis Ore", new OreSpawnData(Blocks.LAPIS_ORE, OreSpawnData.OreSpawnType.CLUSTER, 800, 1,
                38, 12, 15, World.OVERWORLD, 6, 275));
        spawnDataMap.put("Redstone Ore", new OreSpawnData(Blocks.REDSTONE_ORE, OreSpawnData.OreSpawnType.CLUSTER, 340, 1,
                52, 14, 14, World.OVERWORLD, 6, 285));
        spawnDataMap.put("Mithril Ore", new OreSpawnData(MythriaBlocks.MITHRIL_ORE, OreSpawnData.OreSpawnType.CLUSTER, 5000, 1,
                32, 14, 24, World.OVERWORLD, 8, 200));
        spawnDataMap.put("Violacium Ore", new OreSpawnData(MythriaBlocks.VIOLACIUM_ORE, OreSpawnData.OreSpawnType.CLUSTER, 10000, 1,
                18, 6, 18, World.OVERWORLD, 6, 200));
    }

    public static BlockGroundCover getGroundCoverFromOre(Block block) {
        if (block.equals(Blocks.COAL_ORE)) return MythriaBlocks.GROUND_COAL;
        if (block.equals(MythriaBlocks.COPPER_ORE)) return MythriaBlocks.GROUND_COPPER;
        if (block.equals(MythriaBlocks.TIN_ORE)) return MythriaBlocks.GROUND_TIN;
        if (block.equals(MythriaBlocks.IRON_ORE)) return MythriaBlocks.GROUND_IRON;
        if (block.equals(MythriaBlocks.SILVER_ORE)) return MythriaBlocks.GROUND_SILVER;
        if (block.equals(MythriaBlocks.GOLD_ORE)) return MythriaBlocks.GROUND_GOLD;
        if (block.equals(MythriaBlocks.PLATINUM_ORE)) return MythriaBlocks.GROUND_PLATINUM;
        if (block.equals(MythriaBlocks.TITANIUM_ORE)) return MythriaBlocks.GROUND_TITANIUM;
        if (block.equals(MythriaBlocks.TUNGSTEN_ORE)) return MythriaBlocks.GROUND_TUNGSTEN;
        if (block.equals(MythriaBlocks.MITHRIL_ORE)) return MythriaBlocks.GROUND_MITHRIL;
        if (block.equals(MythriaBlocks.VIOLACIUM_ORE)) return MythriaBlocks.GROUND_VIOLACIUM;
        return null;
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        int chunkX = pos.getX() >> 4;
        int chunkZ = pos.getZ() >> 4;

        int offsetX = pos.getX();
        int offsetZ = pos.getZ();

        List<OreVein> nearbyVeins = getNearbyVeins(chunkX, chunkZ, reader.getSeed());
        if (nearbyVeins.isEmpty()) return false;

        for (OreVein vein : nearbyVeins) {
            if (vein.getSpawnData().getDimension() != reader.getWorld().getDimensionKey()) continue;
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    if (!vein.isInRange(offsetX + x, offsetZ + z))
                        continue;
                    for (int y = Math.max(vein.getSpawnData().getMinY() - 16, 1); y <= Math.min(vein.getSpawnData().getMaxY() + 16, 255); y++) {
                        final BlockPos current = new BlockPos(offsetX + x, y, offsetZ + z);
                        BlockState inBlock = reader.getBlockState(current);
                        if (!inBlock.getBlock().equals(Blocks.STONE)) continue;
                        BlockState ore = vein.getSpawnData().getBlock().getDefaultState();

                        if (rand.nextDouble() < vein.getGenerationChance(current)) {
                            reader.setBlockState(current, ore, 2);

                            attemptSpawnSurfaceOre(reader, rand, current, ore);
                        }
                    }
                }
            }
        }
        return true;
    }

    private void attemptSpawnSurfaceOre(IWorld worldIn, Random rand, BlockPos current, BlockState ore) {
        if (rand.nextInt(600) == 0) {
            BlockPos surfacePos = worldIn.getHeight(Heightmap.Type.MOTION_BLOCKING, current);
            int distanceY = surfacePos.getY() - current.getY();
            float chance = 1f - ((float) distanceY / 64f);
            Block b = getGroundCoverFromOre(ore.getBlock());
            if (b != null && rand.nextDouble() < chance) {
                while (true) {
                    checkBlock:
                    {
                        if (surfacePos.getY() > 0) {
                            if (worldIn.isAirBlock(surfacePos.down())) {
                                break checkBlock;
                            }

                            Block block = worldIn.getBlockState(surfacePos.down()).getBlock();
                            if (!isDirt(block) && !isStone(block) && !isSand(block) && !isGravel(block)) {
                                break checkBlock;
                            }
                        }

                        if (surfacePos.getY() == 0) {
                            return;
                        }

                        spawnSurfaceOre(worldIn, rand, surfacePos, b);
                    }
                    surfacePos = surfacePos.down();
                }
            }
        }
    }

    protected boolean isSand(Block block) {
        return BlockTags.SAND.contains(block);
    }

    protected boolean isGravel(Block block) {
        return Tags.Blocks.GRAVEL.contains(block);
    }

    private void spawnSurfaceOre(IWorld worldIn, Random rand, BlockPos surfacePos, Block b) {
        FluidState FluidState = worldIn.getFluidState(surfacePos);
        BlockState state = b.getDefaultState();
        if (state.isValidPosition(worldIn, surfacePos) && worldIn.getBlockState(surfacePos).getMaterial().isReplaceable()) {
            worldIn.setBlockState(surfacePos, state.with(BlockGroundCover.ROTATION, rand.nextInt(16)).with(BlockGroundCover.WATERLOGGED, Boolean.valueOf(FluidState.getFluid() == Fluids.WATER)), 2);
        }
    }

    private List<OreVein> getNearbyVeins(int chunkX, int chunkZ, long seed) {
        List<OreVein> veins = new ArrayList<>();

        for (int x = -GEN_RADIUS_CHUNKS; x <= GEN_RADIUS_CHUNKS; x++) {
            for (int z = -GEN_RADIUS_CHUNKS; z <= GEN_RADIUS_CHUNKS; z++) {
                List<OreVein> localVeins = getVeinsAtChunk(chunkX + x, chunkZ + z, seed);
                if (!localVeins.isEmpty()) veins.addAll(localVeins);
            }
        }
        return veins;
    }

    private List<OreVein> getVeinsAtChunk(int chunkX, int chunkZ, long seed) {
        Random rand = new Random(seed + chunkX * 341873128712L + chunkZ * 132897987541L);
        List<OreVein> veins = new ArrayList<>();

        for (OreSpawnData data : spawnDataMap.values()) {
            if (rand.nextInt(data.getRarity()) == 0) {
                int x = chunkX * 16 + rand.nextInt(16);
                int z = chunkZ * 16 + rand.nextInt(16);
                BlockPos startPos = new BlockPos(
                        x,
                        data.getMinY() + rand.nextInt(data.getMaxY() - data.getMinY()),
                        z
                );
                veins.add(new OreVein(startPos, data, rand));
            }
        }
        return veins;
    }
}
