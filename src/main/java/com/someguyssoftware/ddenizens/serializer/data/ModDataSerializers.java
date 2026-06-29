package com.someguyssoftware.ddenizens.serializer.data;

import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.entity.ai.goal.volant.VolantMovement;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * @author by Mark Gottschling on 7/27/2025
 */
public class ModDataSerializers {
    // Create a DeferredRegister for the EntityDataSerializer registry
    public static final DeferredRegister<EntityDataSerializer<?>> DATA_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, DD.MODID);

    // Register our custom serializer
    public static final RegistryObject<EntityDataSerializer<VolantMovement>> VOLANT_MOVEMENT_STATE_SERIALIZER =
            DATA_SERIALIZERS.register("custom_state", () -> new VolantMovementDataSerializer());

    /**
     * Registers the DeferredRegister to the mod event bus.
     * @param eventBus The mod's event bus.
     */
    public static void register(IEventBus eventBus) {
        DATA_SERIALIZERS.register(eventBus);
    }
}
