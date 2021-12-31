package com.dovecot.dcmc_utils;

import com.dovecot.dcmc_utils.config.DCUConfig;
import com.dovecot.dcmc_utils.tools.event_listeners.LightningStrike;
import com.dovecot.dcmc_utils.tools.event_listeners.PlayerInteract;
import com.dovecot.dcmc_utils.tools.event_listeners.PlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class DoveCotMCUtilities extends JavaPlugin implements Listener {

    public static Logger LOGGER = Bukkit.getLogger();
    public FileConfiguration config = this.getConfig();

    @Override
    public void onEnable() {
        getLogger().info(ChatColor.GREEN + "Enabled " + this.getName());

        DCUConfig.initialize(this);

        getServer().getPluginManager().registerEvents(this, this);
    }


    @Override
    public void onDisable() {
        getLogger().info(ChatColor.RED + "Disabled " + this.getName());
    }

    //进服通知 / 防窒息 / OP进服创造
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event){
        final Player player = event.getPlayer();
        final Block headIn = player.getEyeLocation().getBlock();
        PlayerJoin.sendMessageOnJoin(config, player);
        PlayerJoin.antiSuffocation(headIn, config, player);
        PlayerJoin.setCreativeForOps(config, player);
    }

    //防雷电
    @EventHandler
    public void onLightningStrikeEvent(LightningStrikeEvent event){
        LightningStrike.preventLightnings(event, config, LOGGER);
    }

    @EventHandler
    public void onBlockExplodeEvent(BlockExplodeEvent event){
        event.setCancelled(config.getBoolean(DCUConfig.ANTI_BLOCK_EXPLOSION));
    }

    @EventHandler
    public void onEntityExplodeEvent(EntityExplodeEvent event){
        event.setCancelled(config.getBoolean(DCUConfig.ANTI_ENTITY_EXPLOSION));
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event){
        final Player player = event.getPlayer();
        final Block block = event.getClickedBlock();
        final EquipmentSlot hand = event.getHand();
        if (block != null)
        {
            PlayerInteract.BeaconPlacement(this, config, player, block, hand);
        }
    }
}
