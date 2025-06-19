package net.kaupenjoe.mccourse.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.entity.custom.DodoEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEntities {
    public static final EntityType<DodoEntity> DODO_ET = Registry.register(Registries.ENTITY_TYPE, MCCourseMod.id("dodo"),
            EntityType.Builder.create(DodoEntity::new, SpawnGroup.CREATURE)
                    .dimensions(1f, 2.5f).build("dodo"));

    public static void RegisterModEntities() {
        MCCourseMod.LOGGER.info("Registering mod entities for " + MCCourseMod.MOD_ID);

        FabricDefaultAttributeRegistry.register(DODO_ET, DodoEntity.createDodoAttributes());
    }
}
