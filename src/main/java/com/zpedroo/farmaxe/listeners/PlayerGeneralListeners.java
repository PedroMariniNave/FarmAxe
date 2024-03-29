package com.zpedroo.farmaxe.listeners;

import com.zpedroo.farmaxe.utils.farmaxe.FarmAxeUtils;
import com.zpedroo.farmaxe.utils.menu.Menus;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerGeneralListeners implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR) return;
        if (!FarmAxeUtils.isFarmAxe(event.getItem())) return;

        event.setCancelled(true);

        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        Menus.getInstance().openUpgradeMenu(player, item);
    }
}