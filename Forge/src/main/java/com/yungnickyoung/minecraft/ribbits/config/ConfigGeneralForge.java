package com.yungnickyoung.minecraft.ribbits.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigGeneralForge {
    public final ForgeConfigSpec.ConfigValue<Boolean> prideFlagAllYear;

    public ConfigGeneralForge(final ForgeConfigSpec.Builder BUILDER) {
        BUILDER
                .comment(
                        """
                                ##########################################################################################################
                                # General settings.
                                ##########################################################################################################""")
                .push("General");

        prideFlagAllYear = BUILDER
                .comment(
                        """
                        If enabled, Pride Ribbits will exist all year instead of only in June.
                        Default: false""".indent(1))
                .define("Show Pride Flags All Year", false);

        BUILDER.pop();
    }
}

