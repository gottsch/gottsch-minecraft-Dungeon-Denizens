package com.someguyssoftware.ddenizens.client.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Mob;

/**
 * A model based on vanilla HumanoidModel, implementing custom IDemonModel.
 * Models extending this can hold items by means of vanilla code / rendering.
 * @author by Mark Gottschling on 7/16/2025
 */
public abstract class DemonoidModel<T extends Mob> extends HumanoidModel<T> implements IDemonoidModel {

    public DemonoidModel(ModelPart root) {
        super(root);
    }
}
