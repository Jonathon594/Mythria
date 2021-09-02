package me.Jonathon594.Mythria.DataTypes.Origins;

import com.google.common.collect.ImmutableList;
import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.LifeSpanGene;
import me.Jonathon594.Mythria.DataTypes.Genetic.Genetic;
import me.Jonathon594.Mythria.Enum.PerkType;
import me.Jonathon594.Mythria.Items.MythriaItems;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ScavengerOrigin extends Origin {

    public ScavengerOrigin(String name) {
        super(name);
    }

    @Override
    public int getStartingAge(Genetic genetic) {
        return genetic.getLifeSpanGene().getStage(LifeSpanGene.LifeStage.ADOLESCENT);
    }

    @Override
    protected List<ItemStack> getItems() {
        ItemStack bandolier = createBandolier();
        return ImmutableList.of(bandolier);
    }

    @NotNull
    private ItemStack createBandolier() {
        ItemStack bandolier = new ItemStack(MythriaItems.PRIMITIVE_BANDOLIER, 1);
        bandolier.setDisplayName(new StringTextComponent("Old Bandolier")
                .setStyle(Style.EMPTY).mergeStyle(TextFormatting.WHITE));
        bandolier.setDamage(bandolier.getMaxDamage() / 2);
        return bandolier;
    }

    @Override
    protected List<PerkType> getPerkTypes() {
        return ImmutableList.of(PerkType.LEATHER_WORKING);
    }
}
