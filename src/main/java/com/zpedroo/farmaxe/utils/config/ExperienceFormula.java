package com.zpedroo.farmaxe.utils.config;

import com.zpedroo.farmaxe.utils.FileUtils;

public class ExperienceFormula {

    public static final double BASE_EXP = FileUtils.get().getDouble(FileUtils.Files.CONFIG, "Experience-Formula.base-exp");

    public static final double EXPONENT = FileUtils.get().getDouble(FileUtils.Files.CONFIG, "Experience-Formula.exponent");
}