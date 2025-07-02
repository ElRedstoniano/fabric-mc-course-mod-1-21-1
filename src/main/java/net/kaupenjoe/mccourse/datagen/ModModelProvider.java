package net.kaupenjoe.mccourse.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.block.custom.FluoriteLampBlock;
import net.kaupenjoe.mccourse.block.custom.StrawberryCropBlock;
import net.kaupenjoe.mccourse.components.ModDataComponentTypes;
import net.kaupenjoe.mccourse.fluid.ModFluids;
import net.kaupenjoe.mccourse.item.ModArmorMaterials;
import net.kaupenjoe.mccourse.item.ModItems;
import net.minecraft.client.data.*;
import net.minecraft.client.item.ItemAsset;
import net.minecraft.client.render.item.model.ConditionItemModel;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.property.bool.HasComponentProperty;
import net.minecraft.client.render.model.json.ModelVariant;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.item.Item;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.Pool;

import java.util.List;
import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        BlockStateModelGenerator.BlockTexturePool fluoriteTexturePool =
                blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.FLUORITE_BLOCK);
        //blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.FLUORITE_BLOCK);

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.FLUORITE_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RAW_FLUORITE_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.FLUORITE_DEEPSLATE_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.FLUORITE_NETHER_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.FLUORITE_END_ORE);

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.MAGIC_BLOCK);

        fluoriteTexturePool.stairs(ModBlocks.FLUORITE_STAIRS);
        fluoriteTexturePool.slab(ModBlocks.FLUORITE_SLAB);

        fluoriteTexturePool.button(ModBlocks.FLUORITE_BUTTON);
        fluoriteTexturePool.pressurePlate(ModBlocks.FLUORITE_PRESSURE_PLATE);
        fluoriteTexturePool.fence(ModBlocks.FLUORITE_FENCE);
        fluoriteTexturePool.fenceGate(ModBlocks.FLUORITE_FENCE_GATE);
        fluoriteTexturePool.wall(ModBlocks.FLUORITE_WALL);

        blockStateModelGenerator.registerDoor(ModBlocks.FLUORITE_DOOR);
        blockStateModelGenerator.registerTrapdoor(ModBlocks.FLUORITE_TRAPDOOR);

        // Lámpara

        // 1.21.4
        //Identifier lamp_off_identifier = TexturedModel.CUBE_ALL.upload(ModBlocks.FLUORITE_LAMP, blockStateModelGenerator.modelCollector);
        //Identifier lamp_on_identifier = blockStateModelGenerator.createSubModel(ModBlocks.FLUORITE_LAMP, "_on", Models.CUBE_ALL, TextureMap::all);
        //blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(ModBlocks.FLUORITE_LAMP)
                //.coordinate(BlockStateModelGenerator.createBooleanModelMap(FluoriteLampBlock.CLICKED, lamp_on_identifier, lamp_off_identifier)));

        // 1.21.5
        Identifier lampOffIdentifier = TexturedModel.CUBE_ALL.upload(ModBlocks.FLUORITE_LAMP, blockStateModelGenerator.modelCollector);
        Identifier lampOnIdentifier = blockStateModelGenerator.createSubModel(ModBlocks.FLUORITE_LAMP, "_on", Models.CUBE_ALL, TextureMap::all);
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(ModBlocks.FLUORITE_LAMP)
                .with(BlockStateModelGenerator.createBooleanModelMap(FluoriteLampBlock.CLICKED,
                        new WeightedVariant(Pool.<ModelVariant>builder().add(new ModelVariant(lampOnIdentifier)).build()),
                        new WeightedVariant(Pool.<ModelVariant>builder().add(new ModelVariant(lampOffIdentifier)).build())
                )));

        // Another way to do this // From BlockstateModelGenerator registerRedstoneLamp()
        /*WeightedVariant weightedVariant = BlockStateModelGenerator.createWeightedVariant(
                TexturedModel.CUBE_ALL.upload(ModBlocks.FLUORITE_LAMP, blockStateModelGenerator.modelCollector));
        WeightedVariant weightedVariant2 = BlockStateModelGenerator.createWeightedVariant(
                blockStateModelGenerator.createSubModel(ModBlocks.FLUORITE_LAMP, "_on", Models.CUBE_ALL, TextureMap::all));

        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(ModBlocks.FLUORITE_LAMP)
                        .with(BlockStateModelGenerator.createBooleanModelMap(FluoriteLampBlock.CLICKED, weightedVariant, weightedVariant2)));*/


        blockStateModelGenerator.registerCrop(ModBlocks.STRAWBERRY_CROP, StrawberryCropBlock.AGE, 0, 1, 2, 3, 4, 5);
        //blockStateModelGenerator.registerFlowerPotPlant(ModBlocks.DAHLIA, ModBlocks.POTTED_DAHLIA, BlockStateModelGenerator.CrossType.NOT_TINTED);
        blockStateModelGenerator.registerFlowerPotPlantAndItem(ModBlocks.DAHLIA, ModBlocks.POTTED_DAHLIA,
                BlockStateModelGenerator.CrossType.NOT_TINTED);

       // blockStateModelGenerator.registerSingleton(ModBlocks.COLORED_LEAVES, TexturedModel.LEAVES);
        blockStateModelGenerator.registerTintedBlockAndItem(ModBlocks.COLORED_LEAVES, TexturedModel.LEAVES, -12012264);
        // New leaves in 1.21.2-4+ ^ BlockStateModelGenerator.class

        //blockStateModelGenerator.registerNorthDefaultHorizontalRotated(ModBlocks.CRYSTALLIZER, TexturedModel.ORIENTABLE);
        blockStateModelGenerator.registerCooker(ModBlocks.CRYSTALLIZER, TexturedModel.ORIENTABLE);

        // registerLog -> createLogTexturePool
        blockStateModelGenerator.createLogTexturePool(ModBlocks.BLACKWOOD_LOG).log(ModBlocks.BLACKWOOD_LOG).wood(ModBlocks.BLACKWOOD_WOOD);
        blockStateModelGenerator.createLogTexturePool(ModBlocks.STRIPPED_BLACKWOOD_LOG).log(ModBlocks.STRIPPED_BLACKWOOD_LOG).wood(ModBlocks.STRIPPED_BLACKWOOD_WOOD);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.BLACKWOOD_PLANKS);
        blockStateModelGenerator.registerSingleton(ModBlocks.BLACKWOOD_LEAVES, TexturedModel.LEAVES); // Old leaves in 1.21.1
        //blockStateModelGenerator.registerTintedBlockAndItem(ModBlocks.BLACKWOOD_LEAVES, TexturedModel.LEAVES, -12012264);
        // New leaves in 1.21.2-4+ ^ BlockStateModelGenerator.class

        blockStateModelGenerator.registerTintableCrossBlockState(ModBlocks.BLACKWOOD_SAPLING,  BlockStateModelGenerator.CrossType.NOT_TINTED);

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.COAL_GENERATOR);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.TANK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.FLUORITE, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_FLUORITE, Models.GENERATED);

        itemModelGenerator.register(ModItems.CHAINSAW, Models.GENERATED);
        itemModelGenerator.register(ModItems.STRAWBERRY, Models.GENERATED);
        itemModelGenerator.register(ModItems.STARLIGHT_ASHES, Models.GENERATED);

        itemModelGenerator.register(ModItems.FLUORITE_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.FLUORITE_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.FLUORITE_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.FLUORITE_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.FLUORITE_HOE, Models.HANDHELD);

        itemModelGenerator.register(ModItems.FLUORITE_PAXEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.FLUORITE_HAMMER, Models.HANDHELD);

        /*itemModelGenerator.register(ModItems.FLUORITE_HELMET, Models.GENERATED);
        itemModelGenerator.register(ModItems.FLUORITE_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.FLUORITE_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(ModItems.FLUORITE_BOOTS, Models.GENERATED);*/

        itemModelGenerator.registerArmor(ModItems.FLUORITE_HELMET,
                ModArmorMaterials.FLUORITE_KEY, ItemModelGenerator.HELMET_TRIM_ID_PREFIX/*ModArmorMaterials.FLUORITE_KEY*/, false);
        itemModelGenerator.registerArmor(ModItems.FLUORITE_CHESTPLATE,
                ModArmorMaterials.FLUORITE_KEY, ItemModelGenerator.CHESTPLATE_TRIM_ID_PREFIX, false);
        itemModelGenerator.registerArmor(ModItems.FLUORITE_LEGGINGS,
                ModArmorMaterials.FLUORITE_KEY, ItemModelGenerator.LEGGINGS_TRIM_ID_PREFIX, false);
        itemModelGenerator.registerArmor(ModItems.FLUORITE_BOOTS,
                ModArmorMaterials.FLUORITE_KEY, ItemModelGenerator.BOOTS_TRIM_ID_PREFIX, false);

        itemModelGenerator.register(ModItems.FLUORITE_HORSE_ARMOR, Models.GENERATED);
        itemModelGenerator.register(ModItems.KAUPEN_SMITHING_TEMPLATE, Models.GENERATED);

        itemModelGenerator.register(ModItems.METAL_DETECTOR, Models.GENERATED);

        //itemModelGenerator.register(ModItems.DATA_TABLET, Models.GENERATED);
        registerDataTablet(itemModelGenerator, ModItems.DATA_TABLET);

        // 1.12.3<
       // registerBow(itemModelGenerator, ModItems.KAUPEN_BOW);
        itemModelGenerator.upload(ModItems.KAUPEN_BOW, Models.GENERATED); // 1.12.4>+ // Normal bow texture
        itemModelGenerator.registerBow(ModItems.KAUPEN_BOW); // 1.12.4>+ // only pulling textures

        itemModelGenerator.register(ModItems.BAR_BRAWL_MUSIC_DISC, Models.GENERATED);
        //itemModelGenerator.register(ModItems.STRAWBERRY_SEEDS, Models.GENERATED);
        itemModelGenerator.register(ModFluids.FLUORITE_WATER_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModBlocks.BLACKWOOD_SAPLING.asItem(), Models.GENERATED);

        /*itemModelGenerator.register(ModItems.DODO_SPAWN_EGG, // 1.21.4
                new Model(Optional.of(Identifier.of("item/template_spawn_egg")), Optional.empty()));
        itemModelGenerator.register(ModItems.GIRAFFE_SPAWN_EGG ,
                new Model(Optional.of(Identifier.of("item/template_spawn_egg")), Optional.empty()));
        itemModelGenerator.register(ModItems.WARTURTLE_SPAWN_EGG ,
                new Model(Optional.of(Identifier.of("item/template_spawn_egg")), Optional.empty()));*/

        itemModelGenerator.register(ModItems.DODO_SPAWN_EGG, Models.GENERATED);
        itemModelGenerator.register(ModItems.GIRAFFE_SPAWN_EGG, Models.GENERATED);
        itemModelGenerator.register(ModItems.WARTURTLE_SPAWN_EGG, Models.GENERATED);

        //itemModelGenerator.register(ModBlocks.DAHLIA.asItem(), Models.GENERATED); ^ Done in Blockstates generation

        itemModelGenerator.register(ModItems.IRON_WARTURTLE_ARMOR, Models.GENERATED);
        itemModelGenerator.register(ModItems.GOLD_WARTURTLE_ARMOR, Models.GENERATED);
        itemModelGenerator.register(ModItems.DIAMOND_WARTURTLE_ARMOR, Models.GENERATED);
        itemModelGenerator.register(ModItems.NETHERITE_WARTURTLE_ARMOR, Models.GENERATED);
        itemModelGenerator.register(ModItems.FLUORITE_WARTURTLE_ARMOR, Models.GENERATED);

        //itemModelGenerator.register(ModItems.TOMAHAWK, Models.GENERATED);
        //itemModelGenerator.register(ModItems.SPECTRE_STAFF, Models.GENERATED);
        //itemModelGenerator.register(ModBlocks.PEDESTAL.asItem(), Models.GENERATED);
    }

    public final void registerDataTablet(ItemModelGenerator itemModelGenerator, Item item) {
        // Mirar clase ModelProvider / ItemModelGenerator para ejemplos
        //ItemModel.Unbaked unbaked = ItemModels.basic(itemModelGenerator.upload(item, Models.GENERATED));
        //ItemModel.Unbaked unbaked_off = ItemModels.basic(itemModelGenerator.registerSubModel(item, "_off",Models.GENERATED));
        /*itemModelGenerator.output.accept(item, new ItemAsset(
                new ConditionItemModel.Unbaked(new HasComponentProperty(ModDataComponentTypes.COORDINATES, false),
                unbaked, unbaked_off), new ItemAsset.Properties(false)
        ).model());*/ // Another way to do this (This doesn't work for some reason)
  //      itemModelGenerator.registerCondition(item, new HasComponentProperty(ModDataComponentTypes.COORDINATES, false),
   //             unbaked_off, unbaked); // (This doesn't work for some reason)
        /*itemModelGenerator.output.accept(ItemModels.select(new HasComponentProperty(ModDataComponentTypes.COORDINATES, false),
                unbaked_off, ItemModels.switchCase(true, unbaked)));*/

        ItemModel.Unbaked unbaked = ItemModels.basic(itemModelGenerator.upload(item, Models.GENERATED));
        ItemModel.Unbaked unbakedOff = ItemModels.basic(itemModelGenerator.registerSubModel(item, "_off", Models.GENERATED));
        itemModelGenerator.output.accept(item,
                new ItemAsset(new ConditionItemModel.Unbaked(new HasComponentProperty(ModDataComponentTypes.FOUND_BLOCK, false),
                        unbaked, unbakedOff),
                        new ItemAsset.Properties(false)).model());
    }

    // Outdated in 1.12.4
    // Took a look from ItemModelGenerator.registerArmor() method
    /*@SuppressWarnings("SameParameterValue")
    private void registerDataTablet(ItemModelGenerator itemModelGenerator, Item dataTablet) {
        Identifier identifier = Identifier.of(MCCourseMod.MOD_ID, "item/" + getItemIdAsString(dataTablet));
        Identifier identifier2 = Identifier.of(MCCourseMod.MOD_ID, "item/" +
                getItemIdAsString(dataTablet) + "_off");
        Models.GENERATED.upload(identifier, TextureMap.layer0(identifier2), itemModelGenerator.writer,
                (id, textures) -> createDataTablet(id, textures, dataTablet));

        Identifier identifier3 = Identifier.of(MCCourseMod.MOD_ID, "item/" +
                getItemIdAsString(dataTablet) + "_on");
        Models.GENERATED.upload(identifier3, TextureMap.layer0(identifier), itemModelGenerator.writer);
    }*/

    private String getItemIdAsString(Item item) {
        return Registries.ITEM.getId(item).getPath();
    }

    /*private JsonObject createDataTablet(Identifier id, Map<TextureKey, Identifier> textures, Item item){
        JsonObject jsonObject = Models.GENERATED.createJson(id, textures);
        JsonArray overridesJsonArray = new JsonArray();

        JsonObject jsonObject2 = new JsonObject();
        JsonObject jsonObject3 = new JsonObject();
        jsonObject3.addProperty( MCCourseMod.MOD_ID + ":on", 1);
        jsonObject2.add("predicate", jsonObject3);
        jsonObject2.addProperty("model", MCCourseMod.MOD_ID + ":item/" +
                getItemIdAsString(item) + "_on");
        overridesJsonArray.add(jsonObject2);

        jsonObject.add("overrides", overridesJsonArray);
        return jsonObject;
    }*/

    // Outdated
    //@SuppressWarnings("SameParameterValue")
    /*private void registerBow(ItemModelGenerator itemModelGenerator, Item item) {
        if (item instanceof BowItem) {
            //String bowItemPath = item.toString(); //<- No - devuelve ModId:itemname
            String bowItemPath = getItemIdAsString(item); // <- Si - itemname

            //System.out.println("DEBUGS: " + bowItemPath);
            Identifier bowItemIdentifier = MCCourseMod.id("item/" + bowItemPath);

            Models.GENERATED.upload(bowItemIdentifier, TextureMap.layer0(bowItemIdentifier), itemModelGenerator.writer,
                    (id, textures) -> createBow(id, textures, item));

            Model model = new Model(Optional.of(MCCourseMod.id("item/" + bowItemPath)), Optional.empty(),
                    TextureKey.LAYER0);

            // Pulling bow 0-1-2 models
            for(int i = 0 ; i < 3 ; i++){
                Identifier actualPullingBowIdentifier = MCCourseMod.id("item/" + bowItemPath + "_pulling_" + i);
                model.upload(actualPullingBowIdentifier, TextureMap.layer0(actualPullingBowIdentifier), itemModelGenerator.writer);
            }
        }
    }*/


    /*private JsonObject createBow(Identifier id, Map<TextureKey, Identifier> textures, Item item){
        JsonObject jsonObject = Models.GENERATED.createJson(id, textures);
        JsonObject displayNode = new JsonObject();

        String predicatePath = MCCourseMod.MOD_ID + ":" + "item/" + getItemIdAsString(item) + "_pulling_";
        JsonArray overridesJsonArray = new JsonArray();

        overridesJsonArray.add(addPredicateWithModel(predicatePath + 0, Map.of("pulling", 1f)));
        overridesJsonArray.add(addPredicateWithModel(predicatePath + 1, Map.of("pulling", 1f), Map.of("pull", 0.65f)));
        overridesJsonArray.add(addPredicateWithModel(predicatePath + 2, Map.of("pulling", 1f), Map.of("pull", 0.9f)));

        Vec3d rot1 = new Vec3d(0, 90, -25);
        Vec3d scale1 = new Vec3d(0.68, 0.68, 0.68);
        Vec3d trans1 = new Vec3d(1.13, 3.2, 1.13);

        Vec3d rot2 = new Vec3d( 0, -90, 25);
        Vec3d trans2 = new Vec3d(1.13, 3.2, 1.13);

        Vec3d rot3 = new Vec3d( -80, -280, 40);
        Vec3d scale3 = new Vec3d(0.9, 0.9, 0.9);
        Vec3d trans3 = new Vec3d(-1, -2, 2.5);

        Vec3d rot4 = new Vec3d(-80, 260, -40);
        Vec3d trans4 = new Vec3d(-1, -2, 2.5);

        String fplh = ModelTransformationMode.FIRST_PERSON_LEFT_HAND.asString();
        String fprh = ModelTransformationMode.FIRST_PERSON_RIGHT_HAND.asString();
        String tplh = ModelTransformationMode.THIRD_PERSON_LEFT_HAND.asString();
        String tprh = ModelTransformationMode.THIRD_PERSON_RIGHT_HAND.asString();

        displayNode.add(fplh, addTransformationMode(
                Map.of("rotation", rot1), Map.of("scale", scale1), Map.of("translation", trans1)));
        displayNode.add(fprh, addTransformationMode(
                Map.of("rotation", rot2), Map.of("scale", scale1), Map.of("translation", trans2)));
        displayNode.add(tplh, addTransformationMode(
                Map.of("rotation", rot3), Map.of("scale", scale3), Map.of("translation", trans3)));
        displayNode.add(tprh, addTransformationMode(
                Map.of("rotation", rot4), Map.of("scale", scale3), Map.of("translation", trans4)));

        jsonObject.add("overrides", overridesJsonArray);
        jsonObject.add("display", displayNode);

        return jsonObject;
    }*/

    /*@SafeVarargs
    private JsonObject addPredicate(Map<String, Float>... predicateValues){
        JsonObject predicateContent = new JsonObject();
        JsonObject predicateNode = new JsonObject();

        for ( Map<String, Float> mapEntries : predicateValues){
            for(Map.Entry<String, Float>  entry : mapEntries.entrySet()){
                predicateContent.addProperty(entry.getKey(), entry.getValue());
            }
        }
        predicateNode.add("predicate", predicateContent);

        return predicateNode;
    }

    @SafeVarargs
    private JsonObject addPredicateWithModel(String modelName, Map<String, Float>... predicateValues){
        JsonObject predicateNode =  addPredicate(predicateValues);
        predicateNode.addProperty("model", modelName);

        return predicateNode;
    }

    @SafeVarargs
    private JsonObject addTransformationMode( Map<String, Vec3d>... predicateValues){
        JsonObject transformChildContent = new JsonObject();

        for ( Map<String, Vec3d> mapEntries : predicateValues){
            for(Map.Entry<String, Vec3d>  entry : mapEntries.entrySet()){
                transformChildContent.add(entry.getKey(), addJsonArray( entry.getValue()));
            }
        }
        return transformChildContent;
    }

    private JsonArray addJsonArray(Vec3d vec3d){
        JsonArray arrayElement = new JsonArray();
        arrayElement.add(vec3d.x);
        arrayElement.add(vec3d.y);
        arrayElement.add(vec3d.z);
        return arrayElement;
    }*/
}
