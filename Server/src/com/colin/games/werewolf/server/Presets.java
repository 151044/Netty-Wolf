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

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Presets {
    private Presets(){
        throw new AssertionError();
    }
    private static final Map<Integer, List<String>> presets;
    static{
        try {
            presets = Files.readAllLines(Paths.get("./assets/DefaultLoad.txt")).stream().map(str -> str.split("\\|"))
                    .collect(Collectors.toMap(sArr -> Integer.parseInt(sArr[0]),sArr -> Arrays.asList(sArr[1].split(":"))));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    public static List<String> presetFor(int lookup){
        return presets.get(lookup);
    }
}
