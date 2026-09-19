package com.cosmo.init;

import com.cosmo.CosmicVeil;
import com.cosmo.entity.custom.ScorchedEntity;
import com.cosmo.entity.custom.SolarSmasher;
import com.cosmo.entity.custom.TrapBullet;
import com.cosmo.entity.custom.WeeperEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class EntityInit {
	public static final EntityType<WeeperEntity> WEEPER = Registry.register(Registries.ENTITY_TYPE,
			new Identifier(CosmicVeil.MOD_ID,"weeper"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, WeeperEntity::new)
					.dimensions(EntityDimensions.fixed(.65f,1.7f)).build());
    public static final EntityType<ScorchedEntity> SCORCHED = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(CosmicVeil.MOD_ID,"scorched"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ScorchedEntity::new)
                    .dimensions(EntityDimensions.fixed(.65f,1.7f)).build());
    public static final EntityType<SolarSmasher> SOLAR_SMASHER = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(CosmicVeil.MOD_ID,"solar_smasher"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SolarSmasher::new)
                    .dimensions(EntityDimensions.fixed(2f,1.7f)).build());
	public static final EntityType<TrapBullet> TRAP_BULLET = Registry.register(Registries.ENTITY_TYPE,
			new Identifier(CosmicVeil.MOD_ID,"trap_bullet"),
			FabricEntityTypeBuilder.<TrapBullet>create(SpawnGroup.MONSTER, TrapBullet::new)
					.dimensions(EntityDimensions.fixed(0.25f,0.25f)).build());
}
