package net.kaupenjoe.mccourse.item.custom;

import net.kaupenjoe.mccourse.components.ModDataComponentTypes;
import net.kaupenjoe.mccourse.sound.ModSounds;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.item.Item;

import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class ChainSawItem extends Item {
    public ChainSawItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();

        if(!world.isClient()){ // Lado del servidor
            if(world.getBlockState(context.getBlockPos()).isIn(BlockTags.LOGS)){
                world.breakBlock(context.getBlockPos(), true, context.getPlayer());

                Consumer<Item> itemConsumer = item -> Objects.requireNonNull(context.getPlayer())
                        .sendEquipmentBreakStatus(item, EquipmentSlot.MAINHAND);

                // Establecer coordenadas actuales con el ModDataComponentsTypes
                context.getStack().set(ModDataComponentTypes.COORDINATES, context.getBlockPos());

                // Es el equivalente a poner:
                /*Consumer<Item> itemConsumer2 = new Consumer<Item>() {
                    @Override
                    public void accept(Item item) {
                        Objects.requireNonNull(context.getPlayer())
                                .sendEquipmentBreakStatus(item, EquipmentSlot.MAINHAND);
                    }
                };*/
                context.getStack()
                        .damage(1, (ServerWorld) world, (ServerPlayerEntity) context.getPlayer(),
                                itemConsumer);
                context.getWorld().playSound(null, context.getBlockPos(), ModSounds.CHAINSAW_CUT, SoundCategory.BLOCKS, 1f, 1f);

                // Server Particles (Via Server, seen by all players)
                BlockPos blockPos = context.getBlockPos();
                ((ServerWorld) context.getWorld()).spawnParticles(ParticleTypes.SMOKE, blockPos.getX() + 0.5f,
                        blockPos.getY() + 1.0f, + blockPos.getZ() + 0.5f, 25, 0.0, 0.05, 0.0, 0.15f);
            } else {
                context.getWorld().playSound(null, context.getBlockPos(), ModSounds.CHAINSAW_PULL, SoundCategory.BLOCKS, 1f, 1f);
            }
        }

        //return super.useOnBlock(context);
        return ActionResult.CONSUME;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getEquippedStack(EquipmentSlot.MAINHAND);
        if(stack.get(ModDataComponentTypes.COORDINATES) != null){
            stack.set(ModDataComponentTypes.COORDINATES, null); // Si no existen las coordenadas, se elimina el campo
        }
        //return super.use(world, user, hand);
        return TypedActionResult.pass(stack); // No reproduce ninguna animación de la mano
        //return TypedActionResult.success(stack); // Reproduce una animación de swing al hacer click derecho
        //return TypedActionResult.success(stack, false); // Reproduce una animación breve de la mano al hacer click derecho
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if(!Screen.hasShiftDown()){
            tooltip.add((Text.translatable("tooltip.mccourse.chainsaw.tooltip.shift")));
        } else{
            tooltip.add((Text.translatable("tooltip.mccourse.chainsaw.tooltip.1")));
            tooltip.add((Text.translatable("tooltip.mccourse.chainsaw.tooltip.2")));
        }

        if (stack.get(ModDataComponentTypes.COORDINATES) != null){ // Si tiene coordenadas registradas se añade un tooltip con el mensaje
            BlockPos blockPos = stack.get(ModDataComponentTypes.COORDINATES);
            Text coordinatesText = Text.of("{" + blockPos.getX() + ", " + blockPos.getY() + ", " + blockPos.getZ() + "}");

            tooltip.add(Text.translatable("tooltip.mccourse.chainsaw.last_chopped_tree",
                    coordinatesText.copy().setStyle(Style.EMPTY.withColor(TextColor.fromRgb(Color.CYAN.getRGB())))));
        }

        super.appendTooltip(stack, context, tooltip, type);
    }
}
