package com.dovecot.dcmc_utils.tools.event_listeners;

import com.dovecot.dcmc_utils.config.DCUConfig;
import com.dovecot.dcmc_utils.utils.CfgHandler;
import com.dovecot.dcmc_utils.utils.StringUtils;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.text.MessageFormat;

public class PlayerJoin {
    public static void sendMessageOnJoin(FileConfiguration config, Player player) {
        if (config.getBoolean(DCUConfig.PLAYER_JOIN_TIP)) {
            final String messageToSend = CfgHandler.getStringOrEmpty(config, DCUConfig.PLAYER_JOIN_TIP_CONTENT);
            player.sendMessage(messageToSend);
        }
    }

    public static void antiSuffocation(Block headIn, FileConfiguration config, Player player) {
        if (!(headIn.isPassable() || headIn.isLiquid())) {
            if (config.getBoolean(DCUConfig.INFORM_PLAYER_OF_THE_STUCK_LOCATION)) {
                final String world = player.getWorld().getName();
                final String location = StringUtils.locationToString(player.getLocation());
                player.sendMessage(MessageFormat.format(CfgHandler.getStringOrEmpty(config, DCUConfig.PLAYER_STUCK_LOCATION_NOTIFY_CONTENT), world, location));
            }
            if (config.getBoolean(DCUConfig.CHECK_AND_PREVENT_PLAYER_STUCK)) {
                player.teleport(player.getWorld().getSpawnLocation());
                if (config.getBoolean(DCUConfig.PLAYER_STUCK_RELIEF_NOTIFY)) {
                    player.sendMessage(CfgHandler.getStringOrEmpty(config, DCUConfig.PLAYER_STUCK_RELIEF_NOTIFY_CONTENT));
                }
            }
        }
    }

    public static void setCreativeForOps(FileConfiguration config, Player player) {
        if (config.getBoolean(DCUConfig.SET_OP_TO_CREATIVE) && player.isOp()) {
            player.performCommand(MessageFormat.format("/gamemode creative {}", player.getName()));
        }
    }
}
