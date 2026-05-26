package com.cosmo.datagen;

import com.cosmo.CosmicVeil;
import com.supermartijn642.fusion.api.model.DefaultModelTypes;
import com.supermartijn642.fusion.api.model.ModelInstance;
import com.supermartijn642.fusion.api.model.data.ConnectingModelDataBuilder;
import com.supermartijn642.fusion.api.predicate.DefaultConnectionPredicates;
import com.supermartijn642.fusion.api.provider.FusionModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.util.Identifier;

public class CosmicVeilFusionModelProvider extends FusionModelProvider {
    public CosmicVeilFusionModelProvider(FabricDataOutput output) {
        super(CosmicVeil.MOD_ID, output);
    }

    @Override
    protected void generate() {
        var modelData = ConnectingModelDataBuilder.builder()
                .parent(new Identifier("block/cube_all"))
                .texture("all", new Identifier(CosmicVeil.MOD_ID,"block/dark_shadow_rock_light"))
                .connection(DefaultConnectionPredicates.isSameBlock())
                .build();
        var modelInstance = ModelInstance.of(DefaultModelTypes.CONNECTING, modelData);
        this.addModel(new Identifier(CosmicVeil.MOD_ID,"block/dark_shadow_rock_light"), modelInstance);
        var redmodelData = ConnectingModelDataBuilder.builder()
                .parent(new Identifier("block/cube_all"))
                .texture("all", new Identifier(CosmicVeil.MOD_ID,"block/red_shadow_rock_light"))
                .connection(DefaultConnectionPredicates.isSameBlock())
                .build();
        var redmodelInstance = ModelInstance.of(DefaultModelTypes.CONNECTING, redmodelData);
        this.addModel(new Identifier(CosmicVeil.MOD_ID,"block/red_shadow_rock_light"), redmodelInstance);
        var lightmodelData = ConnectingModelDataBuilder.builder()
                .parent(new Identifier("block/cube_all"))
                .texture("all", new Identifier(CosmicVeil.MOD_ID,"block/light_shadow_rock_light"))
                .connection(DefaultConnectionPredicates.isSameBlock())
                .build();
        var lightmodelInstance = ModelInstance.of(DefaultModelTypes.CONNECTING, lightmodelData);
        this.addModel(new Identifier(CosmicVeil.MOD_ID,"block/light_shadow_rock_light"), lightmodelInstance);
    }
}
