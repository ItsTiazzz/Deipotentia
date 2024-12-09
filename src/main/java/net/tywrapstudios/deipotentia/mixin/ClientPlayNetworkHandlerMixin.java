package net.tywrapstudios.deipotentia.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {
    @Shadow @Final private MinecraftClient client;
    @Shadow private ClientWorld world;

    @Inject(method = "onEntityStatus",
            at = @At(value = "TAIL"))
    private void dei$addValsoulstranglerTotemLogic(EntityStatusS2CPacket packet, CallbackInfo ci) {
        if (this.client.world != null) {
            Entity entity = packet.getEntity(this.client.world);
            if (packet.getStatus() == 100) { // 100 -> Custom Totem Byte
                this.client.particleManager.addEmitter(entity, ParticleTypes.FLAME, 30);
                this.client.particleManager.addEmitter(entity, ParticleTypes.SOUL_FIRE_FLAME, 30);
                this.world.playSound(
                        entity.getX(),
                        entity.getY(),
                        entity.getZ(),
                        SoundEvents.ITEM_TOTEM_USE,
                        entity.getSoundCategory(),
                        2.0f,
                        0.1f,
                        false
                );
                if (entity == this.client.player) {
                    this.client.gameRenderer.showFloatingItem(new ItemStack(Items.WAXED_OXIDIZED_CUT_COPPER_STAIRS));
                }
            }
        }
    }
}
