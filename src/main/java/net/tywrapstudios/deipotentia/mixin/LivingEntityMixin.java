package net.tywrapstudios.deipotentia.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.tywrapstudios.deipotentia.registry.DRegistry;
import net.tywrapstudios.deipotentia.item.ValSoulStranglerItem;
import net.tywrapstudios.deipotentia.util.NbtUtilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "tryUseTotem",
            at = @At("HEAD"),
            cancellable = true)
    private void dei$tryUseValsoulstrangler(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            cir.setReturnValue(false);
        } else {
            ItemStack itemStack = null;
            LivingEntity entity = (LivingEntity) (Object) this;
            if (entity instanceof ServerPlayerEntity player) {
                for (int i = 0; i < player.getInventory().size(); i++) {
                    ItemStack itemStack2 = player.getInventory().getStack(i);
                    if (itemStack2.isOf(DRegistry.DItems.VALSOULSTRANGLER) && NbtUtilities.getEnabled(itemStack2)) {
                        itemStack = itemStack2.copy();
                        itemStack2.damage(1, player, player1 -> player1.sendToolBreakStatus(player1.getActiveHand()));
                        break;
                    }
                }

                if (itemStack != null) {
                    if (NbtUtilities.getEnabled(itemStack)) {
                        ((ValSoulStranglerItem) itemStack.getItem()).deathLogic(player);
                        ((ValSoulStranglerItem) itemStack.getItem()).totemPopLogic(player);
                    }
                }

                cir.setReturnValue(itemStack != null);
            }
        }
    }
}