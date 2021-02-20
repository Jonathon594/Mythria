package me.Jonathon594.Mythria.Client.Renderer.Items;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.datafixers.util.Pair;
import me.Jonathon594.Mythria.Client.Model.MythriaModelShield;
import me.Jonathon594.Mythria.Items.MythriaShieldItem;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.BannerTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.List;

public class MythriaShieldItemRenderer extends ItemStackTileEntityRenderer {
    public static final HashMap<ResourceLocation, RenderMaterial> SHIELD_BASE_CACHE = new HashMap<>();
    public static final HashMap<ResourceLocation, RenderMaterial> SHIELD_BASE_NO_PATTERN_CACHE = new HashMap<>();
    private final MythriaModelShield modelShield = new MythriaModelShield();

    @Override
    public void func_239207_a_(ItemStack stack, ItemCameraTransforms.TransformType p_239207_2_, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        if (stack.getItem() instanceof MythriaShieldItem) {
            MythriaShieldItem shield = (MythriaShieldItem) stack.getItem();

            matrixStack.push();
            matrixStack.scale(1.0F, -1.0F, -1.0F);
            boolean hasPattern = stack.getChildTag("BlockEntityTag") != null;
            ResourceLocation location = hasPattern ? new MythriaResourceLocation("entity/" + shield.getMetalName() + "_shield_base") :
                    new MythriaResourceLocation("entity/" + shield.getMetalName() + "_shield_base_nopattern");
            RenderMaterial material = getShieldMaterial(location, hasPattern);
            IVertexBuilder ivertexbuilder = material.getSprite().wrapBuffer(ItemRenderer.getBuffer(buffer, this.modelShield.getRenderType(material.getAtlasLocation()), false, stack.hasEffect()));
            this.modelShield.getHandle().render(matrixStack, ivertexbuilder, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);

            if (hasPattern) {
                List<Pair<BannerPattern, DyeColor>> list = BannerTileEntity.getPatternColorData(ShieldItem.getColor(stack), BannerTileEntity.getPatternData(stack));
                BannerTileEntityRenderer.func_230180_a_(matrixStack, buffer, combinedLight, combinedOverlay, this.modelShield.getPlate(), material, false, list);
            } else {
                this.modelShield.getPlate().render(matrixStack, ivertexbuilder, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
            }
            matrixStack.pop();
        }
    }

    public RenderMaterial getShieldMaterial(ResourceLocation location, boolean pattern) {
        if (pattern) {
            return getShieldMaterial(SHIELD_BASE_CACHE, location);
        } else {
            return getShieldMaterial(SHIELD_BASE_NO_PATTERN_CACHE, location);
        }
    }

    private RenderMaterial getShieldMaterial(HashMap<ResourceLocation, RenderMaterial> cache, ResourceLocation location) {
        if (!cache.containsKey(location))
            cache.put(location, new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, location));

        return cache.get(location);
    }
}
