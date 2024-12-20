package net.tywrapstudios.deipotentia.item.deactivated;

import net.minecraft.item.ItemConvertible;
import net.tywrapstudios.deipotentia.Deipotentia;
import net.tywrapstudios.deipotentia.registry.DRegistry;

public class DeactivatedAG extends DeactivatedItem{
    public DeactivatedAG(Settings settings) {
        super(settings);
    }

    @Override
    public String getActivatorUuid() {
        return Deipotentia.CONFIG_MANAGER.getConfig().angelUuid;
    }

    @Override
    public ItemConvertible getActivatedItem() {
        return DRegistry.DItems.ANGELS_GUARD;
    }
}
