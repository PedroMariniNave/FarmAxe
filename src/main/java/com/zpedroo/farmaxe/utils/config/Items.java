package com.zpedroo.farmaxe.utils.config;

import com.zpedroo.farmaxe.managers.DataManager;
import com.zpedroo.farmaxe.objects.Enchant;
import com.zpedroo.farmaxe.utils.FileUtils;
import com.zpedroo.farmaxe.utils.builder.ItemBuilder;
import com.zpedroo.farmaxe.utils.farmaxe.FarmAxeUtils;
import com.zpedroo.farmaxe.utils.formatter.NumberFormatter;
import de.tr7zw.nbtapi.NBTItem;
import org.apache.commons.lang.StringUtils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Items {

    private static final ItemStack POINTS_ITEM = ItemBuilder.build(FileUtils.get().getFile(FileUtils.Files.CONFIG).get(), "Points-Item").build();
    private static final ItemStack FARM_SWORD_ITEM = ItemBuilder.build(FileUtils.get().getFile(FileUtils.Files.CONFIG).get(), "Farm-Axe-Item").build();

    @NotNull
    public static ItemStack getPointsItem(BigInteger amount) {
        NBTItem nbt = new NBTItem(POINTS_ITEM.clone());
        nbt.setString(FarmAxeUtils.POINTS_ITEM_NBT, amount.toString());

        String[] placeholders = new String[]{
                "{amount}"
        };
        String[] replacers = new String[]{
                NumberFormatter.getInstance().format(amount)
        };

        return replaceItemPlaceholders(nbt.getItem(), placeholders, replacers);
    }

    @NotNull
    public static ItemStack getFarmAxeItem() {
        NBTItem nbt = new NBTItem(FARM_SWORD_ITEM.clone());
        nbt.setBoolean(FarmAxeUtils.IDENTIFIER_NBT, true);

        ItemStack item = nbt.getItem();
        return replaceItemPlaceholders(item, FarmAxeUtils.getPlaceholders(), FarmAxeUtils.getReplacers(item));
    }

    @NotNull
    public static ItemStack getFarmAxeItem(@NotNull ItemStack baseItem) {
        NBTItem nbt = new NBTItem(FARM_SWORD_ITEM.clone());
        nbt.setBoolean(FarmAxeUtils.IDENTIFIER_NBT, true);

        for (Enchant enchant : DataManager.getInstance().getEnchants()) {
            int level = FarmAxeUtils.getEnchantmentLevel(baseItem, enchant);
            if (level <= enchant.getInitialLevel()) continue;

            String enchantName = enchant.getName();
            nbt.setInteger(enchantName, level);
            Enchantment enchantment = Enchantment.getByName(enchantName.toUpperCase());
            if (enchantment != null) {
                int finalLevel = FarmAxeUtils.applyQualityBonus(baseItem, level);
                nbt.getItem().addUnsafeEnchantment(enchantment, finalLevel);
            }
        }

        nbt.setDouble(FarmAxeUtils.EXPERIENCE_NBT, FarmAxeUtils.getItemExperience(baseItem));
        nbt.setString(FarmAxeUtils.FARM_SWORD_POINTS_NBT, FarmAxeUtils.getItemPoints(baseItem).toString());
        nbt.setInteger(FarmAxeUtils.QUALITY_NBT, FarmAxeUtils.getItemQuality(baseItem));

        ItemStack item = nbt.getItem();
        return replaceItemPlaceholders(item, FarmAxeUtils.getPlaceholders(), FarmAxeUtils.getReplacers(item));
    }

    @NotNull
    private static ItemStack replaceItemPlaceholders(ItemStack item, String[] placeholders, String[] replacers) {
        if (item.getItemMeta() != null) {
            String displayName = item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : null;
            List<String> lore = item.getItemMeta().hasLore() ? item.getItemMeta().getLore() : null;
            ItemMeta meta = item.getItemMeta();
            if (displayName != null) meta.setDisplayName(StringUtils.replaceEach(displayName, placeholders, replacers));
            if (lore != null) {
                List<String> newLore = new ArrayList<>(lore.size());
                for (String str : lore) {
                    newLore.add(StringUtils.replaceEach(str, placeholders, replacers));
                }

                meta.setLore(newLore);
            }

            item.setItemMeta(meta);
        }

        return item;
    }
}