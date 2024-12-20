package net.tywrapstudios.deipotentia.mixin;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ReloadCommand;
import net.tywrapstudios.deipotentia.Deipotentia;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ReloadCommand.class)
public abstract class ReloadCommandMixin {
    @Inject(method = "method_13530",
            at = @At(value = "HEAD"))
    private static void deipotentia$reloadConfigOnVanillaReload(CommandContext context, CallbackInfoReturnable<Integer> cir) {
        Deipotentia.CONFIG_MANAGER.loadConfig();
        Deipotentia.LOGGING.info("Reloading Config...");
    }
}
