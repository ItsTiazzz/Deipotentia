package net.tywrapstudios.deipotentia.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import java.util.UUID;

public class PlayerViewingComponent implements AutoSyncedComponent {
    private boolean isViewing = false;
    private UUID viewingTarget = null;
    private int viewingTime = 0;
    private double x = 0;
    private double y = 62; // Sea Level
    private double z = 0;

    public boolean isViewing() {
        return isViewing;
    }

    public UUID getViewingTarget() {
        return viewingTarget;
    }

    public void setViewingData(boolean viewing, UUID target, double x, double y, double z, int time, PlayerEntity provider) {
        this.isViewing = viewing;
        this.viewingTarget = target;
        this.x = x;
        this.y = y;
        this.z = z;
        this.viewingTime = time;
        DeipotentiaComponents.PLAYER_VIEWING_COMPONENT.sync(provider);
    }

    public void setViewing(boolean viewing, PlayerEntity provider) {
        this.isViewing = viewing;
        DeipotentiaComponents.PLAYER_VIEWING_COMPONENT.sync(provider);
    }

    public double[] getPosition() {
        return new double[] {getX(), getY(), getZ()};
    }

    private double getX() {
        return x;
    }

    private double getY() {
        return y;
    }

    private double getZ() {
        return z;
    }

    public void setTime(int time, PlayerEntity provider) {
        this.viewingTime = time;
        DeipotentiaComponents.PLAYER_VIEWING_COMPONENT.sync(provider);
    }

    public int getTime() {
        return viewingTime;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.isViewing = tag.getBoolean("IsViewing");
        if (tag.contains("ViewingTarget")) {
            this.viewingTarget = tag.getUuid("ViewingTarget");
        }
        this.viewingTime = tag.getInt("ViewingTime");
        this.x = tag.getDouble("OriginX");
        this.y = tag.getDouble("OriginY");
        this.z = tag.getDouble("OriginZ");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putBoolean("IsViewing", this.isViewing);
        if (this.viewingTarget != null) {
            tag.putUuid("ViewingTarget", this.viewingTarget);
        }
        tag.putInt("ViewingTime", this.viewingTime);
        tag.putDouble("OriginX", this.x);
        tag.putDouble("OriginY", this.y);
        tag.putDouble("OriginZ", this.z);
    }
}
