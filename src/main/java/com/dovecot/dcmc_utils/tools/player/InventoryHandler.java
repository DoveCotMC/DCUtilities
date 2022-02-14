package com.dovecot.dcmc_utils.tools.player;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class InventoryHandler {
    public static int getMaterialAmountInInv(Material item, PlayerInventory inv) {
        int m = 0;
        final int s = inv.getSize();
        for (int i = 0; i <= s; i++) {
            final ItemStack stack = inv.getItem(i);
            if (stack != null && stack.getType() == item) {
                m += stack.getAmount();
            }
        }
        return m;
    }

    public static int tryRemoveStackInInv(Player player, PlayerInventory inv, Material toRemove, int[] removeAttributes) {
        int amountToRemove = removeAttributes[1];
        int index = removeAttributes[0];
        final int size = inv.getSize();
        if (amountToRemove > 0 && index < size) {
            final ItemStack itemGot = inv.getItem(index);
            if (itemGot != null && itemGot.getType() == toRemove) {
                final int amountGot = itemGot.getAmount();
                if (itemGot.getAmount() == 1) {
                    itemGot.setType(Material.AIR);
                } else {
                    itemGot.setAmount(Math.max(0, amountGot - amountToRemove));
                }
                inv.setItem(index, itemGot);
                player.updateInventory();
                amountToRemove = Math.max(0, amountToRemove - amountGot);
            }
            index++;
            removeAttributes = new int[]{index, amountToRemove};
            tryRemoveStackInInv(player, inv, toRemove, removeAttributes);
        }
        return amountToRemove;
    }
}
