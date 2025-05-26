package net.kaupenjoe.mccourse.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kaupenjoe.mccourse.util.ColorUtil;
import net.minecraft.block.BlockState;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.util.Objects;

public class FoundBlockData {
    private final BlockState block;
    private final BlockPos position;

    public static final Codec<FoundBlockData> CODEC = RecordCodecBuilder.create( instance ->
            instance.group(BlockState.CODEC.fieldOf("block").forGetter(FoundBlockData::getBlock),
                    BlockPos.CODEC.fieldOf("position").forGetter(FoundBlockData::getPosition))
                    .apply(instance, FoundBlockData::new /* Equivalente a poner (block, pos) -> new FoundBlockData(block, pos)*/));

    public FoundBlockData(BlockState block, BlockPos position) {
        this.block = block;
        this.position = position;
    }

    public BlockState getBlock() {
        return block;
    }

    public BlockPos getPosition() {
        return position;
    }

    public String getOutputStringRaw() {
        return block.getBlock().asItem().getName().getString() + " at " +
                "(" + position.getX() + ", " + position.getY() + "," + position.getZ() + ")";
    }

    public Text getOutputString() {
        return Text.translatable("item.mccourse.metal_detector.valuable_found", ColorUtil.coloredTextFromBlockColor(block.getBlock()),
                Text.of("(" + position.getX() + ", " + position.getY() + ", " + position.getZ() + ")")
                        .copy().setStyle(Style.EMPTY.withColor(TextColor.fromRgb(Color.CYAN.getRGB())))
                );
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.block, this.position);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this){
            return true;
        } else {
            return obj instanceof FoundBlockData fbd && this.block == fbd.block && this.position == fbd.position;
        }
        // return super.equals(obj);
    }
}
