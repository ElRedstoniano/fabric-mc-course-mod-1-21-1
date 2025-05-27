package net.kaupenjoe.mccourse.util;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.components.ModDataComponentTypes;
import net.kaupenjoe.mccourse.item.ModItems;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class ModModelPredicates {
    public static void  registerModelPredicates(){
        ModelPredicateProviderRegistry.register(ModItems.DATA_TABLET, Identifier.of(MCCourseMod.MOD_ID, "on"),
                (stack, world, entity, seed) -> stack.get(ModDataComponentTypes.FOUND_BLOCK) != null ? 1f : 0f);

        registerCustomBow(ModItems.KAUPEN_BOW);
    }

    private static void registerCustomBow(Item item){
        // Copiado de la clase ModelPredicateProviderRegistry
        ModelPredicateProviderRegistry.register(item, Identifier.ofVanilla("pull"), (stack, world, entity, seed) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return entity.getActiveItem() != stack ? 0.0F : (float)(stack.getMaxUseTime(entity) - entity.getItemUseTimeLeft()) / 20.0F;
            }
        });

        ModelPredicateProviderRegistry.register(item, Identifier.ofVanilla("pulling"),
                (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F
        );
    }
}
