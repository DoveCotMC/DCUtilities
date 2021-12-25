package com.dovecot.dcmc_utils.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class DCUConfig {
    public static void initialize(JavaPlugin plugin){
        FileConfiguration config = plugin.getConfig();
        config.addDefault("prevents lightning generation", true);
        config.addDefault("notify when someone tries to generate a lightning bolt", true);
        config.addDefault("lightning bolt interception notification content", "生成的闪电束已被拦截:)");
        config.addDefault("casts tip(s) on player join", true);
        config.addDefault("tip content", "欢迎来到鸽舍MC服务器，请仔细阅读群公告，与其他玩家友好相处~ 祝您游玩愉快！");
        config.options().copyDefaults(true);
        plugin.saveConfig();
    }
}
