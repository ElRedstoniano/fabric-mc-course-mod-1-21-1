package net.kaupenjoe.mccourse.compat;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.kaupenjoe.mccourse.recipe.CrystallizerRecipe;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.RecipeEntry;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CrystallizerDisplay extends BasicDisplay {
    //public CrystallizerDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
    /*public CrystallizerDisplay(RecipeEntry<CrystallizerRecipe> recipe) {
        super(List.of(EntryIngredients.ofIngredient(recipe.value().getIngredients().get(0))),
                List.of(EntryIngredient.of(EntryStacks.of(recipe.value().output()))));
    }*/ // 1.21.1

    // Copied from DefaultPathingDisplay class // another example: ClientsidedCookingDisplay
    // The used codec is similar to the CrystallizerRecipe.Serializer.CODEC one
    public static final DisplaySerializer<CrystallizerDisplay> SERIALIZER = DisplaySerializer.of(
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    EntryIngredient.codec().fieldOf("ingredient").forGetter(CrystallizerDisplay::getIn),
                    EntryIngredient.codec().fieldOf("result").forGetter(CrystallizerDisplay::getOut)
            ).apply(instance, CrystallizerDisplay::new)),
            PacketCodec.tuple(
                    EntryIngredient.streamCodec(),
                    CrystallizerDisplay::getIn,
                    EntryIngredient.streamCodec(),
                    CrystallizerDisplay::getOut,
                    CrystallizerDisplay::new
            ));

    // Original - 1.21.1
    /*public CrystallizerDisplay(CrystallizerRecipe crystallizerRecipe) {
        super(List.of(EntryIngredients.ofIngredient(crystallizerRecipe.getIngredients().get(0))),
                List.of(EntryIngredient.of(EntryStacks.of(crystallizerRecipe.output()))));
    }*/

    public CrystallizerDisplay(RecipeEntry<CrystallizerRecipe> crystallizerRecipe) { // 1.21.2-3+
        super(List.of(EntryIngredients.ofIngredient(crystallizerRecipe.value().getIngredients().get(0))),
                List.of(EntryIngredient.of(EntryStacks.of(crystallizerRecipe.value().output()))));
    }

    public CrystallizerDisplay(EntryIngredient in, EntryIngredient out) {
        this(List.of(in), List.of(out));
    }

    public CrystallizerDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return CrystallizerCategory.CRYSTALLIZER;
    }

    @Override
    public @Nullable DisplaySerializer<? extends Display> getSerializer() {
        return SERIALIZER;
    }
    public final EntryIngredient getIn() {
        return getInputEntries().get(0);
    }

    public final EntryIngredient getOut() {
        return getOutputEntries().get(0);
    }
}
