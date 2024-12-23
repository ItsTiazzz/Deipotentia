package net.tywrapstudios.deipotentia.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.tywrapstudios.deipotentia.Deipotentia;

public class PlayerPostMortemComponent implements AutoSyncedComponent {
    private boolean hasDiedBefore = false;

    public void setDeathData(boolean hasDied, PlayerEntity provider) {
        if (this.hasDiedBefore != hasDied) {
            this.hasDiedBefore = hasDied;
            DeipotentiaComponents.PLAYER_DEATH_COMPONENT.sync(provider);
        }
    }
    
    public boolean hasDiedBefore() {
        return hasDiedBefore;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.hasDiedBefore = tag.contains("HasDied") && tag.getBoolean("HasDied");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putBoolean("HasDied", this.hasDiedBefore);
        Deipotentia.LOGGING.debug("Writing NBT - HasDied: " + this.hasDiedBefore);
    }
}
