package net.tywrapstudios.deipotentia.component;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.minecraft.nbt.NbtCompound;

public class PlayerPostMortemComponent implements ComponentV3 {
    private boolean hasDiedBefore = false;

    public boolean isHasDiedBefore() {
        return hasDiedBefore;
    }

    public void setHasDiedBefore(boolean hasDiedBefore) {
        this.hasDiedBefore = hasDiedBefore;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.hasDiedBefore = tag.getBoolean("HasDied");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putBoolean("HasDied", hasDiedBefore);
    }
}
