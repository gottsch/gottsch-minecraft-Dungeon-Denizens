/*
 * This file is part of  Dungeon Denizens.
 * Copyright (c) 2022 Mark Gottschling (gottsch)
 * 
 * All rights reserved.
 *
 * Dungeon Denizens is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Dungeon Denizens is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Dungeon Denizens.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */
package com.someguyssoftware.ddenizens.client.model;

import java.util.function.Function;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

/**
 * 
 * @author Mark Gottschling on Apr 30, 2022
 *
 * @param <T>
 */
public abstract class HumanlikeModel<T extends Entity> extends EntityModel<T> implements IHumanoidModel {

	public abstract void resetSwing(T entity, ModelPart body, ModelPart rightArm, ModelPart leftArm);

	public HumanlikeModel() {
		super();
	}

	public HumanlikeModel(Function<ResourceLocation, RenderType> renderType) {
		super(renderType);
	}

	/**
	 * 
	 * @return
	 */
	public ModelPart getAttackArm() {
		return getRightArm();
	}

	/**
	 * from vanilla
	 * @param entity
	 * @param age
	 */
	protected void setupAttackAnimation(T entity, float age) {
		resetSwing(entity, getBody(), getRightArm(), getLeftArm());
		if (!(this.attackTime <= 0.0F)) {
			ModelPart attackArm = getAttackArm();
			float f = this.attackTime;
			getBody().yRot = Mth.sin(Mth.sqrt(f) * ((float)Math.PI * 2F)) * 0.2F;
			if (attackArm == getLeftArm()) {
				getBody().yRot *= -1.0F;
			}

			getRightArm().z = Mth.sin(getBody().yRot) * 5.0F;
			getRightArm().x = -Mth.cos(getBody().yRot) * 5.0F;
			getLeftArm().z = -Mth.sin(getBody().yRot) * 5.0F;
			getLeftArm().x = Mth.cos(getBody().yRot) * 5.0F;
			getRightArm().yRot += getBody().yRot;
			getLeftArm().yRot += getBody().yRot;
			getLeftArm().xRot += getBody().yRot;

			f = 1.0F - this.attackTime;
			f *= f;
			f *= f;
			f = 1.0F - f;
			float f1 = Mth.sin(f * (float)Math.PI);
			float f2 = Mth.sin(this.attackTime * (float)Math.PI) * -(getHead().xRot - 0.7F) * 0.75F;
			attackArm.xRot = (float)((double)attackArm.xRot - ((double)f1 * 1.2D + (double)f2));
			attackArm.yRot += getBody().yRot * 2.0F;
			attackArm.zRot += Mth.sin(this.attackTime * (float)Math.PI) * -0.4F;
		}
	}

}
