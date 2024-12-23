package net.tywrapstudios.deipotentia.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameMode;
import net.tywrapstudios.deipotentia.Deipotentia;
import net.tywrapstudios.deipotentia.component.DeipotentiaComponents;
import net.tywrapstudios.deipotentia.component.PlayerViewingComponent;

public class StuckCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, access, env) -> register$1(dispatcher));
    }

    private static void register$1(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal(Deipotentia.CONFIG_MANAGER.getConfig().stuck_command_id)
                        .requires(ServerCommandSource::isExecutedByPlayer)
                        .executes(StuckCommand::execute));
    }

    private static int execute(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        assert player != null;

        player.setNoGravity(false);
        player.setNoDrag(false);
        player.changeGameMode(GameMode.SURVIVAL);
        player.setCameraEntity(player);
        player.stopRiding();
        return 1;
    }
}
