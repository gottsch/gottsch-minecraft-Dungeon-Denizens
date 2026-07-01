package com.someguyssoftware.ddenizens.entity;

import mod.gottsch.forge.gmm.core.entity.monster.Gargoyle;
import mod.gottsch.forge.gmm.core.entity.monster.Margoyle;
import com.someguyssoftware.ddenizens.setup.Registration;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

/**
 * @author by Mark Gottschling on 7/3/2025
 */
public class ModEntities {
    public static String GARGOYLE = "gargoyle";
    public static String MARGOYLE = "margoyle";

    public static final RegistryObject<EntityType<Gargoyle>> GARGOYLE_TYPE = Registration.ENTITIES.register(GARGOYLE, () -> EntityType.Builder.of(Gargoyle::new, MobCategory.MONSTER)
            .sized(0.75F, 1.75F)
            .clientTrackingRange(12)
            .setShouldReceiveVelocityUpdates(false)
            .build(GARGOYLE));

    // 20% larger than a Gargoyle
    public static final RegistryObject<EntityType<Margoyle>> MARGOYLE_TYPE = Registration.ENTITIES.register(MARGOYLE, () -> EntityType.Builder.of(Margoyle::new, MobCategory.MONSTER)
            .sized(0.9F, 2.1F)
            .clientTrackingRange(12)
            .setShouldReceiveVelocityUpdates(false)
            .build(MARGOYLE));

    public static void init() {
        // no op to force load
    }

    public static void register(IEventBus bus) {
        Registration.ENTITIES.register(bus);
    }
}
