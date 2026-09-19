package com.cosmo;

import com.cosmo.entity.custom.ScorchedEntity;
import com.cosmo.entity.custom.SolarSmasher;
import com.cosmo.entity.custom.WeeperEntity;
import com.cosmo.init.*;
import com.cosmo.util.DeathOverrideHandler;
import com.cosmo.util.ShiftedComponent;
import com.cosmo.world.feature.*;
import com.cosmo.world.gen.CosmicVeilEntityGeneration;
import com.cosmo.world.gen.CosmicVeilWorldGen;
import com.mojang.authlib.minecraft.client.MinecraftClient;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;

public class CosmicVeil implements ModInitializer {
  public static final String MOD_ID = "cosmic_veil";
  public static final Feature<DefaultFeatureConfig> DarkPillar = Registry.register(Registries.FEATURE,
      Identifier.of(MOD_ID, "dark_pillar"), new DarkPillarsFeature());
  public static final Feature<DefaultFeatureConfig> DarkSpire = Registry.register(Registries.FEATURE,
      Identifier.of(MOD_ID, "dark_spire"), new DarkSpireFeature());
  public static final Feature<DefaultFeatureConfig> SolarSpire = Registry.register(Registries.FEATURE,
      Identifier.of(MOD_ID, "solar_spire"), new SolarSpireFeature());
  public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
  public static final Feature<DefaultFeatureConfig> ShadowMonarchsTreasure = Registry.register(Registries.FEATURE,
      Identifier.of(MOD_ID, "shadow_monarchs_treasure"), new ShadowMonarchsTreasureStructureFeature());
  public static final Feature<DefaultFeatureConfig> ShadowPortal = Registry.register(Registries.FEATURE,
      Identifier.of(MOD_ID, "shadow_portal"), new ShadowPortalStructureFeature());
  public static final Feature<DefaultFeatureConfig> SolarMonument = Registry.register(Registries.FEATURE,
      Identifier.of(MOD_ID, "solar_monument"), new SolarMonumentStructureFeature());
  public static Enchantment ShadowSight = Registry.register(Registries.ENCHANTMENT,
      new Identifier(MOD_ID, "shadow_sight"), new ShadowSight(Enchantment.Rarity.VERY_RARE));
  public static final SoundEvent shadow_realm_ambient = Registry.register(Registries.SOUND_EVENT,
      Identifier.of(MOD_ID, "shadow_realm_ambient"),
      SoundEvent.of(Identifier.of(MOD_ID, "shadow_realm_ambient")));
  public static final SoundEvent shadow_realm_ambient_loop = Registry.register(Registries.SOUND_EVENT,
      Identifier.of(MOD_ID, "shadow_realm_ambient_loop"),
      SoundEvent.of(Identifier.of(MOD_ID, "shadow_realm_ambient_loop")));

  public static Identifier id(String s) {
    return Identifier.of(MOD_ID, s);
  }

  @Override
  public void onInitialize() {
    ServerLifecycleEvents.SERVER_STARTED.register((minecraftServer) -> {
      minecraftServer.getBossBarManager().add(Identifier.of(MOD_ID, "test"), Text.of("Testing"));
    });
    StatusEffectInit.init();
    ItemInit.RegisterItems();
    ItemGroupInit.RegisterItemGroups();
    BlockInit.init();
    DeathOverrideHandler.register();
    BlockEntityInit.init();
    LootTableModifiers.init();
    RecipeInit.init();
    GeckoLib.initialize();
    FabricDefaultAttributeRegistry.register(EntityInit.WEEPER, WeeperEntity.createWeeperAttributes());
    FabricDefaultAttributeRegistry.register(EntityInit.SCORCHED, ScorchedEntity.createScorchedAttributes());
    FabricDefaultAttributeRegistry.register(EntityInit.SOLAR_SMASHER, SolarSmasher.createSolarSmasherAttributes());
    CosmicVeilEntityGeneration.addSpawns();

    ServerLivingEntityEvents.ALLOW_DAMAGE
        .register((LivingEntity entity, DamageSource source, float amount) -> {
          if (source.isOf(DamageTypes.GENERIC_KILL)) {
            return true;
          }
          return ShiftedComponent.shiftedCheck(source.getAttacker(), entity);
        });

    CosmicVeilWorldGen.generateWorldGen();
  }
}
