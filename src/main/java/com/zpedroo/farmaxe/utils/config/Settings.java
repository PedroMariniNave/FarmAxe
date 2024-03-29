package com.zpedroo.farmaxe.utils.config;

import com.zpedroo.farmaxe.utils.FileUtils;
import com.zpedroo.multieconomy.api.CurrencyAPI;
import com.zpedroo.multieconomy.objects.general.Currency;

import java.util.List;

public class Settings {

    public static final String COMMAND = FileUtils.get().getString(FileUtils.Files.CONFIG, "Settings.command");

    public static final List<String> ALIASES = FileUtils.get().getStringList(FileUtils.Files.CONFIG, "Settings.aliases");

    public static final String ADMIN_PERMISSION = FileUtils.get().getString(FileUtils.Files.CONFIG, "Settings.admin-permission");

    public static final int FARM_AXE_PICKUP_COOLDOWN = FileUtils.get().getInt(FileUtils.Files.CONFIG, "Settings.farm-axe-pickup-cooldown");

    public static final boolean PREVENT_PLAYER_HIT = FileUtils.get().getBoolean(FileUtils.Files.CONFIG, "Settings.prevent-player-hit");

    public static final Currency QUALITY_CURRENCY = CurrencyAPI.getCurrency(FileUtils.get().getString(FileUtils.Files.CONFIG, "Settings.quality-currency"));
}