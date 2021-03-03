package me.Jonathon594.Mythria.WorldGen.Feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import me.Jonathon594.Mythria.Blocks.BlockGroundCover;
import me.Jonathon594.Mythria.Blocks.MythriaBlocks;
import me.Jonathon594.Mythria.Blocks.MythriaOre;
import me.Jonathon594.Mythria.DataTypes.OreSpawnData;
import me.Jonathon594.Mythria.Enum.EnumStone;
import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.WorldGen.Data.OreVein;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.Tags;

import java.util.*;

public class MythriaOreFeature extends MythriaFeature<NoFeatureConfig> {
    public static final int GEN_RADIUS_CHUNKS = 6;
    public static final Map<String, OreSpawnData> spawnDataMap = new HashMap<>();

    public MythriaOreFeature(String name, Codec<NoFeatureConfig> codec) {
        super(codec);
        setRegistryName(Mythria.MODID, name);
    }

    static void init() {
        registerOverworldSpawnData();
        registerTheNetherSpawnData();
    }

    private static void registerTheNetherSpawnData() {
        RegistryKey<World> world = World.THE_NETHER;
        spawnDataMap.put("Copper Ore Nether", new OreSpawnData(ImmutableList.of(world), MythriaBlocks.COPPER_ORE,
                OreSpawnData.OreSpawnType.VEIN, 60, 0,
                128, 32, 14, 5, 400));
        spawnDataMap.put("Iron Ore Nether", new OreSpawnData(ImmutableList.of(world), MythriaBlocks.IRON_ORE,
                OreSpawnData.OreSpawnType.VEIN, 120, 0,
                128, 32, 28, 9, 240));
        spawnDataMap.put("Gold Ore Nether", new OreSpawnData(ImmutableList.of(world), MythriaBlocks.GOLD_ORE,
                OreSpawnData.OreSpawnType.CLUSTER, 180, 0,
                128, 19, 20, 9, 280));
        spawnDataMap.put("Titanium Ore Nether", new OreSpawnData(ImmutableList.of(world), MythriaBlocks.TITANIUM_ORE,
                OreSpawnData.OreSpawnType.VEIN, 220, 0,
                128, 10, 32, 7, 290));
        spawnDataMap.put("Tungsten Ore Nether", new OreSpawnData(ImmutableList.of(world), MythriaBlocks.TUNGSTEN_ORE,
                OreSpawnData.OreSpawnType.VEIN, 405, 0,
                128, 12, 24, 7, 280));
        spawnDataMap.put("Redstone Ore Nether", new OreSpawnData(world, MythriaBlocks.REDSTONE_ORE,
                OreSpawnData.OreSpawnType.CLUSTER, 360, 0,
                128, 14, 14, 6, 285));
        spawnDataMap.put("Violacium Ore Nether", new OreSpawnData(world, MythriaBlocks.VIOLACIUM_ORE,
                OreSpawnData.OreSpawnType.CLUSTER, 5000, 0,
                18, 6, 18, 6, 200));
    }

