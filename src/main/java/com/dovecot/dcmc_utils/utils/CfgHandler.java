package com.dovecot.dcmc_utils.utils;

import org.bukkit.configuration.file.FileConfiguration;

public class CfgHandler {
    public static String getStringOrEmpty(FileConfiguration cfg, String path) {
        return cfg.getString(path) != null ? cfg.getString(path) : "";
    }
}
