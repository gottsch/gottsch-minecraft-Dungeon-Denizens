package com.someguyssoftware.ddenizens.serializer.data;

import com.someguyssoftware.ddenizens.entity.ai.goal.volant.VolantMovement;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;

/**
 * @author by Mark Gottschling on 7/27/2025
 */
public class VolantMovementDataSerializer implements EntityDataSerializer<VolantMovement> {

    /**
     * writes the enum value to the network buffer.
     * @param buffer The buffer to write to.
     * @param movement The CustomState value to write.
     */
    @Override
    public void write(FriendlyByteBuf buffer, VolantMovement movement) {
        buffer.writeEnum(movement);
    }

    /**
     * reads the enum value from the network buffer.
     * @param buffer The buffer to read from.
     * @return The read CustomState value.
     */
    @Override
    public VolantMovement read(FriendlyByteBuf buffer) {
        return buffer.readEnum(VolantMovement.class);
    }

    /**
     * creates a copy of the value. since enums are singletons,
     * we can just return the original value.
     * @param movement The value to copy.
     * @return The same value.
     */
    @Override
    public VolantMovement copy(VolantMovement movement) {
        return movement;
    }
}
