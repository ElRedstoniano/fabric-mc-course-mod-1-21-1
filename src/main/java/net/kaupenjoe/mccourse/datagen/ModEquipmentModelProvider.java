package net.kaupenjoe.mccourse.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.data.client.EquipmentModelProvider;
import net.minecraft.item.equipment.EquipmentModel;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

// Test from: https://github.com/Starexify/BigSwordsR/blob/1.21.4-fabric/src/client/java/net/nova/big_swords/data/models/BSEquipmentModelProvider.java#L22
// https://discord.com/channels/507304429255393322/1331282073654399070/1332048646685986867
// Preparing for 1.21.4
public class ModEquipmentModelProvider {/* implements DataProvider {
    public final FabricDataOutput output;
    public final DataOutput.PathResolver pathResolver;

    public ModEquipmentModelProvider(FabricDataOutput output) {
        this.output = output;
        this.pathResolver = output.getResolver(DataOutput.OutputType.RESOURCE_PACK, "equipment");
    }

    public static void bootstrap(BiConsumer<RegistryKey<EquipmentAsset>, EquipmentModel> consumer) {
        consumer.accept(test1, createHumanoidOnlyModel("livingmetal"));
        consumer.accept(test2.BIOMASS, createHumanoidOnlyModel("biomass"));
    }

    public static EquipmentModel createHumanoidOnlyModel(String id) {
        return EquipmentModel.builder().addHumanoidLayers(Identifier
    ).build();
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
    }*/
}
