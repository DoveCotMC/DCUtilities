package com.dovecot.dcmc_utils.tools.event_listeners;

import com.dovecot.dcmc_utils.config.DCUConfig;
import com.dovecot.dcmc_utils.utils.CfgHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

import java.text.MessageFormat;

import static com.dovecot.dcmc_utils.tools.player.InventoryHandler.getMaterialAmountInInv;
import static com.dovecot.dcmc_utils.tools.player.InventoryHandler.tryRemoveStackInInv;

public class PlayerInteract {
    public static void BeaconPlacement(Plugin plugin, FileConfiguration config, Player player, Block block, EquipmentSlot hand){
        if(!config.getBoolean(DCUConfig.BEACON_PLACEMENT)){
            return;
        }
        if(!player.isSneaking()){
            return;
        }
        if(!(hand == EquipmentSlot.HAND)){
            return;
        }
        if(!(block.getType() == Material.BEACON)){
            return;
        }
        if(!(block.getLocation().getY() >= 6)){
            player.sendMessage(CfgHandler.getStringOrEmpty(config, DCUConfig.BEACON_PLACEMENT_INTERRUPTED_BY_BLOCK_HEIGHT));
            return;
        }
        final PlayerInventory inv = player.getInventory();
        final Location oL = block.getLocation();
        final World world = player.getWorld();
        final Material[] materials = findCorrespondingItems(inv.getItemInMainHand());
        if(materials == null){
            return;
        }
        final int invBlockAmount = getMaterialAmountInInv(materials[0], inv);
        final int invItemAmount = getMaterialAmountInInv(materials[1], inv);
        final int usableBlockAmount = analyzeUsableBlockAmount(world,materials[0],oL);
        final int total = 9 * invBlockAmount + invItemAmount + usableBlockAmount;
        if(usableBlockAmount >= 0){
            if(total >= 1476){
                tryReplaceBlockWithSpecified(plugin, world, inv, materials, oL);
            }else{
                player.sendMessage(MessageFormat.format(CfgHandler.getStringOrEmpty(config, DCUConfig.BEACON_PLACEMENT_INSUFFICIENT_MATERIAL_CONTENT),
                        1476 - total, materials[1].name()));
            }
        }else{
            player.sendMessage(CfgHandler.getStringOrEmpty(config, DCUConfig.BEACON_PLACEMENT_INTERRUPTED_BY_BEDROCK));
        }
    }



    private static Material[] findCorrespondingItems(ItemStack stack){
        final Material m1 = stack.getType();
        final Material m2;
        switch (m1){
            case GOLD_INGOT:
                m2 = Material.GOLD_BLOCK;
                break;
            case IRON_INGOT:
                m2 = Material.IRON_BLOCK;
                break;
            case DIAMOND:
                m2 = Material.DIAMOND_BLOCK;
                break;
            case EMERALD:
                m2 = Material.EMERALD_BLOCK;
                break;
            case GOLD_BLOCK:
                m2 = Material.GOLD_INGOT;
                break;
            case IRON_BLOCK:
                m2 = Material.IRON_INGOT;
                break;
            case DIAMOND_BLOCK:
                m2 = Material.DIAMOND;
                break;
            case EMERALD_BLOCK:
                m2 = Material.EMERALD;
                break;
            default:
                m2 = null;
        }
        return (m2 != null)?((m1.isBlock())?new Material[]{m1, m2}:new Material[]{m2, m1}):null;
    }

    private static int analyzeUsableBlockAmount(World world, Material oM, Location oL){
        int b = 0;
        for(int y = 1; y <= 4; y++){
            for (int x = -y; x <= y; x++){
                for (int z = -y; z <= y; z++){
                    final Material blockToAnalyze = world.getBlockAt(getBlockByLocationWithOffsets(world, oL, x, -y, z)).getType();
                    if (blockToAnalyze == oM){
                        b += 9;
                    }else if(blockToAnalyze == Material.BEDROCK){
                        b -= 5000;
                    }
                }
            }
        }
        return b;
    }

    private static Location getBlockByLocationWithOffsets(World world, Location origin, int xOffset, int yOffset, int zOffset){
        final int ox = origin.getBlockX();
        final int oy = origin.getBlockY();
        final int oz = origin.getBlockZ();
        return new Location(world, ox + xOffset, oy + yOffset, oz + zOffset);
    }

    private static void tryReplaceBlockWithSpecified(Plugin plugin, World world, PlayerInventory inv, Material[] materials, Location oL){
        for(int y = 1; y <= 4; y++){
            for (int x = -y; x <= y; x++){
                for (int z = -y; z <= y; z++){
                    final Location toRemove = getBlockByLocationWithOffsets(world, oL, x, -y, z);
                    if (!(world.getBlockAt(toRemove).getType() == materials[0])){
                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                            replaceBlockAndDropResources(world, toRemove, materials[0], oL);
                            final int itemToRemove = tryRemoveStackInInv(inv, materials[0], new int[]{0,1}) * 9;
                            tryRemoveStackInInv(inv, materials[1], new int[]{0,itemToRemove});
                        },10L);
                    }
                }
            }
        }
    }

    private static void replaceBlockAndDropResources(World world, Location toRemove, Material toPlace, Location toDrop){
        final Material blockToRemove = world.getBlockAt(toRemove).getType();
        final ItemStack resource = blockToRemove.isSolid()? new ItemStack(blockToRemove): null;
        world.getBlockAt(toRemove).setType(Material.AIR);
        world.getBlockAt(toRemove).setType(toPlace);
        if(resource != null) {
            world.dropItem(toDrop.add(0, 0.5, 0), resource);
        }
    }

}
