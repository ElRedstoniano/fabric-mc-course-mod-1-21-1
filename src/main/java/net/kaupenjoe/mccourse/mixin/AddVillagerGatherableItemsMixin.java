package net.kaupenjoe.mccourse.mixin;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import net.kaupenjoe.mccourse.item.ModItems;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;
import java.util.Set;

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
