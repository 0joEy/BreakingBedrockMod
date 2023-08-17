package net.henrycmoss.bb.world.gen;

import net.henrycmoss.bb.Bb;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BbPlacementModifiers {

    private static final DeferredRegister<PlacementModifierType<?>> REGISTER = DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE,
            Bb.MODID);

    public static final RegistryObject<PlacementModifierType<ConfigPlacementFilter>> CONFIG_FILTER = REGISTER.register("config_filter",
            () -> () -> ConfigPlacementFilter.CODEC);

    public static void register(IEventBus modEventBus) {
        REGISTER.register(modEventBus);
    }
}
