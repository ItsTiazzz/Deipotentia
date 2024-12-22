package net.tywrapstudios.deipotentia.component;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.tywrapstudios.deipotentia.Deipotentia;

public class PlayerPostMortemComponent implements ComponentV3, AutoSyncedComponent {
    private static final String NBT_KEY = "HasDied";
    private boolean hasDiedBefore = false;

    public PlayerPostMortemComponent() {
    }

    public boolean hasDiedBefore() {
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
        this.hasDiedBefore = tag.contains("HasDied") && tag.getBoolean("HasDied");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putBoolean(NBT_KEY, this.hasDiedBefore);
        Deipotentia.LOGGING.debug("Writing NBT - HasDied: " + this.hasDiedBefore);
    }
}
