package com.someguyssoftware.ddenizens.entity;

import com.someguyssoftware.ddenizens.entity.monster.Gargoyle;
import com.someguyssoftware.ddenizens.entity.monster.WingedSkeleton;
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

    public static final RegistryObject<EntityType<Gargoyle>> GARGOYLE_TYPE = Registration.ENTITIES.register(GARGOYLE, () -> EntityType.Builder.of(Gargoyle::new, MobCategory.MONSTER)
            .sized(0.75F, 1.75F)
            .clientTrackingRange(12)
            .setShouldReceiveVelocityUpdates(false)
            .build(GARGOYLE));

    public static void init() {
        // no op to force load
    }

    public static void register(IEventBus bus) {
        Registration.ENTITIES.register(bus);
    }
}
