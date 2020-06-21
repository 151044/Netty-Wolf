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

package com.colin.games.werewolf.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Gets the game presets.
 * A preset is a predefined list of roles.
 */
public class Presets {
    private Presets(){
        throw new AssertionError();
    }
    private static final Map<Integer, List<String>> presets = new HashMap<>();
    private static final Logger log = ServerMain.appendLog(LogManager.getFormatterLogger("Presets"));
    static{
        try {
            log.info("Loading presets...");
            final URI uri = Presets.class.getResource("/resources").toURI();
            Map<String, String> env = new HashMap<>();
            env.put("create", "true");
            FileSystem zip = FileSystems.newFileSystem(uri, env);
            setPresets(Path.of(ClassLoader.getSystemResource("resources/DefaultLoad.txt").toURI()));
        } catch (IOException | URISyntaxException e) {
            log.error("Preset file failed to be loaded!");
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the list of presets for the given number of players.
     * @param lookup The number of players to lookup
     * @return The list of presets
     */
    public static List<String> presetFor(int lookup){
        return presets.get(lookup);
    }

    /**
     * Adds presets from a file.
     * @param file The path of the preset file
     */
    public static void addPresets(Path file){
        setPresets(file);
    }
    private static void setPresets(Path path){
        try {
            presets.putAll(Files.readAllLines(path).stream().map(str -> str.split("\\|"))
                    .collect(Collectors.toMap(sArr -> Integer.parseInt(sArr[0]),sArr -> Arrays.asList(sArr[1].split(":")))));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
