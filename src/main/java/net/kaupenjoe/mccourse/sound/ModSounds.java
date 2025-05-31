package net.kaupenjoe.mccourse.sound;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.minecraft.block.jukebox.JukeboxSong;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;

public class ModSounds {
    public static final SoundEvent CHAINSAW_CUT = registerSoundEvent("chainsaw_cut");
    public static final SoundEvent CHAINSAW_PULL = registerSoundEvent("chainsaw_pull");

    public static final SoundEvent MAGIC_BLOCK_BREAK = registerSoundEvent("magic_block_break");
    public static final SoundEvent MAGIC_BLOCK_STEP = registerSoundEvent("magic_block_step");
    public static final SoundEvent MAGIC_BLOCK_PLACE = registerSoundEvent("magic_block_place");
    public static final SoundEvent MAGIC_BLOCK_HIT = registerSoundEvent("magic_block_hit");
    public static final SoundEvent MAGIC_BLOCK_FALL = registerSoundEvent("magic_block_fall");

    public static final BlockSoundGroup MAGIC_BLOCK_SOUNDS = new BlockSoundGroup(1f, 1f,
            MAGIC_BLOCK_BREAK, MAGIC_BLOCK_STEP, MAGIC_BLOCK_PLACE, MAGIC_BLOCK_HIT, MAGIC_BLOCK_FALL);

    public static final SoundEvent BAR_BRAWL = registerSoundEvent("bar_brawl");
    public static final RegistryKey<JukeboxSong> BAR_BRAWL_KEY = of("bar_brawl");

    private static RegistryKey<JukeboxSong> of(String name){
        return RegistryKey.of(RegistryKeys.JUKEBOX_SONG, MCCourseMod.id(name));
    }

    private static SoundEvent registerSoundEvent(String name){
        return Registry.register(Registries.SOUND_EVENT, MCCourseMod.id(name),
                SoundEvent.of(MCCourseMod.id(name)));
    }
    public static void registerSounds(){
        MCCourseMod.LOGGER.info("Registering mod sounds for " + MCCourseMod.MOD_ID);
    }
}
