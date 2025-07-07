package net.kaupenjoe.mccourse.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.util.ModTags;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        // In 1.21.6 getOrCreateTagBuilder -> valueLookupBuilder
        valueLookupBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.FLUORITE_BLOCK,
                        ModBlocks.FLUORITE_ORE,
                        ModBlocks.RAW_FLUORITE_BLOCK,
                        ModBlocks.FLUORITE_DEEPSLATE_ORE,
                        ModBlocks.FLUORITE_END_ORE,
                        ModBlocks.FLUORITE_NETHER_ORE,
                        ModBlocks.FLUORITE_BUTTON,
                        ModBlocks.FLUORITE_PRESSURE_PLATE,
                        ModBlocks.FLUORITE_SLAB,
                        ModBlocks.FLUORITE_STAIRS,
                        ModBlocks.FLUORITE_DOOR,
                        ModBlocks.FLUORITE_TRAPDOOR);
        valueLookupBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.FLUORITE_END_ORE,
                        ModBlocks.FLUORITE_NETHER_ORE);
        valueLookupBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.FLUORITE_DEEPSLATE_ORE)
                .add(ModBlocks.FLUORITE_DOOR)
                .add(ModBlocks.FLUORITE_TRAPDOOR);

        valueLookupBuilder(BlockTags.FENCES).add(ModBlocks.FLUORITE_FENCE);
        valueLookupBuilder(BlockTags.FENCE_GATES).add(ModBlocks.FLUORITE_FENCE_GATE);
        valueLookupBuilder(BlockTags.WALLS).add(ModBlocks.FLUORITE_WALL);

        valueLookupBuilder(ModTags.Blocks.PAXEL_MINEABLE)
                .forceAddTag(BlockTags.PICKAXE_MINEABLE)
                .forceAddTag(BlockTags.AXE_MINEABLE)
                .forceAddTag(BlockTags.SHOVEL_MINEABLE);

        valueLookupBuilder(ModTags.Blocks.FLUORITE_ORES)
                .add(ModBlocks.FLUORITE_ORE)
                .add(ModBlocks.FLUORITE_DEEPSLATE_ORE)
                .add(ModBlocks.FLUORITE_NETHER_ORE)
                .add(ModBlocks.FLUORITE_END_ORE);

        valueLookupBuilder(ModTags.Blocks.STORAGE_BLOCKS_FLUORITE_C)
                .forceAddTag(ModTags.Blocks.FLUORITE_ORES)
                .add(ModBlocks.RAW_FLUORITE_BLOCK);

        valueLookupBuilder(ModTags.Blocks.METAL_DETECTOR_DETECTABLE_BLOCKS)
                .forceAddTag(ConventionalBlockTags.ORES)
                .forceAddTag(ModTags.Blocks.STORAGE_VALUABLE_BLOCKS);

        valueLookupBuilder(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.BLACKWOOD_LOG, ModBlocks.BLACKWOOD_WOOD, ModBlocks.STRIPPED_BLACKWOOD_LOG, ModBlocks.STRIPPED_BLACKWOOD_WOOD);
        valueLookupBuilder(ModTags.Blocks.BLACKWOOD_LOGS)
                .add(ModBlocks.BLACKWOOD_LOG, ModBlocks.BLACKWOOD_WOOD, ModBlocks.STRIPPED_BLACKWOOD_LOG, ModBlocks.STRIPPED_BLACKWOOD_WOOD);

        /*valueLookupBuilder(ConventionalBlockTags.ORES)
                .forceAddTag(ModTags.Blocks.FLUORITE_ORES);*/
    }
}
