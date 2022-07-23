package com.zpedroo.farmaxe.utils.config;

import com.zpedroo.farmaxe.utils.FileUtils;
import com.zpedroo.farmaxe.utils.color.Colorize;

public class Messages {

    public static final String COOLDOWN = Colorize.getColored(FileUtils.get().getString(FileUtils.Files.CONFIG, "Messages.cooldown"));

    public static final String NEED_FARM_AXE = Colorize.getColored(FileUtils.get().getString(FileUtils.Files.CONFIG, "Messages.need-farm-axe"));
}