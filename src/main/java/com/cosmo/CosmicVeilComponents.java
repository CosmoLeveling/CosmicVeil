package com.cosmo;

import com.cosmo.util.CompassComponent;
import com.cosmo.util.MonarchSwordComponent;
import com.cosmo.util.ShiftedComponent;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import dev.onyxstudios.cca.api.v3.world.WorldComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.world.WorldComponentInitializer;
import net.minecraft.util.Identifier;

public class CosmicVeilComponents implements EntityComponentInitializer, WorldComponentInitializer {
    public static final ComponentKey<ShiftedComponent> SHIFTED =
            ComponentRegistry.getOrCreate(new Identifier(CosmicVeil.MOD_ID, "shifted"), ShiftedComponent.class);
    public static final ComponentKey<CompassComponent> Compass =
            ComponentRegistry.getOrCreate(new Identifier(CosmicVeil.MOD_ID, "compass"), CompassComponent.class);
    public static final ComponentKey<MonarchSwordComponent> MonarchSwordComponent =
            ComponentRegistry.getOrCreate(new Identifier(CosmicVeil.MOD_ID, "monarch_sword"), MonarchSwordComponent.class);
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(SHIFTED, ShiftedComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
        registry.registerForPlayers(Compass, CompassComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
        registry.registerForPlayers(MonarchSwordComponent, MonarchSwordComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
    }

    @Override
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry worldComponentFactoryRegistry) {

    }
}
