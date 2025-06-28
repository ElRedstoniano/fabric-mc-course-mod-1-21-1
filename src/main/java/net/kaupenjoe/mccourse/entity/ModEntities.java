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
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class ModEntities {
    private static final RegistryKey<EntityType<?>> DODO_KEY = RegistryKey.of(RegistryKeys.ENTITY_TYPE, MCCourseMod.id("mantis"));
    private static final RegistryKey<EntityType<?>> GIRAFFE_KEY = RegistryKey.of(RegistryKeys.ENTITY_TYPE, MCCourseMod.id("giraffe"));
    private static final RegistryKey<EntityType<?>> TOMAHAWK_KEY = RegistryKey.of(RegistryKeys.ENTITY_TYPE, MCCourseMod.id("tomahawk"));
    private static final RegistryKey<EntityType<?>> WARTURTLE_KEY = RegistryKey.of(RegistryKeys.ENTITY_TYPE, MCCourseMod.id("warturtle"));

    public static final EntityType<DodoEntity> DODO_ET = Registry.register(Registries.ENTITY_TYPE, MCCourseMod.id("dodo"),
            EntityType.Builder.create(DodoEntity::new, SpawnGroup.CREATURE)
                    .dimensions(1f, 2.5f).build(DODO_KEY));
    public static final EntityType<GiraffeEntity> GIRAFFE_ET = Registry.register(Registries.ENTITY_TYPE, MCCourseMod.id("giraffe"),
            EntityType.Builder.create(GiraffeEntity::new, SpawnGroup.CREATURE)
                    .dimensions(1.5f, 2.5f).build(GIRAFFE_KEY));
    public static final EntityType<TomahawkProjectileEntity> TOMAHAWK_ET = Registry.register(Registries.ENTITY_TYPE, MCCourseMod.id("tomahawk"),
            EntityType.Builder.<TomahawkProjectileEntity>create(TomahawkProjectileEntity::new, SpawnGroup.MISC)
                    .dimensions(0.5f, 1.16f).build(TOMAHAWK_KEY));
    public static final EntityType<WarturtleEntity> WARTURTLE_ET = Registry.register(Registries.ENTITY_TYPE, MCCourseMod.id("warturtle"),
            EntityType.Builder.create(WarturtleEntity::new, SpawnGroup.MISC)
                    .dimensions(2f, 1.5f).build(WARTURTLE_KEY));

    public static void RegisterModEntities() {
        FabricDefaultAttributeRegistry.register(DODO_ET, DodoEntity.createDodoAttributes());
        FabricDefaultAttributeRegistry.register(GIRAFFE_ET, GiraffeEntity.createGiraffeAttributes());
        FabricDefaultAttributeRegistry.register(WARTURTLE_ET, WarturtleEntity.createWarturtleAttributes());

        MCCourseMod.LOGGER.info("Registering mod entities for " + MCCourseMod.MOD_ID);
    }
}
