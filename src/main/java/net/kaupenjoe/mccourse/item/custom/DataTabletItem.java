package net.kaupenjoe.mccourse.item.custom;

import net.kaupenjoe.mccourse.components.ModDataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.List;

public class DataTabletItem extends Item{
    public DataTabletItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if(stack.get(ModDataComponentTypes.FOUND_BLOCK) != null){
            stack.set(ModDataComponentTypes.FOUND_BLOCK, null);
        }

        return super.use(world, user, hand);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.get(ModDataComponentTypes.FOUND_BLOCK) != null;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (stack.get(ModDataComponentTypes.FOUND_BLOCK) != null){
            //tooltip.add(Text.literal(stack.get(ModDataComponentTypes.FOUND_BLOCK).getOutputString()));
            tooltip.add(stack.get(ModDataComponentTypes.FOUND_BLOCK).getOutputString());
        }
    }
}
