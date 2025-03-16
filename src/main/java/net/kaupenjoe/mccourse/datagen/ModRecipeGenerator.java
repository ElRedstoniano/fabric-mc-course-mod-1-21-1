package net.kaupenjoe.mccourse.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeGenerator extends FabricRecipeProvider {
    public ModRecipeGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter recipeExporter) {
        List<ItemConvertible> FLUORITE_SMELTABLES = List.of(ModItems.RAW_FLUORITE, ModBlocks.FLUORITE_ORE,
                ModBlocks.FLUORITE_DEEPSLATE_ORE, ModBlocks.FLUORITE_NETHER_ORE, ModBlocks.FLUORITE_END_ORE);
        offerSmelting(recipeExporter, FLUORITE_SMELTABLES, RecipeCategory.MISC, ModItems.FLUORITE,
                .2F, 200, "fluorite");
        offerBlasting(recipeExporter, FLUORITE_SMELTABLES, RecipeCategory.MISC, ModItems.FLUORITE,
                .2F, 100, "fluorite");

        // Receta reversible 9x9
        offerReversibleCompactingRecipes(recipeExporter, RecipeCategory.BUILDING_BLOCKS,
                ModItems.FLUORITE, RecipeCategory.DECORATIONS, ModBlocks.FLUORITE_BLOCK);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.RAW_FLUORITE)
                .pattern("SSS")
                .pattern("SFS")
                .pattern("SSS")
                .input('S', Blocks.STONE)
                .input('F', ModItems.FLUORITE)
                .criterion(hasItem(Blocks.STONE), conditionsFromItem(Blocks.STONE)) // criterion hace que la receta se desbloquee si se cumple una de las condiciones que se especifican
                .criterion(hasItem(ModItems.FLUORITE), conditionsFromItem(ModItems.FLUORITE))
                .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.RAW_FLUORITE)
                .pattern("SSS")
                .pattern("FFF")
                .pattern("SSS")
                .input('S', Blocks.STONE)
                .input('F', ModItems.FLUORITE)
                .criterion(hasItem(Blocks.STONE), conditionsFromItem(Blocks.STONE))
                // criterion hace que la receta se desbloquee si se cumple una de las condiciones que se especifican
                .criterion(hasItem(ModItems.FLUORITE), conditionsFromItem(ModItems.FLUORITE))
                .offerTo(recipeExporter, Identifier.of(MCCourseMod.MOD_ID, "raw_fluorite_2"));
        // La última línea señala una ruta alternativa para evitar una receta duplicada, señalando una id distinta para una nueva receta

        //Slabs
        generateSlabRecipes(ModBlocks.FLUORITE_SLAB, ModBlocks.FLUORITE_BLOCK, recipeExporter);
        // Stairs
        generateStairsRecipes(ModBlocks.FLUORITE_STAIRS, ModBlocks.FLUORITE_BLOCK, recipeExporter);
        // Button
        generateButtonRecipe(ModBlocks.FLUORITE_BUTTON, ModBlocks.FLUORITE_BLOCK, recipeExporter);
        // Pressure plate
        offerPressurePlateRecipe(recipeExporter, ModBlocks.FLUORITE_PRESSURE_PLATE, ModBlocks.FLUORITE_BLOCK);
    }

    public static void generateStairsRecipes(Block outputBlock, Block parentBlock, RecipeExporter recipeExporter){
        if(outputBlock instanceof StairsBlock stairsBlock){

            RecipeProvider.offerStonecuttingRecipe(recipeExporter, RecipeCategory.BUILDING_BLOCKS, stairsBlock,
                    parentBlock, 1);
            createStairsRecipe(stairsBlock, Ingredient.ofItems(parentBlock))
                    .criterion("has_fluorite_block", conditionsFromItem(parentBlock))
                    .offerTo(recipeExporter);
        }
    }

    public static void generateSlabRecipes(Block outputBlock, Block parentBlock, RecipeExporter recipeExporter) {
        if (outputBlock instanceof SlabBlock slabBlock) {
            RecipeProvider.offerStonecuttingRecipe(recipeExporter, RecipeCategory.BUILDING_BLOCKS, slabBlock,
                    parentBlock, 1);
            offerSlabRecipe(recipeExporter, RecipeCategory.BUILDING_BLOCKS, slabBlock, parentBlock);
        }
    }

    public static void generateButtonRecipe(Block outputBlock, Block inputblock, RecipeExporter recipeExporter){
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, outputBlock, 1)
                .input(inputblock)
                .group("fluorite_buttons")
                .criterion("has_fluorite_block", conditionsFromItem(ModBlocks.FLUORITE_BLOCK))
                .offerTo(recipeExporter);
    }

}
