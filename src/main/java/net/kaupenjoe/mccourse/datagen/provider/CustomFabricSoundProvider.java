package net.kaupenjoe.mccourse.datagen.provider;

/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * Extend this class and implement {@link CustomFabricSoundProvider#generateSounds(SoundGenerator)}.
 *
 * <p>Register an instance of the class with {@link FabricDataGenerator.Pack#addProvider} in a {@link net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint}.
 */
public abstract class CustomFabricSoundProvider implements DataProvider {
    protected final FabricDataOutput dataOutput;

    protected CustomFabricSoundProvider(FabricDataOutput dataOutput) {
        this.dataOutput = dataOutput;
    }

    /**
     * Implement this method to register sounds.
     *
     * <p>Call {@link SoundGenerator#add(SoundEvent, SoundEntry...)} to add a list of sound entries
     * for a given {@link SoundEvent}.
     */
    public abstract void generateSounds(SoundGenerator soundGenerator);

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        HashMap<String, JsonObject> soundEvents = new HashMap<>();

        generateSounds(((sound, replace, subtitle, entries) -> {
            Objects.requireNonNull(sound);
            Objects.requireNonNull(entries);

            List<Identifier> keys = Arrays.stream(entries).map(SoundEntry::name).toList();

            if (!keys.stream().filter(i -> Collections.frequency(keys, i) > 1).toList().isEmpty()) {
                throw new RuntimeException("Entries for sound event " + sound.getId() + " contain duplicate sound names. Event will be omitted.");
            }

            JsonObject soundEventData = new JsonObject();
            JsonArray soundEntries = new JsonArray();

            Arrays.asList(entries).forEach(e -> soundEntries.add(e.toJson()));
            soundEventData.add("sounds", soundEntries);

            if (replace) {
                soundEventData.addProperty("replace", true);
            }

            if (subtitle != null) {
                soundEventData.addProperty("subtitle", subtitle);
            }

//            soundEvents.put(sound.getId().toString(), soundEventData);
            // Old og code, will cause that non-minecraft sounds won't work because of identifier pointing to
            // "MODID:custom_sound" insead of "minecraft:custom_sound" or just "custom_sound"

            soundEvents.put(sound.getId().getPath(), soundEventData); // This one will work correctly

        }));

        JsonObject soundsJson = new JsonObject();

        for (Map.Entry<String, JsonObject> entry : soundEvents.entrySet()) {
            soundsJson.add(entry.getKey(), entry.getValue());
        }

        Path soundsPath = dataOutput
                .getResolver(DataOutput.OutputType.RESOURCE_PACK, ".")
                .resolveJson(Identifier.of(dataOutput.getModId(), "sounds"));
        return DataProvider.writeToPath(writer, soundsJson, soundsPath.normalize());
    }

    @Override
    public String getName() {
        return "Sounds";
    }

    @ApiStatus.NonExtendable
    @FunctionalInterface
    public interface SoundGenerator {
        /**
         * Adds an individual {@link SoundEvent} and its respective sounds to your mod's <code>sounds.json</code> file.
         *
         * @param sound The {@link SoundEvent} to add an entry for.
         * @param replace Set this to <code>true</code> if this entry corresponds to a sound event from vanilla
         *                Minecraft or some other mod's namespace, in order to replace the default sounds from the
         *                original namespace's sounds file via your own namespace's resource pack.
         * @param subtitle An optional subtitle to use for the event, given as a translation key for the subtitle.
         * @param entries A list of {@link SoundEntry} instances from which to generate individual sound entry data for
         *                this event.
         */
        void add(SoundEvent sound, boolean replace, @Nullable String subtitle, SoundEntry... entries);

        /**
         * Adds an individual {@link SoundEvent} and its respective sounds to your mod's <code>sounds.json</code> file.
         *
         * @param sound The {@link SoundEvent} to add an entry for.
         * @param replace Set this to <code>true</code> if this entry corresponds to a sound event from vanilla
         *                Minecraft or some other mod's namespace, in order to replace the default sounds from the
         *                original namespace's sounds file via your own namespace's resource pack.
         * @param entries A list of {@link SoundEntry} instances from which to generate individual sound entry data for
         *                this event.
         */
        default void add(SoundEvent sound, boolean replace, SoundEntry... entries) {
            add(sound, replace, null, entries);
        }

        /**
         * Adds an individual {@link SoundEvent} and its respective sounds to your mod's <code>sounds.json</code> file.
         *
         * @param sound The {@link SoundEvent} to add an entry for.
         * @param subtitle An optional subtitle to use for the event, given as a translation key for the subtitle.
         * @param entries A list of {@link SoundEntry} instances from which to generate individual sound entry data for
         *                this event.
         */
        default void add(SoundEvent sound, @Nullable String subtitle, SoundEntry... entries) {
            add(sound, false, subtitle, entries);
        }

        /**
         * Adds an individual {@link SoundEvent} and its respective sounds to your mod's <code>sounds.json</code> file.
         *
         * @param sound The {@link SoundEvent} to add an entry for.
         * @param entries A list of {@link SoundEntry} instances from which to generate individual sound entry data for
         *                this event.
         */
        default void add(SoundEvent sound, SoundEntry... entries) {
            add(sound, false, null, entries);
        }
    }
}