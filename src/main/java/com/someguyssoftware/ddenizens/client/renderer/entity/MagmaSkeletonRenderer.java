/*
 * This file is part of  Dungeon Denizens.
 * Copyright (c) 2024 Mark Gottschling (gottsch)
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
package com.someguyssoftware.ddenizens.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.client.model.MagmaSkeletonModel;
import com.someguyssoftware.ddenizens.entity.monster.skeleton.MagmaSkeleton;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

/**
 * 
 * @author Mark Gottschling on Feb 4, 2024
 *
 * @param <T>
 */
public class MagmaSkeletonRenderer<T extends MagmaSkeleton> extends HumanoidMobRenderer<T, MagmaSkeletonModel<T>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(DD.MODID, "textures/entity/magma_skeleton.png");
	private final float scale;

	/**
	 *
	 * @param context
	 */
	public MagmaSkeletonRenderer(EntityRendererProvider.Context context) {
		super(context, new MagmaSkeletonModel<>(context.bakeLayer(MagmaSkeletonModel.LAYER_LOCATION)), 0.6F);
		this.scale = 1.2F;
	}

	@Override
	protected void scale(MagmaSkeleton skeleton, PoseStack pose, float scale) {
		pose.scale(this.scale, this.scale, this.scale);
	}

	@Override
	public ResourceLocation getTextureLocation(MagmaSkeleton entity) {
		return TEXTURE;
	}
}
