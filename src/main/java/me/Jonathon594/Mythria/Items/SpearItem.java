package me.Jonathon594.Mythria.Items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import me.Jonathon594.Mythria.Client.ClientUtil;
import me.Jonathon594.Mythria.Client.Renderer.Items.SpearItemRenderer;
import me.Jonathon594.Mythria.Entity.SpearEntity;
import me.Jonathon594.Mythria.Interface.IItemData;
import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class SpearItem extends TieredItem implements IItemData {
    private final MythriaResourceLocation textureLocation;
    private final ImmutableMultimap<Attribute, AttributeModifier> attributes;
    private final double weight;

    public SpearItem(String name, IItemTier tier, double weight, double extraAttackSpeed, double extraAttackDamage) {
        super(tier, new Item.Properties().group(ItemGroup.COMBAT).maxDamage(tier.getMaxUses()).setISTER(() -> SpearItemRenderer::new));
        this.weight = weight;
        setRegistryName(Mythria.MODID, name);
        textureLocation = new MythriaResourceLocation("textures/entity/" + name + ".png");

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", 4.0D + tier.getAttackDamage() + extraAttackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", extraAttackSpeed - 2.9F, AttributeModifier.Operation.ADDITION));
        this.attributes = builder.build();

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            ClientUtil.registerSpearProperty(this);
        });
    }

    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity) entityLiving;
            int i = this.getUseDuration(stack) - timeLeft;
            if (i >= 10) {
                if (!worldIn.isRemote) {
                    stack.damageItem(1, playerentity, (p_220047_1_) -> {
                        p_220047_1_.sendBreakAnimation(entityLiving.getActiveHand());
                    });
                    SpearEntity spear = new SpearEntity(worldIn, playerentity, stack);
                    spear.func_234612_a_(playerentity, playerentity.rotationPitch, playerentity.rotationYaw, 0.0F, 2.5F, 1.0F);
                    if (playerentity.abilities.isCreativeMode) {
                        spear.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                    }

                    worldIn.addEntity(spear);
                    worldIn.playMovingSound(null, spear, SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    if (!playerentity.abilities.isCreativeMode) {
                        playerentity.inventory.deleteStack(stack);
                    }
                }

                playerentity.addStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (itemstack.getDamage() >= itemstack.getMaxDamage() - 1) {
            return ActionResult.resultFail(itemstack);
        } else {
            playerIn.setActiveHand(handIn);
            return ActionResult.resultConsume(itemstack);
        }
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        return equipmentSlot.equals(EquipmentSlotType.MAINHAND) ? attributes : super.getAttributeModifiers(equipmentSlot);
    }

    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if ((double) state.getBlockHardness(worldIn, pos) != 0.0D) {
            stack.damageItem(2, entityLiving, (p_220046_0_) -> {
                p_220046_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
        }

        return true;
    }

    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damageItem(1, attacker, (p_220048_0_) -> {
            p_220048_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
        });
        return true;
    }

    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        return !player.isCreative();
    }

    @Override
    public double getWeight() {
        return weight;
    }

    public ResourceLocation getThrownEntityTexture() {
        return textureLocation;
    }
}
