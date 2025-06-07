package net.kaupenjoe.mccourse.recipe;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModRecipes {
    public static final RecipeSerializer<CrystallizerRecipe> CRYSTALLIZER_SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER,
           MCCourseMod.id("crystallizing"), new CrystallizerRecipe.Serializer());
    public static final RecipeType<CrystallizerRecipe> CRYSTALLIZER_TYPE = Registry.register(Registries.RECIPE_TYPE,
    MCCourseMod.id("crystallizing"), new RecipeType<>(){
        @Override
        public String toString() {
            return "crystallizing";
        }
    });

    public static void registerRecipes(){
        MCCourseMod.LOGGER.info("Registering custom recipes for " + MCCourseMod.MOD_ID);
    }
}
