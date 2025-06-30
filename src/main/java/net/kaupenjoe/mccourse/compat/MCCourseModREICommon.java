package net.kaupenjoe.mccourse.compat;

import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REICommonPlugin;
import me.shedaniel.rei.api.common.registry.display.ServerDisplayRegistry;
import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.recipe.CrystallizerRecipe;
import net.kaupenjoe.mccourse.recipe.ModRecipes;
import net.minecraft.util.Identifier;

public class MCCourseModREICommon implements REICommonPlugin {
    private static final Identifier CRYSTALLIZER_DISPLAY = MCCourseMod.id("crystallizer_display");
    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        registry.register(CRYSTALLIZER_DISPLAY, CrystallizerDisplay.SERIALIZER);
    }

    @Override
    public void registerDisplays(ServerDisplayRegistry registry) {
        registry.beginRecipeFiller(CrystallizerRecipe.class)
                .filterType(ModRecipes.CRYSTALLIZER_TYPE)
                .fill(CrystallizerDisplay::new);
    }
}
