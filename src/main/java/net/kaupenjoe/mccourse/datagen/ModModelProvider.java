package net.kaupenjoe.mccourse.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.block.custom.FluoriteLampBlock;
import net.kaupenjoe.mccourse.block.custom.StrawberryCropBlock;
import net.kaupenjoe.mccourse.fluid.ModFluids;
import net.kaupenjoe.mccourse.item.ModItems;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.data.client.*;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.util.Map;
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
        Identifier lamp_off_identifier = TexturedModel.CUBE_ALL.upload(ModBlocks.FLUORITE_LAMP, blockStateModelGenerator.modelCollector);
        Identifier lamp_on_identifier = blockStateModelGenerator.createSubModel(ModBlocks.FLUORITE_LAMP, "_on", Models.CUBE_ALL, TextureMap::all);
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(ModBlocks.FLUORITE_LAMP)
                .coordinate(BlockStateModelGenerator.createBooleanModelMap(FluoriteLampBlock.CLICKED, lamp_on_identifier, lamp_off_identifier)));

        blockStateModelGenerator.registerCrop(ModBlocks.STRAWBERRY_CROP, StrawberryCropBlock.AGE, 0, 1, 2, 3, 4, 5);
        blockStateModelGenerator.registerFlowerPotPlant(ModBlocks.DAHLIA, ModBlocks.POTTED_DAHLIA, BlockStateModelGenerator.TintType.NOT_TINTED);

        blockStateModelGenerator.registerSingleton(ModBlocks.COLORED_LEAVES, TexturedModel.LEAVES);

        blockStateModelGenerator.registerNorthDefaultHorizontalRotated(ModBlocks.CRYSTALLIZER, TexturedModel.ORIENTABLE);
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

        itemModelGenerator.registerArmor((ArmorItem) ModItems.FLUORITE_HELMET);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.FLUORITE_CHESTPLATE);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.FLUORITE_LEGGINGS);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.FLUORITE_BOOTS);

        itemModelGenerator.register(ModItems.FLUORITE_HORSE_ARMOR, Models.GENERATED);
        itemModelGenerator.register(ModItems.KAUPEN_SMITHING_TEMPLATE, Models.GENERATED);

        itemModelGenerator.register(ModItems.METAL_DETECTOR, Models.GENERATED);

        //itemModelGenerator.register(ModItems.DATA_TABLET, Models.GENERATED);
        registerDataTablet(itemModelGenerator,ModItems.DATA_TABLET);

        registerBow(itemModelGenerator, ModItems.KAUPEN_BOW);

        itemModelGenerator.register(ModItems.BAR_BRAWL_MUSIC_DISC, Models.GENERATED);
        //itemModelGenerator.register(ModItems.STRAWBERRY_SEEDS, Models.GENERATED);
        itemModelGenerator.register(ModFluids.FLUORITE_WATER_BUCKET, Models.GENERATED);
    }

    // Took a look from ItemModelGenerator.registerArmor() method
    @SuppressWarnings("SameParameterValue")
    private void registerDataTablet(ItemModelGenerator itemModelGenerator, Item dataTablet) {
        Identifier identifier = Identifier.of(MCCourseMod.MOD_ID, "item/" + getItemIdAsString(dataTablet));
        Identifier identifier2 = Identifier.of(MCCourseMod.MOD_ID, "item/" +
                getItemIdAsString(dataTablet) + "_off");
        Models.GENERATED.upload(identifier, TextureMap.layer0(identifier2), itemModelGenerator.writer,
                (id, textures) -> createDataTablet(id, textures, dataTablet));

        Identifier identifier3 = Identifier.of(MCCourseMod.MOD_ID, "item/" +
                getItemIdAsString(dataTablet) + "_on");
        Models.GENERATED.upload(identifier3, TextureMap.layer0(identifier), itemModelGenerator.writer);
    }

    private String getItemIdAsString(Item item) {
        return Registries.ITEM.getId(item).getPath();
    }

    private JsonObject createDataTablet(Identifier id, Map<TextureKey, Identifier> textures, Item item){
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
    }

    @SuppressWarnings("SameParameterValue")
    private void registerBow(ItemModelGenerator itemModelGenerator, Item item) {
        if (item instanceof BowItem) {
            //String bowItemPath = item.toString(); //<- No - devuelve ModId:itemname
            String bowItemPath = getItemIdAsString(item); // <- Si - itemname

            System.out.println("DEBUGS: " + bowItemPath);
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
    }

    private JsonObject createBow(Identifier id, Map<TextureKey, Identifier> textures, Item item){
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
    }

    @SafeVarargs
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
    }
}
