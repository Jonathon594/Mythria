package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Capability.Bow.Bow;
import me.Jonathon594.Mythria.Capability.Bow.BowProvider;
import me.Jonathon594.Mythria.Client.ClientUtil;
import me.Jonathon594.Mythria.Interface.IItemData;
import me.Jonathon594.Mythria.Mythria;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.DistExecutor;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class MythriaBowItem extends BowItem implements IItemData {
    public static final Predicate<ItemStack> MYTHRIA_ARROWS = (stack) -> {
        return stack.getItem() instanceof MythriaArrowItem;
    };
    private final double weight;

    public MythriaBowItem(String name, IItemTier tier, double weight) {
        super(new Item.Properties().maxDamage(tier.getMaxUses()).group(ItemGroup.COMBAT));
        this.weight = weight;
        setRegistryName(Mythria.MODID, name);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            ClientUtil.registerBowProperty(this);
        });
    }

    public static float getArrowVelocity(int charge) {
        float f = (float) charge / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    public boolean onReloadAmmo(ItemStack stack, ServerPlayerEntity player, Hand hand) {
        if(player.getItemInUseCount() > 0) return false;
        Bow bow = BowProvider.getBow(stack);
        if (!bow.getArrow().isEmpty()) return false;
        ItemStack ammo = player.findAmmo(stack);
        if (player.abilities.isCreativeMode) ammo = new ItemStack(MythriaItems.OAK_ARROW);
        if (ammo.isEmpty()) return false;
        if (bow.getBowstring().isEmpty()) return false;
        bow.setArrow(ammo.split(1));
        player.setHeldItem(hand, stack);
        boolean flag = !bow.getArrow().isEmpty();
        ActionResult<ItemStack> actionResult = ForgeEventFactory.onArrowNock(bow.getArrow(), player.world, player, hand, flag);
        if (actionResult != null) return actionResult.getType().isSuccess();
        player.world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(),
                SoundEvents.ITEM_CROSSBOW_LOADING_END, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + 0.5F);
        return flag;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        Bow bow = BowProvider.getBow(itemstack);
        if (bow.getBowstring().isEmpty()) return ActionResult.resultFail(itemstack);
        playerIn.setActiveHand(handIn);
        playerIn.world.playSound(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(),
                SoundEvents.ITEM_CROSSBOW_LOADING_START, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + 0.5F);
        return ActionResult.resultConsume(itemstack);
    }

    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity) entityLiving;
            boolean flag = playerentity.abilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
            Bow bow = BowProvider.getBow(stack);
            ItemStack arrow = bow.getArrow();

            if (arrow.isEmpty()) {
                worldIn.playSound(null, playerentity.getPosX(), playerentity.getPosY(), playerentity.getPosZ(),
                        SoundEvents.ITEM_CROSSBOW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + 0.5F);
                stack.damageItem(3, playerentity, (p_220009_1_) -> {
                    p_220009_1_.sendBreakAnimation(playerentity.getActiveHand());
                });
                return;
            }

            int i = this.getUseDuration(stack) - timeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, playerentity, i, !arrow.isEmpty() || flag);
            if (i < 0) return;

            if (!arrow.isEmpty() || flag) {
                if (arrow.isEmpty()) {
                    arrow = new ItemStack(Items.ARROW);
                }

                float f = getArrowVelocity(i);
                if (!((double) f < 0.1D)) {
                    boolean isInfinite = playerentity.abilities.isCreativeMode || (arrow.getItem() instanceof ArrowItem && ((ArrowItem) arrow.getItem()).isInfinite(arrow, stack, playerentity));
                    if (!worldIn.isRemote) {
                        ArrowItem arrowitem = (ArrowItem) (arrow.getItem() instanceof ArrowItem ? arrow.getItem() : Items.ARROW);
                        AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(worldIn, arrow, playerentity);
                        abstractarrowentity = customArrow(abstractarrowentity);
                        abstractarrowentity.func_234612_a_(playerentity, playerentity.rotationPitch, playerentity.rotationYaw, 0.0F, f * 3.0F, 1.0F);
                        if (f == 1.0F) {
                            abstractarrowentity.setIsCritical(true);
                        }

                        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
                        if (j > 0) {
                            abstractarrowentity.setDamage(abstractarrowentity.getDamage() + (double) j * 0.5D + 0.5D);
                        }

                        int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
                        if (k > 0) {
                            abstractarrowentity.setKnockbackStrength(k);
                        }

                        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
                            abstractarrowentity.setFire(100);
                        }

                        stack.damageItem(1, playerentity, (p_220009_1_) -> {
                            p_220009_1_.sendBreakAnimation(playerentity.getActiveHand());
                        });
                        if (isInfinite || playerentity.abilities.isCreativeMode && (arrow.getItem() == Items.SPECTRAL_ARROW || arrow.getItem() == Items.TIPPED_ARROW)) {
                            abstractarrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                        }

                        worldIn.addEntity(abstractarrowentity);
                    }

                    worldIn.playSound(null, playerentity.getPosX(), playerentity.getPosY(), playerentity.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    if (!isInfinite && !playerentity.abilities.isCreativeMode) {
                        arrow.shrink(1);
                    }

                    playerentity.addStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    public Predicate<ItemStack> getInventoryAmmoPredicate() {
        return MYTHRIA_ARROWS;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new BowProvider();
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
    }

    @Nullable
    @Override
    public CompoundNBT getShareTag(ItemStack stack) {
        CompoundNBT tag = stack.getOrCreateTag();
        tag.put("arrow", BowProvider.getBow(stack).getArrow().serializeNBT());
        return tag;
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, PlayerEntity player) {
        Bow bow = BowProvider.getBow(item);
        player.dropItem(bow.getArrow(), false);
        bow.setArrow(ItemStack.EMPTY);
        return true;
    }
}
