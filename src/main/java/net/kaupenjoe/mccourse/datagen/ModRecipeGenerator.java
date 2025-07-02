package net.kaupenjoe.mccourse.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.item.ModArmorMaterials;
import net.kaupenjoe.mccourse.item.ModItems;
import net.kaupenjoe.mccourse.trim.ModTrimMaterials;
import net.kaupenjoe.mccourse.trim.ModTrimPatterns;
import net.kaupenjoe.mccourse.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeGenerator extends FabricRecipeProvider {
    public ModRecipeGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                List<ItemConvertible> FLUORITE_SMELTABLES = List.of(ModItems.RAW_FLUORITE, ModBlocks.FLUORITE_ORE,
                        ModBlocks.FLUORITE_DEEPSLATE_ORE, ModBlocks.FLUORITE_NETHER_ORE, ModBlocks.FLUORITE_END_ORE);
                offerSmelting(FLUORITE_SMELTABLES, RecipeCategory.MISC, ModItems.FLUORITE,
                        .2F, 200, "fluorite");
                offerBlasting(FLUORITE_SMELTABLES, RecipeCategory.MISC, ModItems.FLUORITE,
                        .2F, 100, "fluorite");

                // Receta reversible 9x9
                offerReversibleCompactingRecipes(RecipeCategory.BUILDING_BLOCKS,
                        ModItems.FLUORITE, RecipeCategory.DECORATIONS, ModBlocks.FLUORITE_BLOCK);

                // ShapedRecipeJsonBuilder.create -> createShaped
                createShaped(RecipeCategory.MISC, ModItems.RAW_FLUORITE)
                        .pattern("SSS")
                        .pattern("SFS")
                        .pattern("SSS")
                        .input('S', Blocks.STONE)
                        .input('F', ModItems.FLUORITE)
                        .criterion(hasItem(Blocks.STONE), conditionsFromItem(Blocks.STONE)) // criterion hace que la receta se desbloquee si se cumple una de las condiciones que se especifican
                        .criterion(hasItem(ModItems.FLUORITE), conditionsFromItem(ModItems.FLUORITE))
                        .offerTo(recipeExporter);

                createShaped(RecipeCategory.MISC, ModItems.RAW_FLUORITE)
                        .pattern("SSS")
                        .pattern("FFF")
                        .pattern("SSS")
                        .input('S', Blocks.STONE)
                        .input('F', ModItems.FLUORITE)
                        .criterion(hasItem(Blocks.STONE), conditionsFromItem(Blocks.STONE))
                        // criterion hace que la receta se desbloquee si se cumple una de las condiciones que se especifican
                        .criterion(hasItem(ModItems.FLUORITE), conditionsFromItem(ModItems.FLUORITE))
                        .offerTo(recipeExporter, RegistryKey.of(RegistryKeys.RECIPE, Identifier.of(MCCourseMod.MOD_ID, "raw_fluorite_2")));
                // La última línea señala una ruta alternativa para evitar una receta duplicada, señalando una id distinta para una nueva receta

                // Bloque de fluorita en crudo
                createShaped(RecipeCategory.MISC, ModBlocks.RAW_FLUORITE_BLOCK)
                        .pattern("RRR")
                        .pattern("RRR")
                        .pattern("RRR")
                        .input('R', ModItems.RAW_FLUORITE)
                        .criterion(hasItem(ModItems.RAW_FLUORITE), conditionsFromItem(ModItems.RAW_FLUORITE)) // criterion hace que la receta se desbloquee si se cumple una de las condiciones que se especifican
                        .offerTo(recipeExporter);

                //Slabs
                generateSlabRecipes(ModBlocks.FLUORITE_SLAB, ModBlocks.FLUORITE_BLOCK, recipeExporter, this);
                // Stairs
                generateStairsRecipes(ModBlocks.FLUORITE_STAIRS, ModBlocks.FLUORITE_BLOCK, recipeExporter, this);
                // Button
                generateButtonRecipe(ModBlocks.FLUORITE_BUTTON, ModBlocks.FLUORITE_BLOCK, recipeExporter, this);
                // Pressure plate
                offerPressurePlateRecipe(ModBlocks.FLUORITE_PRESSURE_PLATE, ModBlocks.FLUORITE_BLOCK);
                // Fences
                offerFenceRecipe(ModBlocks.FLUORITE_BLOCK, ModBlocks.FLUORITE_FENCE, recipeExporter, this);
                // Fence gate
                offerFenceGateRecipe(ModBlocks.FLUORITE_BLOCK, ModBlocks.FLUORITE_FENCE_GATE, recipeExporter, this);
                // Wall
                offerWallRecipe(RecipeCategory.BUILDING_BLOCKS,ModBlocks.FLUORITE_WALL, ModBlocks.FLUORITE_BLOCK);
                // Door
                offerDoorRecipe(ModBlocks.FLUORITE_DOOR, ModBlocks.FLUORITE_BLOCK, recipeExporter, this);
                // Trapdoor
                offerTrapdoorRecipe(ModBlocks.FLUORITE_TRAPDOOR, ModBlocks.FLUORITE_BLOCK, recipeExporter, this);


                // Sword
                createShaped(RecipeCategory.TOOLS, ModItems.FLUORITE_SWORD)
                        .input('#', Items.STICK)
                        .input('X', ModItems.FLUORITE)
                        .pattern("XX")
                        .pattern(" #")
                        .pattern(" #")
                        .criterion(hasItem(ModItems.FLUORITE), conditionsFromItem(ModItems.FLUORITE))
                        .offerTo(recipeExporter);
                // Pickaxe
                createShaped(RecipeCategory.TOOLS, ModItems.FLUORITE_PICKAXE)
                        .input('#', Items.STICK)
                        .input('X', ModItems.FLUORITE)
                        .pattern("XXX")
                        .pattern(" # ")
                        .pattern(" # ")
                        .criterion(hasItem(ModItems.FLUORITE), conditionsFromItem(ModItems.FLUORITE))
                        .offerTo(recipeExporter);
                // Shovel
                createShaped(RecipeCategory.TOOLS, ModItems.FLUORITE_SHOVEL)
                        .input('#', Items.STICK)
                        .input('X', ModItems.FLUORITE)
                        .pattern("X")
                        .pattern("#")
                        .pattern("#")
                        .criterion(hasItem(ModItems.FLUORITE), conditionsFromItem(ModItems.FLUORITE))
                        .offerTo(recipeExporter);
                // Axe
                createShaped(RecipeCategory.TOOLS, ModItems.FLUORITE_AXE)
                        .input('#', Items.STICK)
                        .input('X', ModItems.FLUORITE)
                        .pattern("XX")
                        .pattern("X#")
                        .pattern(" #")
                        .criterion(hasItem(ModItems.FLUORITE), conditionsFromItem(ModItems.FLUORITE))
                        .offerTo(recipeExporter);
                // Hoe
                createShaped(RecipeCategory.TOOLS, ModItems.FLUORITE_HOE)
                        .input('#', Items.STICK)
                        .input('X', ModItems.FLUORITE)
                        .pattern("XX")
                        .pattern(" #")
                        .pattern(" #")
                        .criterion(hasItem(ModItems.FLUORITE), conditionsFromItem(ModItems.FLUORITE))
                        .offerTo(recipeExporter);

                // Paxel
                createShaped(RecipeCategory.TOOLS, ModItems.FLUORITE_PAXEL)
                        .input('#', Items.STICK)
                        .input('X', ModItems.FLUORITE)
                        .pattern("XXX")
                        .pattern("X# ")
                        .pattern(" # ")
                        .criterion(hasItem(ModItems.FLUORITE), conditionsFromItem(ModItems.FLUORITE))
                        .offerTo(recipeExporter);
                // Paxel 2
                createShaped(RecipeCategory.TOOLS, ModItems.FLUORITE_PAXEL)
                        .input('#', Items.STICK)
                        .input('X', ModItems.FLUORITE)
                        .pattern("XXX")
                        .pattern(" #X")
                        .pattern(" # ")
                        .criterion(hasItem(ModItems.FLUORITE), conditionsFromItem(ModItems.FLUORITE))
                        .offerTo(recipeExporter, RegistryKey.of(RegistryKeys.RECIPE, Identifier.of(MCCourseMod.MOD_ID, "paxel_2")));
                // Hammer
                createShaped(RecipeCategory.TOOLS, ModItems.FLUORITE_HAMMER)
                        .input('#', Items.STICK)
                        .input('X', ModItems.FLUORITE)
                        .pattern("XXX")
                        .pattern("X#X")
                        .pattern(" # ")
                        .criterion(hasItem(ModItems.FLUORITE), conditionsFromItem(ModItems.FLUORITE))
                        .offerTo(recipeExporter);

                // Helmet
                createShaped(RecipeCategory.MISC, ModItems.FLUORITE_HELMET)
                        .input('X', ModItems.FLUORITE)
                        .pattern("XXX")
                        .pattern("X X")
                        //.pattern(" # ")
                        .criterion(hasItem(ModItems.FLUORITE), conditionsFromItem(ModItems.FLUORITE))
                        .offerTo(recipeExporter);
                //Chestplate
                createShaped(RecipeCategory.MISC, ModItems.FLUORITE_CHESTPLATE)
                        .input('X', ModItems.FLUORITE)
                        .pattern("X X")
                        .pattern("XXX")
                        .pattern("XXX")
                        //.pattern(" # ")
                        .criterion(hasItem(ModItems.FLUORITE), conditionsFromItem(ModItems.FLUORITE))
                        .offerTo(recipeExporter);
                //Leggings
                createShaped(RecipeCategory.MISC, ModItems.FLUORITE_LEGGINGS)
                        .input('X', ModItems.FLUORITE)
                        .pattern("XXX")
                        .pattern("X X")
                        .pattern("X X")
                        .criterion(hasItem(ModItems.FLUORITE), conditionsFromItem(ModItems.FLUORITE))
                        .offerTo(recipeExporter);
                //Boots
                createShaped(RecipeCategory.MISC, ModItems.FLUORITE_BOOTS)
                        .input('X', ModItems.FLUORITE)
                        .pattern("X X")
                        .pattern("X X")
                        .criterion(hasItem(ModItems.FLUORITE), conditionsFromItem(ModItems.FLUORITE))
                        .offerTo(recipeExporter);

                // Kaupen template armor
                offerSmithingTrimRecipe(ModItems.KAUPEN_SMITHING_TEMPLATE, ModTrimPatterns.KAUPEN,
                        RegistryKey.of(RegistryKeys.RECIPE, Identifier.of(MCCourseMod.MOD_ID, "kaupen")));

                offerPlanksRecipe(ModBlocks.BLACKWOOD_PLANKS, ModTags.Items.BLACKWOOD_LOGS, 4);
            }
        };
    }
    /*
    @Override
    public void generate(RecipeExporter recipeExporter) {

    }*/ // Todo_ lo que había pasa a estar en el método_ generate de arriba

    public static void generateStairsRecipes(Block outputBlock, Block parentBlock, RecipeExporter recipeExporter, RecipeGenerator recipeGenerator){
        if(outputBlock instanceof StairsBlock stairsBlock){
            // RecipeProvider -> RecipeGenerator (recipeGenerator parameter)
            recipeGenerator.offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, stairsBlock,
                    parentBlock, 1);
            // createStairsRecipes ->
            recipeGenerator.createStairsRecipe(stairsBlock, Ingredient.ofItems(parentBlock))
                    .criterion("has_fluorite_block", recipeGenerator.conditionsFromItem(parentBlock))
                    .offerTo(recipeExporter);
        }
    }

    public static void generateSlabRecipes(Block outputBlock, Block parentBlock, RecipeExporter recipeExporter, RecipeGenerator recipeGenerator) {
        if (outputBlock instanceof SlabBlock slabBlock) {
            recipeGenerator.offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, slabBlock,
                    parentBlock, 1);
            recipeGenerator.offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, slabBlock, parentBlock);
        }
    }

    public static void generateButtonRecipe(Block outputBlock, Block inputblock, RecipeExporter recipeExporter, RecipeGenerator recipeGenerator){
        // ShapelessRecipeJsonBuilder.create -> createShapeless
        recipeGenerator.createShapeless(RecipeCategory.BUILDING_BLOCKS, outputBlock, 1)
                .input(inputblock)
                .group("fluorite_buttons")
                .criterion("has_fluorite_block", recipeGenerator.conditionsFromItem(ModBlocks.FLUORITE_BLOCK))
                .offerTo(recipeExporter);
    }

    public static void offerFenceRecipe(Block inputBlock, Block outputBlock, RecipeExporter recipeExporter, RecipeGenerator recipeGenerator){
        recipeGenerator.createFenceRecipe(outputBlock, Ingredient.ofItems(inputBlock))
                .criterion(RecipeGenerator.hasItem(inputBlock), recipeGenerator.conditionsFromItem(inputBlock))
                .offerTo(recipeExporter);
    }

    public static void offerFenceGateRecipe(Block inputBlock, Block outputBlock, RecipeExporter recipeExporter, RecipeGenerator recipeGenerator){
        recipeGenerator.createFenceGateRecipe(outputBlock, Ingredient.ofItems(inputBlock))
                .criterion(RecipeGenerator.hasItem(inputBlock), recipeGenerator.conditionsFromItem(inputBlock))
                .offerTo(recipeExporter);
    }

    private void offerDoorRecipe(Block output, Block input, RecipeExporter recipeExporter, RecipeGenerator recipeGenerator) {
        recipeGenerator.createDoorRecipe(output, Ingredient.ofItems(input))
                .criterion(RecipeGenerator.hasItem(input), recipeGenerator.conditionsFromItem(input))
                .offerTo(recipeExporter);
    }

    private void offerTrapdoorRecipe(Block output, Block input, RecipeExporter recipeExporter, RecipeGenerator recipeGenerator) {
        recipeGenerator.createTrapdoorRecipe(output, Ingredient.ofItems(input))
                .criterion(RecipeGenerator.hasItem(input), recipeGenerator.conditionsFromItem(input))
                .offerTo(recipeExporter);
    }

    @Override
    public String getName() {
        return "Tuortialmod Recipes";
    }
}
