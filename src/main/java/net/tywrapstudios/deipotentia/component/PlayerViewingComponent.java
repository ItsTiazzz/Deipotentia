package net.tywrapstudios.deipotentia.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;
import net.tywrapstudios.deipotentia.Deipotentia;

import java.util.UUID;

public class PlayerViewingComponent implements AutoSyncedComponent {
    private boolean isViewing = false;
    private UUID viewingTarget = null;
    private int viewingTime = -1; // In ticks (-1 for idle state)
    private double x = 0;
    private double y = 62; // Sea Level
    private double z = 0;
    private boolean dark = false;
    private GameMode gameMode = GameMode.SURVIVAL;

    public void setViewingData(boolean viewing, UUID target, int time, boolean dark, PlayerEntity provider) {
        this.isViewing = viewing;
        this.viewingTarget = target;
        this.x = provider.getX();
        this.y = provider.getY();
        this.z = provider.getZ();
        this.viewingTime = time;
        this.dark = dark;
        this.gameMode = ((ServerPlayerEntity)provider).interactionManager.getGameMode();
        DeipotentiaComponents.PLAYER_VIEWING_COMPONENT.sync(provider);
        Deipotentia.LOGGING.debug(String.format("[PlayerViewingComponent] Setting ViewingData to %s, %s, %s, %s, %s, %s, %s, %s", isViewing, viewingTarget, x, y, z, viewingTime, dark, gameMode.getName()));
    }

    public boolean isViewing() {
        return isViewing;
    }

    public UUID getViewingTarget() {
        return viewingTarget;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setViewing(boolean viewing, PlayerEntity provider) {
        this.isViewing = viewing;
        DeipotentiaComponents.PLAYER_VIEWING_COMPONENT.sync(provider);
    }

    public double[] getPosition() {
        return new double[] {x, y, z};
    }

    public void setTime(int time, PlayerEntity provider) {
        this.viewingTime = time;
        DeipotentiaComponents.PLAYER_VIEWING_COMPONENT.sync(provider);
    }

    public int getTime() {
        return viewingTime;
    }

    public boolean isSelf() {
        return dark;
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
        this.dark = tag.getBoolean("Self");
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
        tag.putBoolean("Self", this.dark);
    }
}
