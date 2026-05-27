package com.cosmo.mixin;

import com.cosmo.world.dimension.ShadowRealmEffects;
import com.cosmo.world.dimension.SolarRealmEffects;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DimensionEffects.class)
public class MixinDimensionEffects {
    @Shadow
    private static Object2ObjectMap<Identifier, DimensionEffects> BY_IDENTIFIER;
    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void addCustomEffects(CallbackInfo ci) {
        BY_IDENTIFIER.put(
                new Identifier("cosmic_veil", "shadow_realm"),
                new ShadowRealmEffects()
        );
        BY_IDENTIFIER.put(
                new Identifier("cosmic_veil", "solar_realm"),
                new SolarRealmEffects()
        );
    }


}