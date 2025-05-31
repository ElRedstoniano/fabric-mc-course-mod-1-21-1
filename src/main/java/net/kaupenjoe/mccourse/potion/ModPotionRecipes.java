package net.kaupenjoe.mccourse.potion;

import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.entry.RegistryEntry;

public class ModPotionRecipes {

    public static void createPotionsRecipes(){
        createRecipe(Potions.AWKWARD, Items.SLIME_BALL, ModPotions.SLIMEY_POTION);
    }

    @SuppressWarnings("unused")
    private static void createRecipe(RegistryEntry<Potion> input, Item ingredient, RegistryEntry<Potion> potion){
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
            builder.registerPotionRecipe(input, ingredient, potion);
        });
    }
}
