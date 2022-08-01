package com.zpedroo.farmaxe.hooks;

import com.zpedroo.farmaxe.utils.farmaxe.FarmAxeUtils;
import com.zpedroo.farmaxe.utils.formatter.NumberFormatter;
import com.zpedroo.farmaxe.utils.progress.ProgressConverter;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

public class PlaceholderAPIHook extends PlaceholderExpansion {

    private final Plugin plugin;

    public PlaceholderAPIHook(Plugin plugin) {
        this.plugin = plugin;
    }

    @NotNull
    public String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @NotNull
    public String getIdentifier() {
        return "farmaxe";
    }

    @NotNull
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    public String onPlaceholderRequest(@NotNull Player player, @NotNull String identifier) {
        ItemStack item = player.getItemInHand();
        double experience = FarmAxeUtils.getItemExperience(item);
        switch (identifier.toUpperCase()) {
            case "LEVEL":
                int level = FarmAxeUtils.getItemLevel(item);
                return NumberFormatter.getInstance().formatThousand(level);
            case "QUALITY":
                int quality = FarmAxeUtils.getItemQuality(item);
                return ProgressConverter.convertQuality(quality);
            case "PROGRESS":
                return ProgressConverter.convertExperience(experience);
            case "PERCENTAGE":
                return NumberFormatter.getInstance().formatDecimal(ProgressConverter.getPercentage(experience));
            case "POINTS":
                BigInteger pointsAmount = FarmAxeUtils.getItemPoints(item);
                return NumberFormatter.getInstance().format(pointsAmount);
        }

        return null;
    }
}