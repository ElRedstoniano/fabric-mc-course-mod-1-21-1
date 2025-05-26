package net.kaupenjoe.mccourse.util;

import net.minecraft.block.Block;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.awt.*;

public class ColorUtil {
    public static Color removeAlpha(Color color){
        return new Color(color.getRed(),color.getBlue(),color.getGreen(),0);
    }

    public static Text coloredTextFromBlockColor(Block block) {
        return Text.translatable(/*block.getName().getString()*/block.getTranslationKey()).copy().setStyle(
                Style.EMPTY.withColor(block.getDefaultMapColor().color));
    }
}
