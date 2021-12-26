package com.dovecot.dcmc_utils.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class DCUConfig {

    public static final String ANTI_LIGHTNING = "prevents lightning generation";
    public static final String ANTI_LIGHTNING_NOTIFY = "notify when someone tries to generate a lightning bolt";
    public static final String ANTI_LIGHTNING_NOTIFICATION_CONTENT = "lightning bolt interception notification content";
    public static final String ANTI_BLOCK_EXPLOSION = "prevents block explosion";
    public static final String ANTI_ENTITY_EXPLOSION = "prevents entity explosion";

    public static final String PLAYER_JOIN_TIP = "casts tip(s) on player join";
    public static final String PLAYER_JOIN_TIP_CONTENT = "player join event's tip content";

    public static void initialize(JavaPlugin plugin){
        FileConfiguration config = plugin.getConfig();
        config.addDefault(ANTI_LIGHTNING, true);
        config.addDefault(ANTI_LIGHTNING_NOTIFY, true);
        config.addDefault(ANTI_LIGHTNING_NOTIFICATION_CONTENT, "生成的闪电束已被拦截:)");
        config.addDefault(ANTI_BLOCK_EXPLOSION, true);
        config.addDefault(ANTI_ENTITY_EXPLOSION, true);
        config.addDefault(PLAYER_JOIN_TIP, true);
        config.addDefault(PLAYER_JOIN_TIP_CONTENT, "欢迎来到鸽舍MC服务器，请仔细阅读群公告，与其他玩家友好相处~ 祝您游玩愉快！");
        config.options().copyDefaults(true);
        plugin.saveConfig();
    }
}
