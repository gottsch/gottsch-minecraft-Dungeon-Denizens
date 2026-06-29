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
import com.someguyssoftware.ddenizens.client.model.MargoyleModel;
import com.someguyssoftware.ddenizens.entity.monster.Margoyle;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * Renders the Margoyle using its own geometry (4 horns, smaller wings) and the
 * "stone" skin. Shares the winged-humanoid animation, so it flaps while hovering.
 *
 * @author Mark Gottschling on July 26, 2025
 */
public class MargoyleRenderer<T extends Margoyle> extends MobRenderer<T, MargoyleModel<T>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(DD.MODID, "textures/entity/margoyle.png");

	public MargoyleRenderer(EntityRendererProvider.Context context) {
		super(context, new MargoyleModel<>(context.bakeLayer(MargoyleModel.LAYER_LOCATION)), 0.9F);
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return TEXTURE;
	}
}
