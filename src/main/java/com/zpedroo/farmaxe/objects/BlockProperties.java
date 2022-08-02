package com.zpedroo.farmaxe.objects;

import lombok.Data;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

@Data
public class BlockProperties {

    @NotNull
    private final MaterialProperties harvestMaterialProperties;
    @Nullable
    private final MaterialProperties replantMaterialProperties;
    private final double expAmount;
    private final BigInteger pointsAmount;
}