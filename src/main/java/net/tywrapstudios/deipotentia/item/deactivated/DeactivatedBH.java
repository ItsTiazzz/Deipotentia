package net.tywrapstudios.deipotentia.item.deactivated;

import net.minecraft.item.ItemConvertible;
import net.tywrapstudios.deipotentia.Deipotentia;
import net.tywrapstudios.deipotentia.registry.DRegistry;

public class DeactivatedBH extends DeactivatedItem {
    public DeactivatedBH(Settings settings) {
        super(settings);
    }

    @Override
    public String getActivatorUuid() {
        return Deipotentia.CONFIG_MANAGER.getConfig().godUuid;
    }

    @Override
    public ItemConvertible getActivatedItem() {
        return DRegistry.DItems.BLAHAJ_GODS;
    }
}
