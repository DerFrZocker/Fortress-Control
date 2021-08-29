/*
 * MIT License
 *
 * Copyright (c) 2020 Marvin (DerFrZocker)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.derfrzocker.fc;

import net.minecraft.server.level.ChunkMap;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Map;

public class WorldHandler_v1_17_R1 implements Listener {

    @NotNull
    private final FortressControl fortressControl;

    public WorldHandler_v1_17_R1(@NotNull FortressControl fortressControl) {
        Validate.notNull(fortressControl, "FortressControl cannot be null");

        this.fortressControl = fortressControl;

        Bukkit.getPluginManager().registerEvents(this, fortressControl);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onWorldLoad(WorldInitEvent event) {
        // checking if the Bukkit world is an instance of CraftWorld, if not return
        if (!(event.getWorld() instanceof CraftWorld)) {
            return;
        }

        final CraftWorld world = (CraftWorld) event.getWorld();

        try {

            // get the playerChunkMap where the ChunkGenerator is store, that we need to override
            ChunkMap chunkMap = world.getHandle().getChunkProvider().chunkMap;

            // get the ChunkGenerator from the PlayerChunkMap
            Field ChunkGeneratorField = ChunkMap.class.getDeclaredField("r");
            ChunkGeneratorField.setAccessible(true);
            Object chunkGeneratorObject = ChunkGeneratorField.get(chunkMap);

            // return, if the chunkGeneratorObject is not an instance of ChunkGenerator
            if (!(chunkGeneratorObject instanceof ChunkGenerator)) {
                return;
            }

            ChunkGenerator chunkGenerator = (ChunkGenerator) chunkGeneratorObject;
            FortressConfiguration configuration = fortressControl.getFortressConfiguration();
            Map<StructureFeature<?>, StructureFeatureConfiguration> structureSettings = chunkGenerator.getSettings().structureConfig();

            if(structureSettings.containsKey(StructureFeature.NETHER_BRIDGE)) {
                structureSettings.put(StructureFeature.NETHER_BRIDGE, new StructureFeatureConfiguration(configuration.getSpacing(), configuration.getSeparation(), configuration.getSalt()));
            }

        } catch (final Exception e) {
            throw new RuntimeException("Unexpected error while hook into world " + world.getName(), e);
        }
    }

}
