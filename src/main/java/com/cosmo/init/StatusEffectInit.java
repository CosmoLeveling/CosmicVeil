package com.cosmo.init;

import com.cosmo.CosmicVeil;
import com.cosmo.status_effects.SolarFlightEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class StatusEffectInit {
    public static StatusEffect SolarFlight = register("solar_flight",new SolarFlightEffect());

    private static StatusEffect register(String name, StatusEffect status_effect) {
        return Registry.register(Registries.STATUS_EFFECT, Identifier.of(CosmicVeil.MOD_ID,name),status_effect);
    }
    public static void init(){}
}
