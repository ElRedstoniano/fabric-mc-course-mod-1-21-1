package net.kaupenjoe.mccourse.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.item.ModItems;
import net.kaupenjoe.mccourse.util.ModTags;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture, BlockTagProvider blockTagProvider) {
        super(output, completableFuture, blockTagProvider);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        // In 1.21.6 getOrCreateTagBuilder -> valueLookupBuilder
        valueLookupBuilder(ModTags.Items.TRANSFORMABLE_ITEMS)
                .add(ModItems.FLUORITE)
                .add(ModItems.RAW_FLUORITE)
                .add(Items.COAL)
                .add(Items.STICK)
                .add(Items.APPLE);

        valueLookupBuilder(ModTags.Items.REPAIRS_FLUORITE_ARMOR)
                .add(ModItems.FLUORITE); //

        //valueLookupBuilder(ItemTags.TRIMMABLE_ARMOR).add(ModItems.FLUORITE_HELMET) ...;
        valueLookupBuilder(ItemTags.HEAD_ARMOR)
                .add(ModItems.FLUORITE_HELMET);
        valueLookupBuilder(ItemTags.CHEST_ARMOR)
                .add(ModItems.FLUORITE_CHESTPLATE);
        valueLookupBuilder(ItemTags.LEG_ARMOR)
                .add(ModItems.FLUORITE_LEGGINGS);
        valueLookupBuilder(ItemTags.FOOT_ARMOR)
                .add(ModItems.FLUORITE_BOOTS);

        valueLookupBuilder(ItemTags.TRIM_MATERIALS)
                .add(ModItems.FLUORITE);
        /*valueLookupBuilder(ItemTags.TRIM_TEMPLATES) // Trim templates no longer exist in 1.12.4
                .add(ModItems.KAUPEN_SMITHING_TEMPLATE);*/

        valueLookupBuilder(ItemTags.VILLAGER_PLANTABLE_SEEDS)
                .add(ModItems.STRAWBERRY_SEEDS);
        valueLookupBuilder(ItemTags.VILLAGER_PICKS_UP)
                .add(ModItems.STRAWBERRY);

        valueLookupBuilder(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.BLACKWOOD_LOG.asItem(), ModBlocks.BLACKWOOD_WOOD.asItem(),
                        ModBlocks.STRIPPED_BLACKWOOD_LOG.asItem(), ModBlocks.STRIPPED_BLACKWOOD_WOOD.asItem());

        valueLookupBuilder(ItemTags.PLANKS)
                .add(ModBlocks.BLACKWOOD_PLANKS.asItem());

        this.copy(ModTags.Blocks.BLACKWOOD_LOGS, ModTags.Items.BLACKWOOD_LOGS);

    }
}
