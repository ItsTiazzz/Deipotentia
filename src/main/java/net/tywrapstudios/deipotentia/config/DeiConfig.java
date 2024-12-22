package net.tywrapstudios.deipotentia.config;

import blue.endless.jankson.Comment;
import net.tywrapstudios.blossombridge.api.config.AbstractConfig;

public class DeiConfig extends AbstractConfig {
    public String stranglerUuid = "49da05e6-7460-4d73-ab17-9d98ce217678";
    public String angelUuid = "3a4c4bc4-a8a0-4976-aa02-65252afc62fd";
    public String godUuid = "1980c059-96dc-4314-87c3-d2e053195d91";
    public boolean $1 = false;
    @Comment("Change this if you're having Mod compat issues (Recommended: stuck-dei)")
    public String stuck_command_id = "stuck";
    @Comment("Change this to true to make Soul Viewing yourself a better experience.\n" +
            "Enabling this is not recommended if you want balanced gameplay.")
    public boolean better_viewing = false;
    public int particle_density = 25;
    public boolean joke_mode = false;
}
