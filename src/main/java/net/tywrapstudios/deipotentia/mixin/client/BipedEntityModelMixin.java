package net.tywrapstudios.deipotentia.mixin.client;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.tywrapstudios.deipotentia.registry.DTags;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin {
    @Shadow
    public @Final ModelPart rightArm;
    @Shadow
    public @Final ModelPart leftArm;

    @Inject(
            method = {"positionRightArm", "positionLeftArm"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/entity/model/CrossbowPosing;hold(Lnet/minecraft/client/model/ModelPart;Lnet/minecraft/client/model/ModelPart;Lnet/minecraft/client/model/ModelPart;Z)V",
                    shift = At.Shift.AFTER
            ),
            cancellable = true
    )
    public void deipotentia$poseArms(LivingEntity entity, CallbackInfo ci) {
        if(entity.getMainHandStack().isIn(DTags.Items.PLUSH.get()) || entity.getOffHandStack().isIn(DTags.Items.PLUSH.get())) {
            this.rightArm.pitch = -0.95F;
            this.rightArm.yaw = (float) (-Math.PI / 8);
            this.leftArm.pitch = -0.90F;
            this.leftArm.yaw = (float) (Math.PI / 8);
            ci.cancel();
        }
    }
}
