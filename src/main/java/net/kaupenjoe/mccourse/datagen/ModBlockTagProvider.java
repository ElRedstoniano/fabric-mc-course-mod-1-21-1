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
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
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
        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.FLUORITE_END_ORE,
                        ModBlocks.FLUORITE_NETHER_ORE);
        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.FLUORITE_DEEPSLATE_ORE)
                .add(ModBlocks.FLUORITE_DOOR)
                .add(ModBlocks.FLUORITE_TRAPDOOR);

        getOrCreateTagBuilder(BlockTags.FENCES).add(ModBlocks.FLUORITE_FENCE);
        getOrCreateTagBuilder(BlockTags.FENCE_GATES).add(ModBlocks.FLUORITE_FENCE_GATE);
        getOrCreateTagBuilder(BlockTags.WALLS).add(ModBlocks.FLUORITE_WALL);

        getOrCreateTagBuilder(ModTags.Blocks.PAXEL_MINEABLE)
                .forceAddTag(BlockTags.PICKAXE_MINEABLE)
                .forceAddTag(BlockTags.AXE_MINEABLE)
                .forceAddTag(BlockTags.SHOVEL_MINEABLE);

        getOrCreateTagBuilder(ModTags.Blocks.FLUORITE_ORES)
                .add(ModBlocks.FLUORITE_ORE)
                .add(ModBlocks.FLUORITE_DEEPSLATE_ORE)
                .add(ModBlocks.FLUORITE_NETHER_ORE)
                .add(ModBlocks.FLUORITE_END_ORE);

        getOrCreateTagBuilder(ModTags.Blocks.STORAGE_BLOCKS_FLUORITE_C)
                .forceAddTag(ModTags.Blocks.FLUORITE_ORES)
                .add(ModBlocks.RAW_FLUORITE_BLOCK);

        getOrCreateTagBuilder(ModTags.Blocks.METAL_DETECTOR_DETECTABLE_BLOCKS)
                .forceAddTag(ConventionalBlockTags.ORES)
                .forceAddTag(ModTags.Blocks.STORAGE_VALUABLE_BLOCKS);

        /*getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.BLACKWOOD_LOG, ModBlocks.BLACKWOOD_WOOD, ModBlocks.STRIPPED_BLACKWOOD_LOG, ModBlocks.STRIPPED_BLACKWOOD_WOOD);*/

        /*getOrCreateTagBuilder(ConventionalBlockTags.ORES)
                .forceAddTag(ModTags.Blocks.FLUORITE_ORES);*/
    }
}
