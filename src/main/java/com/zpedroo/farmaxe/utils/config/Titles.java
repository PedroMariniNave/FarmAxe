package com.zpedroo.farmaxe.utils.config;

import com.zpedroo.farmaxe.utils.FileUtils;
import com.zpedroo.farmaxe.utils.color.Colorize;

public class Titles {

    public static final String[] FARM_AXE_UPGRADE = new String[]{
            Colorize.getColored(FileUtils.get().getString(FileUtils.Files.CONFIG, "Titles.farm-axe-upgrade.title")),
            Colorize.getColored(FileUtils.get().getString(FileUtils.Files.CONFIG, "Titles.farm-axe-upgrade.subtitle"))
    };
}