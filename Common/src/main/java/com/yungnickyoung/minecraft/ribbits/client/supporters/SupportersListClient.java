package com.yungnickyoung.minecraft.ribbits.client.supporters;

import com.yungnickyoung.minecraft.ribbits.RibbitsCommon;
import com.yungnickyoung.minecraft.ribbits.services.Services;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * List of players with the supporter hat enabled on the player's current server.
 * The Set of UUIDs should always be kept in sync with the server's list of supporters.
 */
public class SupportersListClient {
    private static final Set<UUID> playersWithSupporterHat = new HashSet<>();

    /**
     * Toggles the supporter hat for a player.
     * If enabling supporter hat, ensures the player is listed in the supporters.json file (SupportersJSON).
     * @param playerUUID The UUID of the player to toggle the supporter hat for
     * @param enabled Whether to enable or disable the supporter hat
     */
    public static void toggleSupporterHat(UUID playerUUID, boolean enabled) {
        if (enabled) {
            // If enabling supporter hat, ensure player is a valid supporter
            if (!Services.PLATFORM.isDevelopmentEnvironment() && !SupportersJSON.get().isSupporter(playerUUID)) {
                RibbitsCommon.LOGGER.error("Player {} attempted to enable supporter hat without being a supporter!", playerUUID);
                return;
            }
            playersWithSupporterHat.add(playerUUID);
        } else {
            playersWithSupporterHat.remove(playerUUID);
        }
    }

    public static boolean isPlayerSupporterHatEnabled(UUID playerUUID) {
        return playersWithSupporterHat.contains(playerUUID);
    }

    public static void clear() {
        playersWithSupporterHat.clear();
    }
}
