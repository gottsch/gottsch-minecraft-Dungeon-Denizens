package com.someguyssoftware.ddenizens.item;

import com.someguyssoftware.ddenizens.entity.ModEntities;
import com.someguyssoftware.ddenizens.setup.Registration;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

import static com.someguyssoftware.ddenizens.entity.ModEntities.GARGOYLE_TYPE;

/**
 * @author by Mark Gottschling on 7/3/2025
 */
public class ModItems {

    public static final RegistryObject<Item> GARGOYLE_EGG = Registration.ITEMS.register(ModEntities.GARGOYLE + "_egg", () -> new GargoyleEggItem(GARGOYLE_TYPE, 0xf5f6d2, 0xcdc3bb, new Item.Properties()));

    public static void init() {
        // no op to force load
    }

    public static void register(IEventBus bus) {
        Registration.ITEMS.register(bus);
    }
}
