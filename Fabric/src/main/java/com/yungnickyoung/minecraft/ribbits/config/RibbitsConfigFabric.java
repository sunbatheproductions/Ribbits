package com.yungnickyoung.minecraft.ribbits.config;

import com.yungnickyoung.minecraft.ribbits.RibbitsCommon;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name="ribbits-fabric-" + RibbitsCommon.MC_VERSION_STRING)
public class RibbitsConfigFabric implements ConfigData {
    @ConfigEntry.Category("Ribbits")
    @ConfigEntry.Gui.TransitiveObject
    public ConfigGeneralFabric general = new ConfigGeneralFabric();
}
