package net.kaupenjoe.mccourse.mixin;

import com.google.common.collect.ImmutableSet;
import net.kaupenjoe.mccourse.item.ModItems;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Set;

@Mixin(VillagerEntity.class)
public class AddVillagerGatherableItemsMixin {
    @SuppressWarnings("ShadowModifiers")
    @Shadow @Final
    public static Set<Item> GATHERABLE_ITEMS;

    static {
        //ModItemGroup.registerItemGroup();
        //noinspection ShadowFinalModification
        GATHERABLE_ITEMS = ImmutableSet.<Item>builder()
                .addAll(GATHERABLE_ITEMS)
                .add(Items.MELON)
                .add(ModItems.STRAWBERRY_SEEDS)
                .build();
    }
}
