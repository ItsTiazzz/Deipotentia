package net.tywrapstudios.deipotentia.item.deactivated;

import net.minecraft.item.ItemConvertible;
import net.tywrapstudios.deipotentia.registry.DRegistry;

public class DeactivatedBH extends DeactivatedItem {
    public DeactivatedBH(Settings settings) {
        super(settings);
    }

    @Override
    public String getActivatorUuid() {
        return ""; // TODO: Add LazySpace's UUID here <3
    }

    @Override
    public ItemConvertible getActivatedItem() {
        return DRegistry.DItems.BLAHAJ_GODS;
    }
}
