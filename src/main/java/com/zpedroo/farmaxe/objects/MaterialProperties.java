package com.zpedroo.farmaxe.objects;

import lombok.Data;
import org.bukkit.Material;

@Data
public class MaterialProperties {

    private final Material material;
    private final byte data;
}