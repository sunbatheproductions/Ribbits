package com.yungnickyoung.minecraft.ribbits.client.supporters;

import com.yungnickyoung.minecraft.ribbits.RibbitsCommon;
import com.yungnickyoung.minecraft.yungsapi.io.JSON;
import org.jetbrains.annotations.NotNull;

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
 * Holds the list of ALL supporters and friends from the externally hosted supporters.json file.
 * This is validated against client-side when rendering the supporter hat on players.
 */
public class SupportersJSON {
    private static Thread backgroundThread;
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

    /**
     * Loads the supporters.json file from GitHub and populates the supporters list.
     */
    public static void populateSupportersList() {
        if (backgroundThread != null && backgroundThread.isAlive()) {
            return;
        }
        backgroundThread = new SupporterJSONPopulatorThread();
        backgroundThread.start();
    }

    private static class SupporterJSONPopulatorThread extends Thread {
        public SupporterJSONPopulatorThread() {
            super("Ribbits Supporter JSON Thread");
            setDaemon(true);
        }

        @Override
        public void run() {
            // Create InputStream of data from GitHub
            InputStream resourceStream;
            try {
                resourceStream = fetchDataFromGitHub();
            } catch (IOException e) {
                RibbitsCommon.LOGGER.error("Unable to load Ribbits supporters.json resource!");
                RibbitsCommon.LOGGER.error(e);
                return;
            }

            // Create a temp JSON file from the InputStream
            File tempFile;
            try {
                tempFile = File.createTempFile("resource-", ".tmp");
                tempFile.deleteOnExit();
                Files.copy(resourceStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                RibbitsCommon.LOGGER.error("Failed to create temp file for supporters.json", e);
                return;
            }

            // Validate temp file
            if (!tempFile.exists()) {
                RibbitsCommon.LOGGER.error("Ribbits supporters.json temp file does not exist!");
                return;
            }
            if (!tempFile.canRead()) {
                RibbitsCommon.LOGGER.error("Ribbits supporters.json temp file is not readable!");
                return;
            }

            // Load the JSON file into the SupportersJSON instance
            try {
                SupportersJSON.instance = JSON.loadObjectFromJsonFile(tempFile.toPath(), SupportersJSON.class);
                RibbitsCommon.LOGGER.info("Successfully loaded supporters.json file!");
            } catch (IOException e) {
                RibbitsCommon.LOGGER.error("Unable to load from supporters.json file: {}", e.getMessage());
            }
        }

        private @NotNull InputStream fetchDataFromGitHub() throws IOException {
            URL url = new URL("https://raw.githubusercontent.com/yungnickyoung/Ribbits/refs/heads/1.20.1/supporters.json");
            URLConnection conn = url.openConnection();
            final int TIMEOUT = 10_000;
            conn.setConnectTimeout(TIMEOUT);
            conn.setReadTimeout(TIMEOUT);
            return conn.getInputStream();
        }
    }
}
