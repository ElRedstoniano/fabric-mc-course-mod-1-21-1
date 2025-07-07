package net.kaupenjoe.mccourse.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.kaupenjoe.mccourse.entity.ModEntities;
import net.minecraft.data.tag.ProvidedTagBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.registry.tag.TagBuilder;
import net.minecraft.registry.tag.TagKey;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

//public class ModEntityTypeTagProvider extends FabricTagProvider.FabricValueLookupTagProvider<EntityType<?>> {
public class ModEntityTypeTagProvider extends FabricTagProvider.FabricValueLookupTagProvider.EntityTypeTagProvider {
    public ModEntityTypeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    /*public ModEntityTypeTagProvider(FabricDataOutput output,
                                    CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.ENTITY_TYPE, registriesFuture,
                entityType -> entityType.getRegistryEntry().registryKey());
        // Took a look from VanillaEntityTypeTagProvider class
    }*/

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
       // this.builder(EntityTypeTags.CAN_EQUIP_SADDLE).add(ModEntities.GIRAFFE_ET);
        builder(EntityTypeTags.CAN_EQUIP_SADDLE)
                .add(ModEntities.GIRAFFE_ET.getRegistryEntry().registryKey());
    }
}
