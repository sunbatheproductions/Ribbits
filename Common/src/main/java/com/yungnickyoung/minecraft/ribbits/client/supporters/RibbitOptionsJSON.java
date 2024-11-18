package com.yungnickyoung.minecraft.ribbits.client.supporters;

import com.yungnickyoung.minecraft.ribbits.RibbitsCommon;
import com.yungnickyoung.minecraft.ribbits.services.Services;
import com.yungnickyoung.minecraft.yungsapi.io.JSON;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents the JSON options stored in config/ribbits-options.json.
 */
public class RibbitOptionsJSON {
    private static RibbitOptionsJSON instance;
    public static RibbitOptionsJSON get() {
        if (instance == null) {
            instance = new RibbitOptionsJSON(false);
        }
        return instance;
    }

    private boolean enableSupporterHat;

    private RibbitOptionsJSON(boolean enableSupporterHat) {
        this.enableSupporterHat = enableSupporterHat;
    }

    public boolean isSupporterHatEnabled() {
        return enableSupporterHat;
    }

    public void setSupporterHatEnabled(boolean enabled) {
        this.enableSupporterHat = enabled;
        Services.SUPPORTER_HELPER.toggleSupporterHatNotifyServer(enabled);
        saveToFile();
    }

    // TODO - separate I/O thread?
    private void saveToFile() {
        try (Writer writer = new FileWriter(getOptionsFilePath().toFile())) {
            JSON.gson.toJson(instance, writer);
        } catch (IOException e) {
            RibbitsCommon.LOGGER.error("Error saving ribbits-options.json file: {}", e.toString());
        }
    }

    public static void loadFromFile() {
        Path path = getOptionsFilePath();
        File file = new File(path.toString());

        if (!file.exists()) {
            // Create default file if options file doesn't already exist
            try {
                JSON.createJsonFileFromObject(path, RibbitOptionsJSON.get());
            } catch (IOException e) {
                RibbitsCommon.LOGGER.error("Unable to create ribbits-options.json file: {}", e.toString());
            }
        } else {
            // If file already exists, load data into singleton instance
            if (!file.canRead()) {
                RibbitsCommon.LOGGER.error("ribbits-options.json file not readable! Using default configuration...");
                return;
            }

            try {
                instance = JSON.loadObjectFromJsonFile(path, RibbitOptionsJSON.class);
            } catch (IOException e) {
                RibbitsCommon.LOGGER.error("Error loading ribbits-options.json file: {}", e.toString());
                RibbitsCommon.LOGGER.error("Using default configuration...");
            }
        }
    }

    private static Path getOptionsFilePath() {
        return Paths.get(Services.PLATFORM.getConfigPath().toString(), "ribbits-options.json");
    }
}
