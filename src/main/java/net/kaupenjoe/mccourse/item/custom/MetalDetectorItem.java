package net.kaupenjoe.mccourse.item.custom;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.kaupenjoe.mccourse.components.FoundBlockData;
import net.kaupenjoe.mccourse.components.ModDataComponentTypes;
import net.kaupenjoe.mccourse.item.ModItems;
import net.kaupenjoe.mccourse.util.InventoryUtil;
import net.kaupenjoe.mccourse.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

public class MetalDetectorItem extends Item {
    public MetalDetectorItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(!context.getWorld().isClient()) {
            BlockPos positionClicked = context.getBlockPos();
            PlayerEntity player = context.getPlayer();
            boolean foundBlock = false;

            for(int i = 0; i <= positionClicked.getY(); i++) {
                BlockState stateBelow = context.getWorld().getBlockState(positionClicked.down(i));
                Block blockBelow = stateBelow.getBlock();

                if(isValuableBlock(stateBelow)) {
                    outputValuableCoordinates(positionClicked.add(0, -i, 0), player, blockBelow);
                    foundBlock = true;

                    if(InventoryUtil.hasPlayerStackInInventory(player, ModItems.DATA_TABLET)) {
                        addComponentToStack(player, positionClicked.add(0, -i, 0), blockBelow);
                    }

                    context.getWorld().playSound(null, positionClicked, SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.BLOCKS, 0.5f, 1f);

                    break;
                }
            }

            if(!foundBlock) {
                player.sendMessage(Text.translatable("item.mccourse.metal_detector.no_valuables"), false);
            }
        }

        return ActionResult.SUCCESS;
    }

    private void addComponentToStack(PlayerEntity player, BlockPos pos, Block blockBelow) {
        ItemStack dataTablet =
                player.getInventory().getStack(InventoryUtil.getFirstInventoryIndex(player, ModItems.DATA_TABLET));

        FoundBlockData data = new FoundBlockData(blockBelow.getDefaultState(), pos);
        dataTablet.set(ModDataComponentTypes.FOUND_BLOCK, data);
    }

    private void outputValuableCoordinates(BlockPos blockPos, PlayerEntity player, Block blockBelow) {
        /*player.sendMessage(Text.literal("Found " + blockBelow.asItem().getName().getString() + " at " +
                "(" + blockPos.getX() + ", " + blockPos.getY() + "," + blockPos.getZ() + ")"), false);*/
        FoundBlockData data = new FoundBlockData(blockBelow.getDefaultState(), blockPos);
        player.sendMessage(data.getOutputString(), false);
    }

    private boolean isValuableBlock(BlockState block) {
        //return block.isOf(Blocks.IRON_ORE);
        return  block.isIn(ConventionalBlockTags.ORES) || block.isIn(ConventionalBlockTags.CHESTS)
                || block.isIn(ModTags.Blocks.METAL_DETECTOR_DETECTABLE_BLOCKS)
                ;
    }

    private MutableText coloredFromBlockText(Block block) {
        return Text.literal(block.getTranslationKey()).copy().setStyle(
                Style.EMPTY.withColor(block.getDefaultMapColor().color));
    }
}