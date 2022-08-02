package com.zpedroo.farmaxe.listeners;

import com.zpedroo.farmaxe.FarmAxe;
import com.zpedroo.farmaxe.enums.EnchantProperty;
import com.zpedroo.farmaxe.managers.DataManager;
import com.zpedroo.farmaxe.objects.BlockProperties;
import com.zpedroo.farmaxe.objects.Enchant;
import com.zpedroo.farmaxe.objects.MaterialProperties;
import com.zpedroo.farmaxe.utils.config.Blocks;
import com.zpedroo.farmaxe.utils.config.Messages;
import com.zpedroo.farmaxe.utils.config.Titles;
import com.zpedroo.farmaxe.utils.farmaxe.FarmAxeUtils;
import com.zpedroo.farmaxe.utils.formatter.NumberFormatter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FarmAxeListeners implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;

        Player player = event.getPlayer();
        if (Blocks.DISABLED_WORLDS.contains(player.getWorld().getName())) return;

        Block block = event.getBlock();
        if (!hasBlockProperties(block)) return;

        ItemStack item = player.getItemInHand();
        if (!FarmAxeUtils.isFarmAxe(item)) {
            event.setCancelled(true);
            player.sendMessage(Messages.NEED_FARM_AXE);
            return;
        }

        BlockProperties harvestBlockProperties = getHarvestBlockProperties(block);
        MaterialProperties replantMaterialProperties = harvestBlockProperties.getReplantMaterialProperties();
        replantBlock(block, replantMaterialProperties, 0);

        final int oldLevel = FarmAxeUtils.getItemLevel(item);
        Enchant enchant = DataManager.getInstance().getEnchantByName("exp");
        double bonus = 1 + FarmAxeUtils.getEnchantEffectByItem(item, enchant, EnchantProperty.MULTIPLIER);
        double expAmount = harvestBlockProperties.getExpAmount();
        double expToGive = expAmount * bonus;

        ItemStack newItem = FarmAxeUtils.addItemExperience(item, expToGive);
        newItem = FarmAxeUtils.addItemPoints(newItem, harvestBlockProperties.getPointsAmount());
        int newLevel = FarmAxeUtils.getItemLevel(newItem);

        if (isNewLevel(oldLevel, newLevel)) {
            sendUpgradeTitle(player, oldLevel, newLevel);
        }

        player.setItemInHand(newItem);
    }

    @Nullable
    private BlockProperties getHarvestBlockProperties(@NotNull Block block) {
        return Blocks.LIST.stream().filter(blockProperties -> blockProperties.getHarvestMaterialProperties().getMaterial().equals(block.getType())
                && blockProperties.getHarvestMaterialProperties().getData() == block.getData()).findFirst().orElse(null);
    }

    private void replantBlock(Block block, MaterialProperties replantMaterialProperties, int delayInTicks) {
        if (replantMaterialProperties.getMaterial() == null) return;

        new BukkitRunnable() {
            @Override
            public void run() {
                block.setType(replantMaterialProperties.getMaterial());
                block.setData(replantMaterialProperties.getData());
            }
        }.runTaskLater(FarmAxe.get(), delayInTicks);
    }

    private void sendUpgradeTitle(Player player, int oldLevel, int newLevel) {
        String[] placeholders = new String[]{ "{old_level}", "{new_level}" };
        String[] replacers = new String[]{
                NumberFormatter.getInstance().formatThousand(oldLevel), NumberFormatter.getInstance().formatThousand(newLevel)
        };
        player.sendTitle(
                StringUtils.replaceEach(Titles.FARM_AXE_UPGRADE[0], placeholders, replacers),
                StringUtils.replaceEach(Titles.FARM_AXE_UPGRADE[1], placeholders, replacers)
        );
    }

    private boolean hasBlockProperties(Block block) {
        return getHarvestBlockProperties(block) != null;
    }

    private boolean isNewLevel(int oldLevel, int newLevel) {
        return oldLevel != newLevel;
    }
}