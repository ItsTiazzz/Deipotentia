package net.tywrapstudios.deipotentia.config;

import blue.endless.jankson.Comment;
import net.tywrapstudios.blossombridge.api.config.AbstractConfig;

public class DeiConfig extends AbstractConfig {
    @Comment("Which UUID is \"Val\".")
    public String valUuid = "49da05e6-7460-4d73-ab17-9d98ce217678";
    @Comment("Which UUID is \"Sten\".")
    public String stenUuid = "3a4c4bc4-a8a0-4976-aa02-65252afc62fd";
    @Comment("Which UUID is \"God\".")
    public String godUuid = "1980c059-96dc-4314-87c3-d2e053195d91";
    @Comment("Internal value to determine whether the Valsoulstrangler has been enabled.\nPreferably do not change this unless you're debugging/resetting the mod.")
    public boolean val = false;
    @Comment("The density of the particles for the Angel's Guard.")
    public int particle_density = 250;
}
