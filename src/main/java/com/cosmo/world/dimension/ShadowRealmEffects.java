package com.cosmo.world.dimension;

import net.minecraft.client.render.DimensionEffects;
import net.minecraft.util.math.Vec3d;

public class ShadowRealmEffects extends DimensionEffects {
    public ShadowRealmEffects() {
        super(
            Float.NaN,   // clouds height (NaN = no clouds)
            false,       // alternateSkyColor
            SkyType.NONE, // no vanilla sky renderer, let FabricSkyBoxes handle it
            false,       // brightenLighting
            true         // darkened block faces
        );
    }

    @Override
    public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
        // Return color as-is, no time-of-day adjustment
        return color;
    }

    @Override
    public boolean useThickFog(int camX, int camY) {
        return true; // thick oppressive fog
    }
}