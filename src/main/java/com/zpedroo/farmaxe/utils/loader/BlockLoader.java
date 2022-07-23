package com.zpedroo.farmaxe.utils.loader;

import com.zpedroo.farmaxe.objects.BlockProperties;
import com.zpedroo.farmaxe.utils.formatter.NumberFormatter;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class BlockLoader {

    @NotNull
    public static List<BlockProperties> load(List<String> list) {
        List<BlockProperties> ret = new ArrayList<>(list.size());
        for (String str : list) {
            ret.add(load(str));
        }

        return ret;
    }

    @Nullable
    public static BlockProperties load(String str) {
        String[] split = str.split(",");
        if (split.length <= 2) return null;

        String[] materialSplit = split[0].split(":");
        Material material = Material.getMaterial(materialSplit[0].toUpperCase());
        if (material == null) return null;

        byte data = materialSplit.length > 1 ? Byte.parseByte(materialSplit[1]) : 0;
        double expAmount = Double.parseDouble(split[1]);
        BigInteger pointsAmount = NumberFormatter.getInstance().filter(split[2]);

        return new BlockProperties(material, data, expAmount, pointsAmount);
    }
}