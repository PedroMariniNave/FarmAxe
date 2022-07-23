package com.zpedroo.farmaxe.commands;

import com.zpedroo.farmaxe.utils.config.Items;
import com.zpedroo.farmaxe.utils.config.Messages;
import com.zpedroo.farmaxe.utils.config.Settings;
import com.zpedroo.farmaxe.utils.cooldown.Cooldown;
import com.zpedroo.farmaxe.utils.formatter.NumberFormatter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;

public class FarmAxeCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = sender instanceof Player ? (Player) sender : null;
        if (args.length > 0) {
            switch (args[0].toUpperCase()) {
                case "POINTS":
                case "ITEM":
                    if (!sender.hasPermission(Settings.ADMIN_PERMISSION)) break;

                    Player target = Bukkit.getPlayer(args[1]);
                    if (target == null) break;

                    BigInteger amount = NumberFormatter.getInstance().filter(args[2]);
                    if (amount.signum() <= 0) break;

                    ItemStack item = Items.getPointsItem(amount);
                    giveItemToPlayer(target, item);
                    return true;
            }
        }

        if (player == null) return true;
        if (Cooldown.get().isInCooldown(player, this)) {
            player.sendMessage(StringUtils.replaceEach(Messages.COOLDOWN, new String[]{
                    "{cooldown}"
            }, new String[]{
                    String.valueOf(Cooldown.get().getTimeLeftInSeconds(player, this))
            }));
            return true;
        }

        Cooldown.get().addCooldown(player, this, Settings.FARM_AXE_PICKUP_COOLDOWN);

        ItemStack item = Items.getFarmAxeItem();
        player.getInventory().addItem(item);
        return false;
    }

    private int getIntByString(String str) {
        int ret = 0;
        try {
            ret = Integer.parseInt(str);
        } catch (Exception ex) {
            // ignore
        }

        return ret;
    }

    private void giveItemToPlayer(Player target, ItemStack item) {
        if (target.getInventory().firstEmpty() != -1) {
            target.getInventory().addItem(item);
        } else {
            target.getWorld().dropItemNaturally(target.getLocation(), item);
        }
    }
}