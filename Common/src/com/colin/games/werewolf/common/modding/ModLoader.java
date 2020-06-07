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

package com.colin.games.werewolf.common.modding;

import com.colin.games.werewolf.common.utils.ReflectUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class ModLoader {
    private ModLoader(){
        throw new AssertionError();
    }
    public static List<Mod> loadMods() throws IOException, ClassNotFoundException {
        List<Mod> mods = ReflectUtils.loadConcreteSubclasses(Path.of("./mods"),Mod.class).stream().map(cls -> {
            try {
                return cls.getConstructor().newInstance();
            } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }).filter(mod -> mod != null).collect(Collectors.toList());
        return mods;
    }
}
