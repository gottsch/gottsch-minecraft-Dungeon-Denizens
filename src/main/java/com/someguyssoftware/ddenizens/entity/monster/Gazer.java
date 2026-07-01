/**
 * 
 */
package com.someguyssoftware.ddenizens.entity.monster;

import com.someguyssoftware.ddenizens.entity.ai.goal.WeightedCastProjectileGoal;
import com.someguyssoftware.ddenizens.entity.ai.goal.WeightedChanceSummonGoal;
import com.someguyssoftware.ddenizens.entity.projectile.AbstractDDHurtingProjectile;
import com.someguyssoftware.ddenizens.entity.projectile.HarmSpell;
import com.someguyssoftware.ddenizens.entity.projectile.ParalysisSpell;
import com.someguyssoftware.ddenizens.setup.Registration;

import mod.gottsch.forge.gmm.core.config.MobConfig;
import mod.gottsch.forge.gmm.core.config.MobConfigHelper;
import mod.gottsch.forge.gottschcore.random.WeightedCollection;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * @author Mark Gottschling on Apr 6, 2022
 *
 */
public class Gazer extends Beholderkin {

	/**
	 * 
	 * @param entityType
	 * @param level
	 */
	public Gazer(EntityType<? extends FlyingMob> entityType, Level level) {
		super(entityType, level, MonsterSize.MEDIUM);
		this.moveControl = new BeholderkinMoveControl(this);
		this.xpReward = 10;
	}

	@Override
	protected void registerGoals() {
		MobConfig config = MobConfigHelper.get(this);
		this.goalSelector.addGoal(4, new BeholderkinBiteGoal(this, (int) config.number("biteCooldownTime", 20)));
		this.goalSelector.addGoal(5, new BeholderkinRandomFloatAroundGoal(this, (int) config.number("maxFloatHeight", 5)));
		this.goalSelector.addGoal(7, new BeholderkinLookGoal(this));

		WeightedCollection<Integer, AbstractDDHurtingProjectile> spells = new WeightedCollection<>();
		spells.add(3, new ParalysisSpell(Registration.PARALYSIS_SPELL_ENTITY_TYPE.get(), level()));
		spells.add(1, new HarmSpell(Registration.HARM_SPELL_ENTITY_TYPE.get(), level()));
		this.goalSelector.addGoal(6, new WeightedCastProjectileGoal(this, (int) config.number("spellChargeTime", 80), spells));

		WeightedCollection<Double, EntityType<? extends Mob>> mobs = new WeightedCollection<>();
		mobs.add(33D, Registration.HEADLESS_ENTITY_TYPE.get());
		mobs.add(33D, Registration.ORC_ENTITY_TYPE.get());
		mobs.add(34D, EntityType.ZOMBIE);
		mobs.add(20D, EntityType.VEX);
		this.goalSelector.addGoal(6, new WeightedChanceSummonGoal(this, (int) config.number("summonCooldownTime", 2400), 100, mobs, (int) config.number("minSummonSpawns", 1), (int) config.number("maxSummonSpawns", 1)));

		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Boulder.class, true, (entity) -> {
			if (entity instanceof Boulder) {
				 return ((Boulder)entity).isActive();
			}
			return false;
		}));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	/**
	 * this method needs to be assigned to the EntityType during EntityAttributeCreationEvent event.
	 * see ModSetup.onAttributeCreate()
	 * @return
	 */
	public static AttributeSupplier.Builder prepareAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.ATTACK_DAMAGE, 6.0D)
				.add(Attributes.ATTACK_KNOCKBACK, 1.0D)
				.add(Attributes.ARMOR, 3.0D)
				.add(Attributes.ARMOR_TOUGHNESS, 1.0D)
				.add(Attributes.MAX_HEALTH, 18.0)
				.add(Attributes.FOLLOW_RANGE, 100.0)
				.add(Attributes.MOVEMENT_SPEED, 0.18F);
	}

	@Override
	public int getAmbientSoundInterval() {
		return 100;
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return Registration.AMBIENT_GAZER.get();
	}
}
