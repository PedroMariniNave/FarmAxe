package com.zpedroo.farmaxe.managers.cache;

import com.zpedroo.farmaxe.enums.EnchantProperty;
import com.zpedroo.farmaxe.objects.Enchant;
import com.zpedroo.farmaxe.utils.FileUtils;
import com.zpedroo.farmaxe.utils.formatter.NumberFormatter;
import lombok.Getter;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@Getter
public class DataCache {

    private final Map<String, Enchant> enchants = getEnchantsFromConfig();

    private Map<String, Enchant> getEnchantsFromConfig() {
        FileUtils.Files file = FileUtils.Files.CONFIG;
        Map<String, Enchant> ret = new HashMap<>(4);
        for (String enchantName : FileUtils.get().getSection(file, "Enchants")) {
            Enchant enchant = loadEnchant(enchantName, file);
            ret.put(enchantName.toUpperCase(), enchant);
        }

        return ret;
    }

    private Enchant loadEnchant(String enchantName, FileUtils.Files file) {
        if (!FileUtils.get().getFile(file).get().contains("Enchants." + enchantName)) return null;

        int initialLevel = FileUtils.get().getInt(file, "Enchants." + enchantName + ".level.initial");
        int maxLevel = FileUtils.get().getInt(file, "Enchants." + enchantName + ".level.max");
        int requiredLevel = FileUtils.get().getInt(file, "Enchants." + enchantName + ".level.requirement-per-upgrade");
        BigInteger costPerLevel = NumberFormatter.getInstance().filter(FileUtils.get().getString(file, "Enchants." + enchantName + ".cost-per-level", "0"));
        double multiplierPerLevel = FileUtils.get().getDouble(file, "Enchants." + enchantName + ".multiplier-per-level");

        Map<EnchantProperty, Number> enchantProperties = new HashMap<>(EnchantProperty.values().length);
        enchantProperties.put(EnchantProperty.MULTIPLIER, multiplierPerLevel);

        return new Enchant(enchantName, initialLevel, maxLevel, requiredLevel, costPerLevel, enchantProperties);
    }
}