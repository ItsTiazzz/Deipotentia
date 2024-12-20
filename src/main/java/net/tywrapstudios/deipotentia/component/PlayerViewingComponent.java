package net.tywrapstudios.deipotentia.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.tywrapstudios.deipotentia.DeipotentiaComponents;

import java.util.UUID;

public class PlayerViewingComponent implements AutoSyncedComponent {
    private boolean isViewing = false;
    private UUID viewingTarget = null;

    public boolean isViewing() {
        return isViewing;
    }

    public UUID getViewingTarget() {
        return viewingTarget;
    }

    public void setViewing(boolean viewing, UUID target, PlayerEntity provider) {
        this.isViewing = viewing;
        this.viewingTarget = target;
        DeipotentiaComponents.PLAYER_VIEWING_COMPONENT.sync(provider);
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.isViewing = tag.getBoolean("IsViewing");
        if (tag.contains("ViewingTarget")) {
            this.viewingTarget = tag.getUuid("ViewingTarget");
        }
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putBoolean("IsViewing", this.isViewing);
        if (this.viewingTarget != null) {
            tag.putUuid("ViewingTarget", this.viewingTarget);
        }
    }
}
