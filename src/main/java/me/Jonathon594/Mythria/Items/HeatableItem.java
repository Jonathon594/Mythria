package me.Jonathon594.Mythria.Items;

import me.Jonathon594.Mythria.Capability.HeatableItem.HeatableProvider;
import me.Jonathon594.Mythria.Interface.IHeatableItem;
import me.Jonathon594.Mythria.Interface.IShareTagCapability;
import me.Jonathon594.Mythria.Interface.ITickableItem;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HeatableItem extends MythriaItem implements IHeatableItem, ITickableItem, IShareTagCapability {
    public HeatableItem(String name, Properties properties) {
        super(name, properties);
    }

    public HeatableItem(String name, Properties properties, boolean override) {
        super(name, properties, override);
    }

    @Override
    public Collection<? extends ITextComponent> getHeatToolTips(double temperature) {
        List<ITextComponent> lines = new ArrayList<>();
        if (shouldBurnPlayers(temperature)) {
            lines.add(new StringTextComponent(TextFormatting.RED + "Hot!"));
        }
        return lines;
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        ItemStack itemStack = entity.getItem();
        me.Jonathon594.Mythria.Capability.HeatableItem.HeatableItem heatableItem = HeatableProvider.getHeatable(itemStack);
        BlockPos position = entity.getPosition();
        float ambient = entity.world.getBiome(position).getTemperature(position);
        float ambientTempC = MythriaUtil.celciusFromBiomeTemperature(ambient);
        heatableItem.update(ambientTempC, itemStack);

        double temperature = heatableItem.getTemperature();
        if (entity.isInWaterRainOrBubbleColumn() && temperature > ambientTempC) {
            heatableItem.setTemperature(MythriaUtil.thermalConduction(0.1, temperature, ambient));
            entity.playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 1.0f, 1.0f);
        }
        return false;
    }

    public boolean shouldBreakWhenRapidlyCooled() {
        return false;
    }

    @Override
    public void tick(PlayerEntity player, ItemStack is) {
        if (is.getItem() instanceof IHeatableItem) {
            me.Jonathon594.Mythria.Capability.HeatableItem.HeatableItem heatableItem = HeatableProvider.getHeatable(is);
            if (heatableItem == null) return;
            float ambient = player.world.getBiome(player.getPosition()).getTemperature(player.getPosition());
            heatableItem.update(MythriaUtil.celciusFromBiomeTemperature(ambient), is);
        }
    }
}
