package net.tywrapstudios.deipotentia.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.tywrapstudios.deipotentia.Deipotentia;

public class StrangledEffect extends StatusEffect {
    public StrangledEffect() {
        super(StatusEffectCategory.HARMFUL, 2293580);
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        unfreezeEntity(entity);
    }

    private static void unfreezeEntity(LivingEntity entity) {
        entity.setNoGravity(false);
        entity.velocityDirty= false;
        entity.velocityModified = false;
        Deipotentia.LOGGING.debug("Unfroze entity: " + entity);
    }
}