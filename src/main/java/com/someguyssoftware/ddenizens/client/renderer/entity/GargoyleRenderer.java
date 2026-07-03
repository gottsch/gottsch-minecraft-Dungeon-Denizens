/*
 * This file is part of  Dungeon Denizens.
 * Copyright (c) 2025 Mark Gottschling (gottsch)
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

import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.client.model.GargoyleModel;
import com.someguyssoftware.ddenizens.client.model.WingedSkeletonModel;
import com.someguyssoftware.ddenizens.entity.monster.Gargoyle;
import com.someguyssoftware.ddenizens.entity.monster.WingedSkeleton;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * 
 * @author Mark Gottschling on July 5, 2025
 *
 * @param <T>
 */
public class GargoyleRenderer<T extends Gargoyle> extends MobRenderer<T, GargoyleModel<T>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(DD.MODID, "textures/entity/gargoyle.png");

	/**
	 *
	 * @param context
	 */
	public GargoyleRenderer(EntityRendererProvider.Context context) {
        super(context, new GargoyleModel<>(context.bakeLayer(GargoyleModel.LAYER_LOCATION)), 0.8F);
     }

     @Override
    public ResourceLocation getTextureLocation(Gargoyle entity) {
        return TEXTURE;
    }
}
