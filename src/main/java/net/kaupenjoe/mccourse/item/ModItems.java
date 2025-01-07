package net.kaupenjoe.mccourse.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.item.custom.ChainSawItem;
import net.kaupenjoe.mccourse.item.custom.ModFoodComponents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item FLUORITE = registerItem("fluorite", new Item(new Item.Settings()));
    public static final Item RAW_FLUORITE = registerItem("raw_fluorite", new Item(new Item.Settings()));

    public static final Item CHAINSAW = registerItem("chainsaw",
            new ChainSawItem(new Item.Settings().maxDamage(32)));
    public static final Item STRAWBERRY = registerItem("strawberry",
            new Item(new Item.Settings().food(ModFoodComponents.STRAWBERRY)));

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM,
                Identifier.of(MCCourseMod.MOD_ID, name), item);
    }

    private static void customIngredients(FabricItemGroupEntries entries){
        entries.add(FLUORITE);
        entries.add(RAW_FLUORITE);
    }

    public static  void registerModItems(){
        MCCourseMod.LOGGER.info("Registering Mod Items for " + MCCourseMod.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::customIngredients);
    }
}
