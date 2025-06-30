package net.kaupenjoe.mccourse.datagen;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.item.ModArmorMaterials;
import net.minecraft.client.render.entity.equipment.EquipmentModel;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.registry.RegistryKey;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

// Test from: https://github.com/Starexify/BigSwordsR/blob/1.21.4-fabric/src/client/java/net/nova/big_swords/data/models/BSEquipmentModelProvider.java#L22
// https://discord.com/channels/507304429255393322/1331282073654399070/1332048646685986867
// Preparing for 1.21.4
public class ModEquipmentModelProvider implements DataProvider {
    public final FabricDataOutput output;
    public final DataOutput.PathResolver pathResolver;

    public ModEquipmentModelProvider(FabricDataOutput output) {
        this.output = output;
        this.pathResolver = output.getResolver(DataOutput.OutputType.RESOURCE_PACK, "equipment");
    }

    public static void bootstrap(BiConsumer<RegistryKey<EquipmentAsset>, EquipmentModel> consumer) {
        consumer.accept(ModArmorMaterials.FLUORITE_KEY, createWarturtleOnlyModel("warturtle_body"));
    }

    public static EquipmentModel createWarturtleOnlyModel(String id) {
        EquipmentModel.LayerType layerType = ClassTinkerers.getEnum(EquipmentModel.LayerType.class, "WARTURTLE_BODY");
        return EquipmentModel.builder().addLayers(layerType,
                new EquipmentModel.Layer(MCCourseMod.id(id), Optional.empty(), false)).build();
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        Map<RegistryKey<EquipmentAsset>, EquipmentModel> map = new HashMap();
        bootstrap((key, model) -> {
            if (map.putIfAbsent(key, model) != null) {
                throw new IllegalStateException("Tried to register equipment asset twice for id: " + key);
            }
        });
        return DataProvider.writeAllToPath(writer, EquipmentModel.CODEC, this.pathResolver::resolveJson, map);
    }


    @Override
    public String getName() {
        return "TestMod equipment model generator";
    }
}
