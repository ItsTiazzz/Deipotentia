package net.tywrapstudios.deipotentia.item.deactivated;

import net.minecraft.item.ItemConvertible;
import net.tywrapstudios.deipotentia.registry.DRegistry;

public class DeactivatedBH extends DeactivatedItem {
    public DeactivatedBH(Settings settings) {
        super(settings);
    }

    @Override
    public String getActivatorUuid() {
        return "7a4a8f5e-acd5-41d7-9e3d-998bfd623c3d";
    }

    @Override
    public ItemConvertible getActivatedItem() {
        return DRegistry.DItems.BLAHAJ_GODS;
    }
}
