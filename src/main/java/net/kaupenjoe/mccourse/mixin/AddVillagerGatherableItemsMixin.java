package net.kaupenjoe.mccourse.mixin;

import net.minecraft.entity.passive.VillagerEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(VillagerEntity.class)
public class AddVillagerGatherableItemsMixin {
    /*@Mutable
    @SuppressWarnings("ShadowModifiers")
    @Shadow @Final
    public static Map<Item, Integer> ITEM_FOOD_VALUES;
    //public static Set<Item> GATHERABLE_ITEMS;

    static {
        //ModItemGroup.registerItemGroup();
        ITEM_FOOD_VALUES = ImmutableMap.<Item, Integer>builder()
                .putAll(ITEM_FOOD_VALUES)
                .put(Items.MELON, 1)
                .put(ModItems.STRAWBERRY_SEEDS, 1)
                .put(ModItems.STRAWBERRY, 1)
                .build();
    }*/ // Fabric ya incluye una api que permite registrar items coleccionables, compostables y comestibles para
    // los aldeanos, por lo que esto ya no sirve del todo_ (VillagerInteractionRegistries)
}
