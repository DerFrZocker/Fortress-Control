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

import de.derfrzocker.spigot.utils.Config;
import org.bstats.bukkit.Metrics;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class FortressControl extends JavaPlugin {

    @NotNull
    private FortressConfiguration fortressConfiguration = new FortressConfiguration(FortressConfiguration.DEFAULT_SPACING, FortressConfiguration.DEFAULT_SEPARATION, FortressConfiguration.DEFAULT_SALT);

    @Override
    public void onEnable() {
        saveDefaultConfig();

        // we don't use #getConfig(), since it loads the default config of the jar as default values
        Config config = new Config(new File(getDataFolder(), "config.yml"));

        fortressConfiguration = readConfiguration(config);

        new WorldHandler_v1_16_R2(this);

       new Metrics(this, 8735);
    }

    /**
     * @return the FortressConfiguration
     */
    @NotNull
    public FortressConfiguration getFortressConfiguration() {
        return fortressConfiguration;
    }

    @NotNull
    private FortressConfiguration readConfiguration(ConfigurationSection section) {
        int spacing = getNumber(section, "spacing", FortressConfiguration.DEFAULT_SPACING);
        int separation = getNumber(section, "separation", FortressConfiguration.DEFAULT_SEPARATION);
        int salt = getNumber(section, "salt", FortressConfiguration.DEFAULT_SALT);

        return new FortressConfiguration(spacing, separation, salt);
    }

    private int getNumber(ConfigurationSection section, String fieldName, int defaultValue) {
        String valueString = section.getString(fieldName);
        int value = defaultValue;

        if (valueString == null) {
            getLogger().warning("The Configuration '" + buildPath(section) + "' does not contain an value for the field '" + fieldName + "', using default value '" + defaultValue + "'");
            return value;
        }

        if (!valueString.equals("default")) {
            try {
                value = Integer.parseInt(valueString);
            } catch (NumberFormatException e) {
                getLogger().warning("The value '" + valueString + "' for the field '" + fieldName + "' in the Configuration '" + buildPath(section) + "' is not a valid Integer, using default value '" + defaultValue + "'");
            }
        }

        return value;
    }

    private String buildPath(ConfigurationSection configurationSection) {
        ConfigurationSection parent = configurationSection.getParent();
        StringBuilder builder = new StringBuilder(configurationSection.getName());

        while (parent != null) {
            builder.insert(0, '.');
            builder.insert(0, parent.getName());

            parent = parent.getParent();
        }

        if (builder.charAt(0) == '.') {
            builder.substring(1);
        }

        return builder.toString();
    }

}
