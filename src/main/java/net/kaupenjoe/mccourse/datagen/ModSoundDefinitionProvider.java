package net.kaupenjoe.mccourse.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.datagen.provider.CustomFabricSoundProvider;
import net.kaupenjoe.mccourse.datagen.provider.SoundEntryBuilder;
import net.kaupenjoe.mccourse.sound.ModSounds;
import net.minecraft.sound.SoundEvent;

public class ModSoundDefinitionProvider extends CustomFabricSoundProvider {
    public ModSoundDefinitionProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateSounds(SoundGenerator soundGenerator) {
        generateCustomSoundWithSubtitles(soundGenerator, ModSounds.CHAINSAW_CUT, false);
        generateCustomSoundWithSubtitles(soundGenerator, ModSounds.CHAINSAW_PULL, false);

        generateCustomSoundWithSubtitles(soundGenerator, ModSounds.MAGIC_BLOCK_BREAK, false);
        generateCustomSoundWithSubtitles(soundGenerator, ModSounds.MAGIC_BLOCK_STEP, false);
        generateCustomSoundWithSubtitles(soundGenerator, ModSounds.MAGIC_BLOCK_PLACE, false);
        generateCustomSoundWithSubtitles(soundGenerator, ModSounds.MAGIC_BLOCK_HIT, false);
        generateCustomSoundWithSubtitles(soundGenerator, ModSounds.MAGIC_BLOCK_FALL, false);
        generateCustomMusicSound(soundGenerator, ModSounds.BAR_BRAWL);
    }

    // Took a look from https://github.com/FabricMC/fabric/pull/2759 and used the classes for custom sound datagen
    // https://discord.com/channels/507304429255393322/507982478276034570/1133787014240673822
    // https://github.com/FabricMC/fabric/pull/4560 ?
    // Nota: a partir de la api 0.121 de fabric ya se incluye el builder FabricSoundsProvider, por lo que a partir de esa versión
    // ya no se necesitaría nada de esto: https://discord.com/channels/507304429255393322/507304986921664533/1363490378648522952

    private void generateCustomSoundWithSubtitles(SoundGenerator soundGenerator, SoundEvent sound, boolean replace) {
        soundGenerator.add(sound, replace, "sounds." + sound.getId().toShortTranslationKey(),
                SoundEntryBuilder.sound( MCCourseMod.id(sound.getId().getPath()))
                        .build());

    }


    private void generateCustomMusicSound(SoundGenerator soundGenerator, SoundEvent sound) {
        soundGenerator.add(sound,
                SoundEntryBuilder.sound( MCCourseMod.id(sound.getId().getPath()))
                        .stream()
                        .build());
    }
}
