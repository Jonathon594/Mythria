package me.Jonathon594.Mythria.Commands;

import com.mojang.brigadier.CommandDispatcher;
import me.Jonathon594.Mythria.Commands.Arguments.GeneticTypeArgument;
import me.Jonathon594.Mythria.DataTypes.Genetic.GeneticType;
import me.Jonathon594.Mythria.Managers.SpawnManager;
import me.Jonathon594.Mythria.MythriaRegistries;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.entity.player.ServerPlayerEntity;

public class SetSpawnCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
                Commands.literal("setspawn")
                        .requires((source) -> source.hasPermissionLevel(2))
                        .then(Commands.argument("genetic", new GeneticTypeArgument())
                                .suggests(
                                        (context, builder) -> ISuggestionProvider.suggest(MythriaRegistries.GENETICS.getValues().stream().map(
                                                (geneticType -> geneticType.getRegistryName().toString())
                                        ), builder)
                                )
                                .executes((context) -> {
                                    ServerPlayerEntity serverPlayerEntity = context.getSource().asPlayer();
                                    SpawnManager.setSpawnLocation(context.getArgument("genetic", GeneticType.class),
                                            (int) serverPlayerEntity.getPosX(),
                                            (int) serverPlayerEntity.getPosZ(),
                                            serverPlayerEntity.getServerWorld().getDimensionKey());
                                    SpawnManager.save();
                                    SpawnManager.init();
                                    return 1;
                                })));
    }
}
