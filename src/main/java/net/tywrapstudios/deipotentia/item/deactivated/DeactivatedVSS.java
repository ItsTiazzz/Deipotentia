package net.tywrapstudios.deipotentia.item.deactivated;

import net.minecraft.item.ItemConvertible;
import net.tywrapstudios.deipotentia.Deipotentia;
import net.tywrapstudios.deipotentia.registry.DRegistry;

public class DeactivatedVSS extends DeactivatedItem{
    public DeactivatedVSS(Settings settings) {
        super(settings);
    }

    @Override
    public String getActivatorUuid() {
        return Deipotentia.CONFIG_MANAGER.getConfig().stranglerUuid;
    }

    @Override
    public ItemConvertible getActivatedItem() {
        return DRegistry.DItems.VALSOULSTRANGLER;
    }

    @Override
    public boolean getActivatingStatus() {
        return !Deipotentia.CONFIG_MANAGER.getConfig().$1;
    }

    @Override
    public void handleBeforeActivation() {
        Deipotentia.CONFIG_MANAGER.getConfig().$1 = true;
        Deipotentia.CONFIG_MANAGER.saveConfig();
    }
}
