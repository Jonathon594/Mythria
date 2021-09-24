package me.Jonathon594.Mythria.Commands.Arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import me.Jonathon594.Mythria.DataTypes.Genetic.GeneticType;
import me.Jonathon594.Mythria.MythriaRegistries;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class GeneticArgumentType implements ArgumentType<GeneticType> {
    public static final DynamicCommandExceptionType BAD_GENETIC_TYPE =
            new DynamicCommandExceptionType((string) ->
                    new TranslationTextComponent("argument.genetic.id.invalid", string));

    @Override
    public GeneticType parse(StringReader reader) throws CommandSyntaxException {
        ResourceLocation location = ResourceLocation.read(reader);
        if (!MythriaRegistries.GENETICS.containsKey(location))
            throw BAD_GENETIC_TYPE.createWithContext(reader, location.toString());
        GeneticType type = MythriaRegistries.GENETICS.getValue(location);
        return type;
    }
}
