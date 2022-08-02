package com.zpedroo.farmaxe.utils.loader;

import com.zpedroo.farmaxe.objects.BlockProperties;
import com.zpedroo.farmaxe.objects.MaterialProperties;
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
            BlockProperties blockProperties = load(str);
            if (blockProperties == null) continue;

            ret.add(blockProperties);
        }

        return ret;
    }

    @Nullable
    public static BlockProperties load(String str) {
        String[] split = str.split(",");
        if (split.length <= 3) return null;

        MaterialProperties harvestMaterialProperties = getMaterialProperties(split[0]);
        if (harvestMaterialProperties == null) return null;

        MaterialProperties replantMaterialProperties = getMaterialProperties(split[1]);
        double expAmount = Double.parseDouble(split[2]);
        BigInteger pointsAmount = NumberFormatter.getInstance().filter(split[3]);

        return new BlockProperties(harvestMaterialProperties, replantMaterialProperties, expAmount, pointsAmount);
    }

    private static MaterialProperties getMaterialProperties(String str) {
        String[] split = str.split(":");
        Material material = Material.getMaterial(split[0].toUpperCase());
        if (material == null) return null;

        byte data = split.length > 1 ? Byte.parseByte(split[1]) : 0;

        return new MaterialProperties(material, data);
    }
}