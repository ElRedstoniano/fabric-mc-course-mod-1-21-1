package net.kaupenjoe.mccourse.compat;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.recipe.CrystallizerRecipe;
import net.kaupenjoe.mccourse.recipe.ModRecipes;
import net.kaupenjoe.mccourse.screen.custom.CrystallyzerScreen;

public class MCCourseModREIClient implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        //REIClientPlugin.super.registerCategories(registry);
        registry.add(new CrystallizerCategory());

        registry.addWorkstations(CrystallizerCategory.CRYSTALLIZER,
                EntryStacks.of(ModBlocks.CRYSTALLIZER)); // Esto al presionar U mostrará la categoría
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        //REIClientPlugin.super.registerDisplays(registry);
        registry.registerRecipeFiller(CrystallizerRecipe.class, ModRecipes.CRYSTALLIZER_TYPE, CrystallizerDisplay::new);
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        //REIClientPlugin.super.registerScreens(registry);
        registry.registerClickArea(screen -> new Rectangle(((screen.width - 176) / 2) + 78,
                ((screen.height - 166) / 2) + 30, 20, 25),
                CrystallyzerScreen.class, CrystallizerCategory.CRYSTALLIZER);

        // Añade un área clickable encima del icono de la flecha para que al hacerle click se puedan ver las recetas registradas
    }
}
