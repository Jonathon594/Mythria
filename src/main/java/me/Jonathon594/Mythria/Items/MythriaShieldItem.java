package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Client.ClientUtil;
import me.Jonathon594.Mythria.Client.Renderer.Items.MythriaShieldItemRenderer;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

import javax.annotation.Nullable;

public class MythriaShieldItem extends ShieldItem {
    private final double weight;
    private final IItemTier tier;

    public MythriaShieldItem(final String name, final IItemTier tier, double weight) {
        super(new Item.Properties().defaultMaxDamage(336 + tier.getMaxUses()).group(ItemGroup.COMBAT).setISTER(() -> MythriaShieldItemRenderer::new));
        setRegistryName(Mythria.MODID, name);
        DispenserBlock.registerDispenseBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);
        this.weight = weight;
        this.tier = tier;

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientUtil.registerShieldProperty(this));
    }

    @Override
    public boolean isShield(ItemStack stack, @Nullable LivingEntity entity) {
        return true;
    }


    public String getMetalName() {
        return tier.toString().toLowerCase();
    }
}