    private static void registerOverworldSpawnData() {
        RegistryKey<World> world = World.OVERWORLD;
        spawnDataMap.put("Topaz Ore", new OreSpawnData(world, MythriaBlocks.TOPAZ_ORE,
                OreSpawnData.OreSpawnType.CLUSTER, 50, 1,
                128, 3, 3, 6, 200));
        spawnDataMap.put("Onyx Ore", new OreSpawnData(world, MythriaBlocks.ONYX_ORE,
                OreSpawnData.OreSpawnType.CLUSTER, 80, 1,
                34, 3, 3, 4, 200));
        spawnDataMap.put("Ruby Ore", new OreSpawnData(world, MythriaBlocks.RUBY_ORE,
                OreSpawnData.OreSpawnType.CLUSTER, 50, 37,
                86, 3, 3, 4, 200));
        spawnDataMap.put("Sapphire Ore", new OreSpawnData(world, MythriaBlocks.RUBY_ORE,
                OreSpawnData.OreSpawnType.CLUSTER, 60, 24,
                48, 3, 3, 4, 200));
        spawnDataMap.put("Coal Ore", new OreSpawnData(ImmutableList.of(world), MythriaBlocks.COAL_ORE,
                OreSpawnData.OreSpawnType.VEIN, 20, 44,
                54, 14, 30, 12, 200));
        spawnDataMap.put("Copper Ore", new OreSpawnData(ImmutableList.of(world), MythriaBlocks.COPPER_ORE,
                OreSpawnData.OreSpawnType.VEIN, 60, 45,
                255, 32, 14, 5, 400));
        spawnDataMap.put("Tin Ore", new OreSpawnData(ImmutableList.of(world), MythriaBlocks.TIN_ORE,
                OreSpawnData.OreSpawnType.CLUSTER, 60, 55,
                255, 6, 20, 6, 400));
        spawnDataMap.put("Emerald Ore", new OreSpawnData(ImmutableList.of(world), MythriaBlocks.EMERALD_ORE,
                OreSpawnData.OreSpawnType.CLUSTER, 60, 55,
                255, 3, 3, 6, 400));
        spawnDataMap.put("Iron Ore", new OreSpawnData(ImmutableList.of(world), MythriaBlocks.IRON_ORE,
                OreSpawnData.OreSpawnType.VEIN, 200, 1,
                70, 32, 28, 9, 240));
        spawnDataMap.put("Silver Ore", new OreSpawnData(ImmutableList.of(world), MythriaBlocks.SILVER_ORE,
                OreSpawnData.OreSpawnType.CLUSTER, 400, 20,
                45, 22, 12, 8, 240));
        spawnDataMap.put("Gold Ore", new OreSpawnData(ImmutableList.of(world), MythriaBlocks.GOLD_ORE,
                OreSpawnData.OreSpawnType.CLUSTER, 540, 15,
                55, 19, 20, 9, 280));
        spawnDataMap.put("Platinum Ore", new OreSpawnData(ImmutableList.of(world), MythriaBlocks.PLATINUM_ORE,
                OreSpawnData.OreSpawnType.CLUSTER, 600, 1,
                38, 42, 10, 8, 275));
        spawnDataMap.put("Titanium Ore", new OreSpawnData(ImmutableList.of(world), MythriaBlocks.TITANIUM_ORE,
                OreSpawnData.OreSpawnType.VEIN, 600, 1,
                45, 10, 32, 7, 290));
        spawnDataMap.put("Tungsten Ore", new OreSpawnData(ImmutableList.of(world), MythriaBlocks.TUNGSTEN_ORE,
                OreSpawnData.OreSpawnType.VEIN, 800, 1,
                43, 12, 24, 7, 280));
        spawnDataMap.put("Diamond Ore", new OreSpawnData(world, MythriaBlocks.DIAMOND_ORE,
                OreSpawnData.OreSpawnType.CLUSTER, 780, 1,
                17, 6, 12, 6, 250));
        spawnDataMap.put("Lapis Ore", new OreSpawnData(world, MythriaBlocks.LAPIS_ORE,
                OreSpawnData.OreSpawnType.CLUSTER, 800, 1,
                38, 12, 15, 6, 275));
        spawnDataMap.put("Redstone Ore", new OreSpawnData(world, MythriaBlocks.REDSTONE_ORE,
                OreSpawnData.OreSpawnType.CLUSTER, 340, 1,
                52, 14, 14, 6, 285));
        spawnDataMap.put("Mithril Ore", new OreSpawnData(world, MythriaBlocks.MITHRIL_ORE,
                OreSpawnData.OreSpawnType.CLUSTER, 5000, 1,
                32, 14, 24, 8, 200));
        spawnDataMap.put("Violacium Ore", new OreSpawnData(world, MythriaBlocks.VIOLACIUM_ORE,
                OreSpawnData.OreSpawnType.CLUSTER, 10000, 1,
                18, 6, 18, 6, 200));
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        int chunkX = pos.getX() >> 4;
        int chunkZ = pos.getZ() >> 4;

        int offsetX = pos.getX();
        int offsetZ = pos.getZ();

        List<OreVein> nearbyVeins = getNearbyVeins(chunkX, chunkZ, reader.getSeed(), reader.getWorld());
        if (nearbyVeins.isEmpty()) return false;

        for (OreVein vein : nearbyVeins) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    if (!vein.isInRange(offsetX + x, offsetZ + z))
                        continue;
                    for (int y = Math.max(vein.getSpawnData().getMinY() - 16, 1); y <= Math.min(vein.getSpawnData().getMaxY() + 16, 255); y++) {
                        final BlockPos current = new BlockPos(offsetX + x, y, offsetZ + z);
                        BlockState inBlock = reader.getBlockState(current);
                        EnumStone stone = EnumStone.getFromBlock(inBlock.getBlock());
                        if (stone == null) continue;
                        BlockState ore = vein.getSpawnData().getBlock().getDefaultState().with(MythriaOre.STONE_TYPE, stone);

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

    private void attemptSpawnSurfaceOre(ISeedReader worldIn, Random rand, BlockPos current, BlockState ore) {
        if (rand.nextInt(600) == 0) {
            BlockPos pos = new BlockPos(current.getX(), 256, current.getZ());
            Block surfaceBlock = ((MythriaOre) ore.getBlock()).getSurfaceBlock();
            if (worldIn.getWorld().getDimensionKey().equals(World.THE_NETHER)) pos = pos.offset(Direction.DOWN, 128);
            do {
                BlockState state = worldIn.getBlockState(pos.down());
                Block block = state.getBlock();
                if (isDirt(block) || isStone(block) || isSand(block) || isGravel(block) || isNetherrack(block) || isNylium(block) || isNetherStone(block)) {
                    int distanceY = Math.abs(pos.getY() - current.getY());
                    float chance = 1f - ((float) distanceY / 64f);
                    if (surfaceBlock != null && rand.nextDouble() < chance) {
                        spawnSurfaceOre(worldIn, rand, pos, surfaceBlock);
                    }
                }
                pos = pos.down();
            } while (pos.getY() > 0);
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
            worldIn.setBlockState(surfacePos, state.with(BlockGroundCover.ROTATION, rand.nextInt(16)).with(BlockGroundCover.WATERLOGGED, FluidState.getFluid() == Fluids.WATER), 2);
        }
    }

    private List<OreVein> getNearbyVeins(int chunkX, int chunkZ, long seed, World world) {
        List<OreVein> veins = new ArrayList<>();

        for (int x = -GEN_RADIUS_CHUNKS; x <= GEN_RADIUS_CHUNKS; x++) {
            for (int z = -GEN_RADIUS_CHUNKS; z <= GEN_RADIUS_CHUNKS; z++) {
                List<OreVein> localVeins = getVeinsAtChunk(chunkX + x, chunkZ + z, seed, world);
                if (!localVeins.isEmpty()) veins.addAll(localVeins);
            }
        }
        return veins;
    }

    private List<OreVein> getVeinsAtChunk(int chunkX, int chunkZ, long seed, World world) {
        Random rand = new Random(seed + chunkX * 341873128712L + chunkZ * 132897987541L);
        List<OreVein> veins = new ArrayList<>();

        for (OreSpawnData data : spawnDataMap.values()) {
            if (!data.getDimensions().contains(world.getDimensionKey())) continue;
            if (rand.nextInt(data.getRarity()) == 0) {
                int x = chunkX * 16 + rand.nextInt(16);
                int y = data.getMinY() + rand.nextInt(data.getMaxY() - data.getMinY());
                int z = chunkZ * 16 + rand.nextInt(16);
                BlockPos startPos = new BlockPos(x, y, z);
                veins.add(new OreVein(startPos, data, rand));
            }
        }
        return veins;
    }
}
