package com.yungnickyoung.minecraft.ribbits.client.supporters;

import com.yungnickyoung.minecraft.ribbits.RibbitsCommon;
import com.yungnickyoung.minecraft.yungsapi.io.JSON;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Holds the list of ALL supporters and friends from the supporters.json file hosted on GitHub.
 * This is validated against client-side when rendering the supporter hat on players.
 */
public class SupportersJSON {
    private static SupportersJSON instance;

    public static SupportersJSON get() {
        if (instance == null) {
            instance = new SupportersJSON();
        }
        return instance;
    }

    private final Set<UUID> supporters;
    private final Set<UUID> friends;

    private SupportersJSON() {
        this.supporters = new HashSet<>();
        this.friends = new HashSet<>();
    }

    public boolean isSupporter(UUID uuid) {
        return supporters.contains(uuid) || friends.contains(uuid);
    }

    public static void populateSupportersList() {
        // TODO - move into separate thread
        // Load resource as a stream
        InputStream resourceStream;
        try {
            URL url = new URL("https://raw.githubusercontent.com/yungnickyoung/Ribbits/refs/heads/1.20.1/supporters.json");
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(10 * 1000);
            conn.setReadTimeout(10 * 1000);
            resourceStream = conn.getInputStream();
        } catch (IOException e) {
            RibbitsCommon.LOGGER.error("Failed to load supporter json", e);
            return;
        }

        if (resourceStream == null) {
            RibbitsCommon.LOGGER.error("Unable to find Ribbits supporters.json resource!");
            return;
        }

        // Create a temporary file and copy the resource stream to it
        File tempFile;
        try {
            tempFile = File.createTempFile("resource-", ".tmp");
            tempFile.deleteOnExit();
            Files.copy(resourceStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!tempFile.exists()) {
            RibbitsCommon.LOGGER.error("Ribbits supporters.json temp file does not exist!");
            return;
        }

        if (!tempFile.canRead()) {
            RibbitsCommon.LOGGER.error("Ribbits supporters.json temp file is not readable!");
            return;
        }

        try {
            SupportersJSON.instance = JSON.loadObjectFromJsonFile(tempFile.toPath(), SupportersJSON.class);
        } catch (IOException e) {
            RibbitsCommon.LOGGER.error("Unable to load from supporters.json file: {}", e.getMessage());
        }
    }
}
