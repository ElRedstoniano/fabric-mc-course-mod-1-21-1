package net.kaupenjoe.mccourse.world.gen;

public class ModWorldGeneration {
    public static void generateModWorldGeneration(){
        ModGeodeGeneration.generateGeodes();

        ModOreGeneration.generateOres(); // Tiene que ir antes que ModTreeGeneration

        ModTreeGeneration.generatesTrees();
        ModFlowerGeneration.generateFlowers();
    }
}
