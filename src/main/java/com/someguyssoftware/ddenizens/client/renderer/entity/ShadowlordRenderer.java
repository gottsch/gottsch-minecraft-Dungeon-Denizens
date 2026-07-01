/**
 * 
 */
package com.someguyssoftware.ddenizens.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.client.model.ShadowlordModel;
import com.someguyssoftware.ddenizens.client.renderer.entity.layer.ShadowlordEyeLayer;
import com.someguyssoftware.ddenizens.entity.monster.Beholder;
import com.someguyssoftware.ddenizens.entity.monster.Shadowlord;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * 
 * @author Mark Gottschling on Apr 18, 2022
 *
 * @param <T>
 */
public class ShadowlordRenderer<T extends Shadowlord> extends HumanoidMobRenderer<T, ShadowlordModel<T>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(DD.MODID, "textures/entity/shadowlord_2.png");

    private final float scale;

	/**
	 * 
	 * @param context
	 */
	public ShadowlordRenderer(EntityRendererProvider.Context context) {
        super(context, new ShadowlordModel<>(context.bakeLayer(ShadowlordModel.LAYER_LOCATION)), 0F);

        this.addLayer(new ShadowlordEyeLayer<>(this));
        // TODO make another 'eye' layer for the trim on the robes, but not full bright.
        this.scale = 1.25F; // makes the body approx 3 blocks in height
     }

    @Override
    protected void scale(Shadowlord shadowlord, PoseStack pose, float scale) {
        pose.scale(this.scale, this.scale, this.scale);
    }

     @Override
    public ResourceLocation getTextureLocation(Shadowlord entity) {
        return TEXTURE;
    }
}
