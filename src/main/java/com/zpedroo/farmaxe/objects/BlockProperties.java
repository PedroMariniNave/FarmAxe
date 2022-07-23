package com.zpedroo.farmaxe.objects;

import lombok.Data;
import org.bukkit.Material;

import java.math.BigInteger;

@Data
public class BlockProperties {

    private final Material material;
    private final byte data;
    private final double expAmount;
    private final BigInteger pointsAmount;
}