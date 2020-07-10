/*
 * Netty-Wolf
 * Copyright (C) 2020  Colin Chow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.colin.games.werewolf.common.utils.gui;

import com.colin.games.werewolf.common.utils.Config;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

/**
 * A GUI for manually editing settings.
 */
public class ConfigFrame extends JFrame {
    private final Config conf;

    /**
     * Creates a new ConfigFrame with the specified path to load the config from.
     * @param config The path of the config file
     * @throws IOException If the file cannot be read
     */
    public ConfigFrame(Path config) throws IOException {
        this(config,"Configs");
    }

    /**
     * Creates a new ConfigFrame with the specified {@link com.colin.games.werewolf.common.utils.Config config} instance.
     * @param conf The config instance
     */
    public ConfigFrame(Config conf){
        this(conf,"Configs");
    }

    /**
     * Creates a new ConfigFrame with the specified path to load the config from and the given title.
     * @param load The path of the config file
     * @param title The title of the config frame
     * @throws IOException If the file cannot be read
     */
    public ConfigFrame(Path load,String title) throws IOException {
        this(Config.read(load),title);
    }

    /**
     * Creates a new ConfigFrame with the specified {@link com.colin.games.werewolf.common.utils.Config config} instance and the given title.
     * @param conf The config instance
     * @param title The title of the config frame
     */
    public ConfigFrame(Config conf,String title){
        super(title);
        this.conf = conf;
        for(Map.Entry<String,String> ent : conf.getAsMap().entrySet()){
            
        }
    }
}
