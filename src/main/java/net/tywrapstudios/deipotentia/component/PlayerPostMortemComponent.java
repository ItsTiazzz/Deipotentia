package net.tywrapstudios.deipotentia.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.tywrapstudios.deipotentia.Deipotentia;

public class PlayerPostMortemComponent implements AutoSyncedComponent {
    private boolean hasDiedBefore = false;
    private int identifier = 0;

    public void setDeathData(boolean hasDied, int identifier, PlayerEntity provider) {
        this.hasDiedBefore = hasDied;
        this.identifier = identifier;
        DeipotentiaComponents.PLAYER_DEATH_COMPONENT.sync(provider);
    }
    
    public boolean hasDiedBefore() {
        return hasDiedBefore;
    }

    public int getIdentifier() {
        return identifier;
    }

    public boolean verifyIdentifier(int identifier) {
        return this.identifier == identifier;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.hasDiedBefore = tag.getBoolean("HasDied");
        this.identifier = tag.getInt("Identifier");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putBoolean("HasDied", this.hasDiedBefore);
        tag.putInt("Identifier", this.identifier);
    }
}
