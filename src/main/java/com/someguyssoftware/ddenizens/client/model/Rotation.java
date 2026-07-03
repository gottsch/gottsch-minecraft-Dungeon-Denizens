package com.someguyssoftware.ddenizens.client.model;

import net.minecraft.client.model.geom.ModelPart;

public record Rotation(float x, float y, float z) {
    public Rotation(ModelPart part) {
        this(part.xRot, part.yRot, part.zRot);
    }
}
