package com.zpedroo.farmaxe.utils.config;

import com.zpedroo.farmaxe.objects.BlockProperties;
import com.zpedroo.farmaxe.utils.FileUtils;
import com.zpedroo.farmaxe.utils.loader.BlockLoader;

import java.util.List;

public class Blocks {

    public static final List<String> DISABLED_WORLDS = FileUtils.get().getStringList(FileUtils.Files.CONFIG, "Blocks.disabled-worlds");

    public static final List<BlockProperties> LIST = BlockLoader.load(FileUtils.get().getStringList(FileUtils.Files.CONFIG, "Blocks.blocks"));
}