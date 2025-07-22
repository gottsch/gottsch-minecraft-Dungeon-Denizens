package com.someguyssoftware.ddenizens.client.model;

import net.minecraft.client.model.geom.ModelPart;

public record Position(float x, float y, float z) {
    public Position(ModelPart part) {
        this(part.x, part.y, part.z);
    }
}
