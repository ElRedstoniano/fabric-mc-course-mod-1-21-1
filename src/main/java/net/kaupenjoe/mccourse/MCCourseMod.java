package net.kaupenjoe.mccourse;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.command.ReturnHomeCommand;
import net.kaupenjoe.mccourse.command.SetHomeCommand;
import net.kaupenjoe.mccourse.components.ModDataComponentTypes;
import net.kaupenjoe.mccourse.effect.ModEffects;
import net.kaupenjoe.mccourse.event.AttackEntityHandler;
import net.kaupenjoe.mccourse.event.PlayerCopyHandler;
import net.kaupenjoe.mccourse.item.ModItemGroups;
import net.kaupenjoe.mccourse.item.ModItems;
import net.kaupenjoe.mccourse.potion.ModPotionRecipes;
import net.kaupenjoe.mccourse.potion.ModPotions;
import net.kaupenjoe.mccourse.sound.ModSounds;
import net.kaupenjoe.mccourse.util.HammerUsageEvent;
import net.minecraft.block.ComposterBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MCCourseMod implements ModInitializer {
	public static final String MOD_ID = "mccourse";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		//

		LOGGER.info("Hello Fabric world!");
		ModItemGroups.registerItemGroup();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

		ModSounds.registerSounds();

		ModDataComponentTypes.registerDataComponentsTypes();

		FuelRegistry.INSTANCE.add(ModItems.STARLIGHT_ASHES, 600);

		PlayerBlockBreakEvents.BEFORE.register(new HammerUsageEvent());

		AttackEntityCallback.EVENT.register(new AttackEntityHandler());

		ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModItems.STRAWBERRY, 0.5f);
		ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModItems.STRAWBERRY_SEEDS, 0.25f);

		CommandRegistrationCallback.EVENT.register(SetHomeCommand::register);
		CommandRegistrationCallback.EVENT.register(ReturnHomeCommand::register);
		ServerPlayerEvents.COPY_FROM.register(new PlayerCopyHandler());

		ModEffects.registerEffects();

		ModPotions.registerPotions();
		ModPotionRecipes.createPotionsRecipes();

		registerCustomTrades();
	}

	public static Identifier id(String path){
		return  Identifier.of(MOD_ID, path);
	}

	@SuppressWarnings("unused")
	public static Identifier mcId(String path){
		return  Identifier.of("minecraft", path);
	}

	private static void registerCustomTrades(){
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
	}
}