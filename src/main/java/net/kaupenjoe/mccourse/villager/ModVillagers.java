package net.kaupenjoe.mccourse.villager;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;

public class ModVillagers {

    public static final RegistryKey<PointOfInterestType> MAGIC_POI_KEY = registerPoiKey("magic_poi");
    public static final PointOfInterestType MAGIC_POI = registerPOI("magic_poi", ModBlocks.MAGIC_BLOCK);
    public static final VillagerProfession KAUPENGER = registerProfession("kaupenger", MAGIC_POI_KEY);

    private static VillagerProfession registerProfession(String name, RegistryKey<PointOfInterestType> type){
        return Registry.register(Registries.VILLAGER_PROFESSION, MCCourseMod.id(name),
                new VillagerProfession(name, entry -> entry.matchesKey(type),
                        entry -> entry.matchesKey(type),
                        ImmutableSet.of(), ImmutableSet.of(), SoundEvents.ENTITY_VILLAGER_WORK_LIBRARIAN));
    }

    private static PointOfInterestType registerPOI(String name, Block block){
        return PointOfInterestHelper.register(MCCourseMod.id(name),
                1 /*cantidad de aldeanos por estación de trabajo*/, 1, block);
    }

    private static RegistryKey<PointOfInterestType> registerPoiKey(String name){
        return RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, MCCourseMod.id(name));
    }

    public static void registerCustomTrades(){
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 1, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 2),
                    new ItemStack(ModItems.STRAWBERRY, 6), 6, 2, 0.04f
            ));
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 9),
                    new ItemStack(ModItems.CHAINSAW, 1), 1, 6, 0.09f
            ));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 2, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.DIAMOND, 6),
                    new ItemStack(ModItems.FLUORITE, 19), 4, 1, 0.04f
            ));
        });

        TradeOfferHelper.registerVillagerOffers(ModVillagers.KAUPENGER, 1, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.DIAMOND, 6),
                    new ItemStack(ModItems.RAW_FLUORITE, 19), 4, 1, 0.04f
            ));
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(ModItems.FLUORITE, 6),
                    new ItemStack(ModItems.SPECTRE_STAFF, 1), 1, 8, 0.04f
            ));
        });
    }

    public static void registerVillagers(){
        MCCourseMod.LOGGER.info("Registering villagers for " + MCCourseMod.MOD_ID);
    }
}
