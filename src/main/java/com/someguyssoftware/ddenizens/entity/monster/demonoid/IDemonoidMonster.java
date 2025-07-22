package com.someguyssoftware.ddenizens.entity.monster.demonoid;

/**
 * @author by Mark Gottschling on 7/16/2025
 */
public interface IDemonoidMonster {
    public static final String IS_FLYING_TAG = "isFlying";

    public boolean isFlying();
//    public void setFlying(boolean isFlying);
    public void setFlyingMode(boolean isFlying);

    boolean shouldLand();

    boolean isLandAttackOnly();
}
