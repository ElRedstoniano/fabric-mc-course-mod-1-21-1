package net.kaupenjoe.mccourse.block.custom;

import net.kaupenjoe.mccourse.item.ModItems;
import net.kaupenjoe.mccourse.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class MagicBlock extends Block {
    public MagicBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {

        world.playSound(player,pos, SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.BLOCKS, 1f, 1f);
        return ActionResult.SUCCESS;
        //return super.onUse(state, world, pos, player, hit);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if(entity instanceof ItemEntity item){
            if(isValidItem(item.getStack())){
                item.setStack(new ItemStack(Items.DIAMOND, item.getStack().getCount()));
            }
        }

        super.onSteppedOn(world, pos, state, entity);
    }

    private boolean isValidItem(ItemStack stack) {
        //return stack.getItem() == ModItems.FLUORITE || stack.getItem() == ModItems.RAW_FLUORITE
                //|| stack.getItem() == Items.COAL;
        return stack.isIn(ModTags.Items.TRANSFORMABLE_ITEMS);
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        tooltip.add(Text.translatable("tooltip.mccourse.magic_block.tooltip.1"));

        super.appendTooltip(stack, context, tooltip, options);
    }
}
