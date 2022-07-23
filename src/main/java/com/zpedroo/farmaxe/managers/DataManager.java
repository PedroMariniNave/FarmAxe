package com.zpedroo.farmaxe.managers;

import com.zpedroo.farmaxe.managers.cache.DataCache;
import com.zpedroo.farmaxe.objects.Enchant;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class DataManager {

    private static DataManager instance;
    public static DataManager getInstance() { return instance; }

    private final DataCache dataCache = new DataCache();

    public DataManager() {
        instance = this;
    }

    @Nullable
    public Enchant getEnchantByName(String enchantName) {
        return dataCache.getEnchants().get(enchantName.toUpperCase());
    }

    public Collection<Enchant> getEnchants() {
        return dataCache.getEnchants().values();
    }

    public DataCache getCache() {
        return dataCache;
    }
}