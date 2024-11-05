package com.yungnickyoung.minecraft.ribbits.client;

public class RibbitsCommonClient {
    private static boolean isSupporterHatEnabled = false;

    public static void init() {
        // TODO - read initial value from file
    }

    public static boolean isSupporterHatEnabled() {
        return isSupporterHatEnabled;
    }

    public static void setSupporterHatEnabled(boolean enabled) {
        isSupporterHatEnabled = enabled;
    }
}
