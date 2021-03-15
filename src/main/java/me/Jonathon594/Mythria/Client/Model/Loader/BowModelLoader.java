package me.Jonathon594.Mythria.Client.Model.Loader;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.datafixers.util.Pair;
import me.Jonathon594.Mythria.Capability.Bow.Bow;
import me.Jonathon594.Mythria.Capability.Bow.BowProvider;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.TransformationMatrix;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.*;
import net.minecraftforge.client.model.geometry.IModelGeometry;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

public class BowModelLoader implements IModelGeometry<BowModelLoader> {
    private ImmutableList<RenderMaterial> textures;

    public BowModelLoader(@Nullable ImmutableList<RenderMaterial> textures) {
        this.textures = textures;
    }

    @Override
    public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<RenderMaterial, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation) {
        ImmutableMap<ItemCameraTransforms.TransformType, TransformationMatrix> transformMap =
                PerspectiveMapWrapper.getTransforms(new ModelTransformComposition(owner.getCombinedTransform(), modelTransform));

        return new BakedModel(
                owner.useSmoothLighting(), owner.isShadedInGui(), owner.isSideLit(),
                spriteGetter.apply(owner.resolveTexture("frame0")), new BowOverrideHandler(overrides), textures, transformMap);
    }

    @Override
    public Collection<RenderMaterial> getTextures(IModelConfiguration owner, Function<ResourceLocation, IUnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
        this.textures = getTextures(owner);
        return textures;
    }

    private ImmutableList<RenderMaterial> getTextures(IModelConfiguration owner) {
        ImmutableList.Builder<RenderMaterial> builder = ImmutableList.builder();
        for (int i = 0; i < 4 && owner.isTexturePresent("frame" + i); i++) {
            builder.add(owner.resolveTexture("frame" + i));
        }
        return builder.build();
    }

    public static class BakedModel implements IBakedModel {
        private final boolean isAmbientOcclusion;
        private final boolean isGui3d;
        private final boolean isSideLit;
        private final TextureAtlasSprite particle;
        private final BowOverrideHandler overrides;
        private final ImmutableList<RenderMaterial> textures;
        private final ImmutableMap<ItemCameraTransforms.TransformType, TransformationMatrix> cameraTransforms;
        private int pullState = 0;
        private RenderMaterial arrow;
        private RenderMaterial bowstring;
        private Hand hand = Hand.MAIN_HAND;

        public BakedModel(boolean isAmbientOcclusion, boolean isGui3d, boolean isSideLit, TextureAtlasSprite particle, BowOverrideHandler overrides, ImmutableList<RenderMaterial> textures, ImmutableMap<ItemCameraTransforms.TransformType, TransformationMatrix> cameraTransforms) {
            this.isAmbientOcclusion = isAmbientOcclusion;
            this.isGui3d = isGui3d;
            this.isSideLit = isSideLit;
            this.particle = particle;
            this.overrides = overrides;
            this.cameraTransforms = cameraTransforms;
            this.textures = textures;
        }

        @Override
        public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
            List<BakedQuad> quads = new ArrayList<>(ItemLayerModel.getQuadsForSprite(5, textures.get(pullState).getSprite(), TransformationMatrix.identity(), false));
            if (bowstring != null) {
                TransformationMatrix stringTransform = new TransformationMatrix(
                        new Vector3f(0, 0, 0.25f),
                        Quaternion.ONE,
                        new Vector3f(1f, 1f, 0.5f), null);
                quads.addAll(ItemLayerModel.getQuadsForSprite(0, bowstring.getSprite(), stringTransform, false));
            }
            if (arrow != null) {
                TransformationMatrix arrowTransform = new TransformationMatrix(
                        new Vector3f(1 + ((float) (pullState - 3) / 16f), -((float) (pullState - 4) / 16f), 1f + (hand == Hand.MAIN_HAND ? 0.75f : -0.75f) / 16f),
                        new Quaternion(Vector3f.YP, 180, true),
                        new Vector3f(1f, 1f, 1f), null);
                quads.addAll(ItemLayerModel.getQuadsForSprite(0, arrow.getSprite(), arrowTransform, false));
            }
            return quads;
        }

        @Override
        public boolean isAmbientOcclusion() {
            return isAmbientOcclusion;
        }

        @Override
        public boolean isGui3d() {
            return isGui3d;
        }

        @Override
        public boolean isSideLit() {
            return isSideLit;
        }

        @Override
        public boolean isBuiltInRenderer() {
            return false;
        }

        @Override
        public TextureAtlasSprite getParticleTexture() {
            return particle;
        }

        @Override
        public ItemOverrideList getOverrides() {
            return overrides;
        }

        @Override
        public boolean doesHandlePerspectives() {
            return true;
        }

        @Override
        public IBakedModel handlePerspective(ItemCameraTransforms.TransformType cameraTransformType, MatrixStack mat) {
            switch (cameraTransformType) {
                case FIRST_PERSON_LEFT_HAND:
                case THIRD_PERSON_LEFT_HAND:
                    hand = Hand.OFF_HAND;
                    break;
                case FIRST_PERSON_RIGHT_HAND:
                case THIRD_PERSON_RIGHT_HAND:
                    hand = Hand.MAIN_HAND;
                    break;
            }
            return PerspectiveMapWrapper.handlePerspective(this, cameraTransforms, cameraTransformType, mat);
        }

        public void setPullState(int pullState) {
            this.pullState = pullState;
        }

        public void setArrow(String name) {
            this.arrow = (name.isEmpty() || name.equalsIgnoreCase("air")) ? null : ForgeHooksClient.getBlockMaterial(new MythriaResourceLocation("items/arrows/" + name));
        }

        public void setBowstring(String name, int pullState) {
            this.bowstring = (name.isEmpty() || name.equalsIgnoreCase("air")) ? null : ForgeHooksClient.getBlockMaterial(new MythriaResourceLocation("items/bows/string/" + name + "_" + pullState));
        }
    }

    public static class Loader implements IModelLoader<BowModelLoader> {
        public static final BowModelLoader.Loader INSTANCE = new BowModelLoader.Loader();

        private Loader() {
        }

        @Override
        public void onResourceManagerReload(IResourceManager resourceManager) {

        }

        @Override
        public BowModelLoader read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
            return new BowModelLoader(null);
        }
    }

    public static class BowOverrideHandler extends ItemOverrideList {
        private final ItemOverrideList nested;

        public BowOverrideHandler(ItemOverrideList nested) {
            this.nested = nested;
        }

        @Nullable
        @Override
        public IBakedModel getOverrideModel(IBakedModel model, ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity livingEntity) {
            BakedModel bakedModel = (BakedModel) model;
            Bow bow = BowProvider.getBow(stack);
            boolean flag = livingEntity == null;
            boolean pulling = !flag && livingEntity.getActiveItemStack() == stack;
            float pull = flag ? 0F : pulling ? (float) (stack.getUseDuration() - livingEntity.getItemInUseCount()) / 20f : 0f;
            int pullState = pulling ? getPullState(pull) : 0;
            bakedModel.setPullState(pullState);
            if(bow != null) {
                bakedModel.setArrow(bow.getArrow().getItem().getRegistryName().getPath());
                bakedModel.setBowstring(bow.getBowstring().getItem().getRegistryName().getPath(), pullState);
            }
            return model;
        }

        private int getPullState(float pull) {
            int pullState = 1;
            if (pull >= 0.9) pullState = 3;
            else if (pull > 0.65) pullState = 2;
            return pullState;
        }
    }
}
