package net.kaupenjoe.mccourse.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.block.custom.FluoriteLampBlock;
import net.kaupenjoe.mccourse.item.ModItems;
import net.minecraft.data.client.*;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.Map;

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
}
