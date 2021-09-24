package me.Jonathon594.Mythria.Commands;

import com.mojang.brigadier.CommandDispatcher;
import me.Jonathon594.Mythria.Commands.Arguments.GeneticArgumentType;
import me.Jonathon594.Mythria.DataTypes.Genetic.GeneticType;
import me.Jonathon594.Mythria.Managers.SpawnManager;
import me.Jonathon594.Mythria.MythriaRegistries;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class SetSpawnCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
                Commands.literal("setspawn")
                        .requires((source) -> source.hasPermissionLevel(2))
                        .then(Commands.argument("genetic", new GeneticArgumentType())
                                .suggests(
                                        (context, builder) -> ISuggestionProvider.suggest(MythriaRegistries.GENETICS.getValues().stream().map(
                                                (geneticType -> geneticType.getRegistryName().toString())
                                        ), builder)
                                )
                                .executes((context) -> {
                                    ServerPlayerEntity serverPlayerEntity = context.getSource().asPlayer();
                                    GeneticType genetic = context.getArgument("genetic", GeneticType.class);
                                    SpawnManager.setSpawnLocation(genetic,
                                            (int) serverPlayerEntity.getPosX(),
                                            (int) serverPlayerEntity.getPosZ(),
                                            serverPlayerEntity.getServerWorld().getDimensionKey());
                                    SpawnManager.save();
                                    SpawnManager.init();
                                    context.getSource().sendFeedback(new TranslationTextComponent("commands.setspawn.success", genetic.getDisplayName(), serverPlayerEntity.getPosition().toString()), true);
                                    return 1;
                                })));
    }
}
