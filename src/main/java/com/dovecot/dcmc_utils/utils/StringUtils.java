package com.dovecot.dcmc_utils.utils;

import org.bukkit.Location;

import java.text.MessageFormat;

public class StringUtils {
    public static String locationToString(Location loc) {
        return MessageFormat.format("[{0},{1},{2}]", loc.getX(), loc.getY(), loc.getZ());
    }
}
