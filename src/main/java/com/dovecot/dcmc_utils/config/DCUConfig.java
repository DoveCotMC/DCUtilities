package com.dovecot.dcmc_utils.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class DCUConfig {

    public static final String ANTI_LIGHTNING = "prevents lightning generation";
    public static final String ANTI_LIGHTNING_NOTIFY = "notifies when someone tries to generate a lightning bolt";
    public static final String ANTI_LIGHTNING_NOTIFICATION_CONTENT = "lightning bolt interception notification content";
    public static final String ANTI_BLOCK_EXPLOSION = "prevents block explosion";
    public static final String ANTI_ENTITY_EXPLOSION = "prevents entity explosion";
    public static final String CHECK_AND_PREVENT_PLAYER_STUCK = "prevents player stuck in block when they just joined the world";
    public static final String INFORM_PLAYER_OF_THE_STUCK_LOCATION = "informs player the location where they stuck";
    public static final String PLAYER_STUCK_LOCATION_NOTIFY_CONTENT = "player former location notification content";
    public static final String PLAYER_STUCK_RELIEF_NOTIFY = "notifies player when they are not stuck then";
    public static final String PLAYER_STUCK_RELIEF_NOTIFY_CONTENT = "player stuck relief notification content";
    public static final String PLAYER_JOIN_TIP = "casts tip(s) on player join";
    public static final String PLAYER_JOIN_TIP_CONTENT = "player join event's tip content";
    public static final String SET_OP_TO_CREATIVE = "sets OPs to creative mode when they join the server";
    public static final String BEACON_PLACEMENT = "allows the players to place beacon bases in one key";
    public static final String BEACON_PLACEMENT_INTERRUPTED_BY_BLOCK_HEIGHT = "beacon placement interrupted by block height notification content";
    public static final String BEACON_PLACEMENT_INTERRUPTED_BY_BEDROCK = "beacon placement interrupted by bedrock notification content";
    public static final String BEACON_PLACEMENT_INSUFFICIENT_MATERIAL_CONTENT = "beacon placement material insufficient notification content";

    public static void initialize(JavaPlugin plugin) {
        FileConfiguration config = plugin.getConfig();
        config.addDefault(ANTI_LIGHTNING, true);
        config.addDefault(ANTI_LIGHTNING_NOTIFY, true);
        config.addDefault(ANTI_LIGHTNING_NOTIFICATION_CONTENT, "生成的闪电束已被拦截:)");
        config.addDefault(ANTI_BLOCK_EXPLOSION, true);
        config.addDefault(ANTI_ENTITY_EXPLOSION, true);
        config.addDefault(CHECK_AND_PREVENT_PLAYER_STUCK, true);
        config.addDefault(INFORM_PLAYER_OF_THE_STUCK_LOCATION, true);
        config.addDefault(PLAYER_STUCK_LOCATION_NOTIFY_CONTENT, "你之前位于世界({0})的坐标{1}处。");
        config.addDefault(PLAYER_STUCK_RELIEF_NOTIFY, true);
        config.addDefault(PLAYER_STUCK_RELIEF_NOTIFY_CONTENT, "为防止窒息，你已被传送到当前世界出生点。");
        config.addDefault(PLAYER_JOIN_TIP, true);
        config.addDefault(PLAYER_JOIN_TIP_CONTENT, "欢迎来到鸽舍MC服务器，请仔细阅读群公告，与其他玩家友好相处~ 祝您游玩愉快！");
        config.addDefault(SET_OP_TO_CREATIVE, true);
        config.addDefault(BEACON_PLACEMENT, true);
        config.addDefault(BEACON_PLACEMENT_INTERRUPTED_BY_BEDROCK, "信标基底将替换的方块中含不可替换的基岩。");
        config.addDefault(BEACON_PLACEMENT_INTERRUPTED_BY_BLOCK_HEIGHT, "信标放置的高度至少为6。");
        config.addDefault(BEACON_PLACEMENT_INSUFFICIENT_MATERIAL_CONTENT, "你缺乏必要的材料，还缺{0}个{1}。");
        config.options().copyDefaults(true);
        plugin.saveConfig();
    }
}
