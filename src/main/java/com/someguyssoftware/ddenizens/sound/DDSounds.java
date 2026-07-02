package com.someguyssoftware.ddenizens.sound;

import com.someguyssoftware.ddenizens.DD;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Sound-event registration: owns the {@link #SOUNDS} deferred registry plus every ambient/step/flap
 * sound event.
 */
public class DDSounds {
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, DD.MODID);

	public static final RegistryObject<SoundEvent> AMBIENT_DAEMON = registerSoundEvent("ambient_daemon");
	public static final RegistryObject<SoundEvent> AMBIENT_BEHOLDER = registerSoundEvent("ambient_beholder");
	public static final RegistryObject<SoundEvent> AMBIENT_DEATH_TYRANT = registerSoundEvent("ambient_death_tyrant");
	public static final RegistryObject<SoundEvent> AMBIENT_GAZER = registerSoundEvent("ambient_gazer");
	public static final RegistryObject<SoundEvent> AMBIENT_SPECTATOR = registerSoundEvent("ambient_spectator");
	public static final RegistryObject<SoundEvent> AMBIENT_SHADOWLORD = registerSoundEvent("ambient_shadowlord");
	// NOTE ambient_shadow migrated to gmm (GMMSounds.SHADOW_AMBIENT) — Shadow now defaults to gmm's own sound.
	public static final RegistryObject<SoundEvent> SHADOWLORD_STEP = registerSoundEvent("shadowlord_step");
	public static final RegistryObject<SoundEvent> WINGED_SKELETON_FLAP = registerSoundEvent("winged_skeleton_flap");

	public static void init() {
		SOUNDS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

	private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
		return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DD.MODID, name)));
	}
}
