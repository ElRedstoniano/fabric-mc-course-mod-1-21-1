package net.kaupenjoe.mccourse.fabricasm;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public class EarlyRiser implements Runnable{

    @Override
    public void run() {
        MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();

        // https://lambdaurora.dev/tutorials/java/bytecode/types.html
        // Examples: https://github.com/Chocohead/Fabric-ASM/blob/master/example/src/com/chocohead/mm/testing/EarlyRiser.java

        String layerType = remapper.mapClassName("intermediary", "net.minecraft.class_10186.class_10190"); // EquipmentModel.LayerType
        ClassTinkerers.enumBuilder(layerType, 'L' + "java/lang/String;")
                .addEnum("WARTURTLE_BODY", "warturtle").build();
    }
}
