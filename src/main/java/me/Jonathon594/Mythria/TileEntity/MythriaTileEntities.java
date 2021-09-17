package me.Jonathon594.Mythria.TileEntity;

import me.Jonathon594.Mythria.Blocks.BlockGroundCover;
import me.Jonathon594.Mythria.Blocks.MythriaBlocks;
import me.Jonathon594.Mythria.Client.Renderer.TileEntity.CampfireTileEntityRenderer;
import me.Jonathon594.Mythria.Client.Renderer.TileEntity.GroundCoverTileEntityRenderer;
import me.Jonathon594.Mythria.Client.Renderer.TileEntity.PitFurnaceTileEntityRenderer;
import me.Jonathon594.Mythria.Client.Renderer.TileEntity.TanningRackTileEntityRenderer;
import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MythriaTileEntities {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Mythria.MODID);

    public static final RegistryObject<TileEntityType<GroundCoverTileEntity>> GROUND_COVER =
            TILE_ENTITY_TYPES.register("ground_cover", () -> TileEntityType.Builder.create(GroundCoverTileEntity::new, MythriaUtil.getAllBlocksOfType(BlockGroundCover.class)).build(null));

    public static final RegistryObject<TileEntityType<CampfireTileEntity>> CAMPFIRE =
            TILE_ENTITY_TYPES.register("campfire", () -> TileEntityType.Builder.create(CampfireTileEntity::new, MythriaBlocks.CAMPFIRE).build(null));

    public static final RegistryObject<TileEntityType<PitFurnaceTileEntity>> PIT_FURNACE =
            TILE_ENTITY_TYPES.register("pit_furnace", () -> TileEntityType.Builder.create(PitFurnaceTileEntity::new, MythriaBlocks.PIT_FURNACE).build(null));

    public static final RegistryObject<TileEntityType<StoneFurnaceTileEntity>> STONE_FURNACE =
            TILE_ENTITY_TYPES.register("stone_furnace", () -> TileEntityType.Builder.create(StoneFurnaceTileEntity::new, MythriaBlocks.STONE_FURNACE).build(null));

    public static final RegistryObject<TileEntityType<CobblestoneFurnaceTileEntity>> COBBLESTONE_FURNACE =
            TILE_ENTITY_TYPES.register("cobblestone_furnace", () -> TileEntityType.Builder.create(CobblestoneFurnaceTileEntity::new, MythriaBlocks.COBBLESTONE_FURNACE).build(null));

    public static final RegistryObject<TileEntityType<TanningRackTileEntity>> TANNING_RACK =
            TILE_ENTITY_TYPES.register("tanning_rack", () -> TileEntityType.Builder.create(TanningRackTileEntity::new, MythriaBlocks.OAK_TANNING_RACK).build(null));

    public static final RegistryObject<TileEntityType<MythriaBarrelTileEntity>> THATCH_BASKET =
            TILE_ENTITY_TYPES.register("thatch_basket", () -> TileEntityType.Builder.create(MythriaBarrelTileEntity::new, MythriaBlocks.THATCH_BASKET).build(null));

    public static void registerRendersClient() {
        ClientRegistry.bindTileEntityRenderer(GROUND_COVER.get(), GroundCoverTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(CAMPFIRE.get(), CampfireTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TANNING_RACK.get(), TanningRackTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(PIT_FURNACE.get(), PitFurnaceTileEntityRenderer::new);
    }
}
