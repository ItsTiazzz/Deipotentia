package net.tywrapstudios.deipotentia.component;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.tywrapstudios.deipotentia.Deipotentia;
import net.tywrapstudios.deipotentia.DeipotentiaComponents;

public class PlayerPostMortemComponent implements ComponentV3, AutoSyncedComponent {
    private static final String NBT_KEY = "HasDied";
    private boolean hasDiedBefore = false;

    public PlayerPostMortemComponent(PlayerPostMortemComponent other) {
        this.hasDiedBefore = other.hasDiedBefore;
    }

    public PlayerPostMortemComponent() {
    }

    public boolean isHasDiedBefore() {
        return hasDiedBefore;
    }

    public void setHasDiedBefore(boolean value, PlayerEntity provider) {
        if (this.hasDiedBefore != value) {
            this.hasDiedBefore = value;
            DeipotentiaComponents.PLAYER_DEATH_COMPONENT.sync(provider);
        }
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        boolean oldValue = this.hasDiedBefore;
        this.hasDiedBefore = tag.contains("HasDied") ? tag.getBoolean("HasDied") : false;
        Deipotentia.LOGGING.debug("Reading NBT - Old value: " + oldValue + ", New value: " + this.hasDiedBefore + ", Has tag: " + tag.contains("HasDied"));
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putBoolean(NBT_KEY, this.hasDiedBefore);
        Deipotentia.LOGGING.debug("Writing NBT - HasDied: " + this.hasDiedBefore);
    }
}
