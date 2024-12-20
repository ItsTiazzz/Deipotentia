package net.tywrapstudios.deipotentia.item.deactivated;

import net.minecraft.item.ItemConvertible;

public interface Deactivated {
    String getActivatorUuid();
    ItemConvertible getActivatedItem();
    default boolean getActivatingStatus() {return true;}
    default void handleBeforeActivation() {}
}
