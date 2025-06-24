package net.kaupenjoe.mccourse.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.entity.custom.DodoEntity;
import net.kaupenjoe.mccourse.entity.custom.GiraffeEntity;
import net.kaupenjoe.mccourse.entity.custom.TomahawkProjectileEntity;
import net.kaupenjoe.mccourse.entity.custom.WarturtleEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEntities {

    public static final EntityType<DodoEntity> DODO_ET = Registry.register(Registries.ENTITY_TYPE, MCCourseMod.id("dodo"),
            EntityType.Builder.create(DodoEntity::new, SpawnGroup.CREATURE)
                    .dimensions(1f, 2.5f).build("dodo"));
    public static final EntityType<GiraffeEntity> GIRAFFE_ET = Registry.register(Registries.ENTITY_TYPE, MCCourseMod.id("giraffe"),
            EntityType.Builder.create(GiraffeEntity::new, SpawnGroup.CREATURE)
                    .dimensions(1.5f, 2.5f).build("giraffe"));
    public static final EntityType<TomahawkProjectileEntity> TOMAHAWK_ET = Registry.register(Registries.ENTITY_TYPE, MCCourseMod.id("tomahawk"),
            EntityType.Builder.<TomahawkProjectileEntity>create(TomahawkProjectileEntity::new, SpawnGroup.MISC)
                    .dimensions(0.5f, 1.16f).build("tomahawk"));
    public static final EntityType<WarturtleEntity> WARTURTLE_ET = Registry.register(Registries.ENTITY_TYPE, MCCourseMod.id("warturtle"),
            EntityType.Builder.create(WarturtleEntity::new, SpawnGroup.MISC)
                    .dimensions(2f, 1.5f).build("warturtle"));

    public static void RegisterModEntities() {
        FabricDefaultAttributeRegistry.register(DODO_ET, DodoEntity.createDodoAttributes());
        FabricDefaultAttributeRegistry.register(GIRAFFE_ET, GiraffeEntity.createGiraffeAttributes());
        FabricDefaultAttributeRegistry.register(WARTURTLE_ET, WarturtleEntity.createWarturtleAttributes());

        MCCourseMod.LOGGER.info("Registering mod entities for " + MCCourseMod.MOD_ID);
    }
}
