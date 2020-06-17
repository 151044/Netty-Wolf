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

import com.colin.games.werewolf.common.Environment;
import com.colin.games.werewolf.common.utils.ReflectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A loader for mods.
 */
public class ModLoader {
    private ModLoader(){
        throw new AssertionError();
    }
    private static List<Mod> mods = new ArrayList<>();
    private static final Logger log = LogManager.getFormatterLogger("Mod Loader");

    /**
     * Loads classes implementing the Mod interface from the specified path.
     * This method does not reload mods for each invocation. The cache of the last results will be used if possible.
     * @param path The path to load mods from
     * @param throwOnInvalid Whether to throw a {@link ModdedException} if an error occurs.
     * @return A list of loaded mods, or an empty list if none exist at the target directory
     * @throws IOException If an I/O error occurs
     * @throws ClassNotFoundException Lint
     */
    public static List<Mod> loadMods(Path path, boolean throwOnInvalid) throws IOException, ClassNotFoundException {
        if(mods.size() != 0){
            return mods;
        }
        mods = ReflectUtils.loadConcreteSubclasses(path,Mod.class).stream().map(cls -> {
            try {
                return cls.getConstructor().newInstance();
            } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        List<Mod> rem = new ArrayList<>();
        for(Mod m : mods){
            Environment.Side s = Environment.getSide();
            if(s.equals(Environment.Side.CLIENT)){
                if(!m.isClient()){
                    if(throwOnInvalid){
                        throw new ModdedException(m,"The mod is not client-side!");
                    }else{
                        log.warn("Mod " + m.name() + " is not client-side! Removing from mod list!");
                        rem.add(m);
                    }
                }else{
                    log.info("Mod " + m.name() + " is successfully loaded!");
                }
            }else{
                if(!m.isServer()){
                    if(throwOnInvalid){
                        throw new ModdedException(m,"The mod is not server-side!");
                    }else{
                        log.warn("Mod " + m.name() + " is not server-side! Removing from mod list!");
                        rem.add(m);
                    }
                }else{
                    log.info("Mod " + m.name() + " is successfully loaded!");
                }
            }
        }
        mods.removeAll(rem);
        return mods;
    }

    /**
     * Reloads mods by invalidating the cache and reloading.
     * @param path The path to load mods from
     * @param throwOnInvalid Whether to throw a {@link ModdedException} if an error occurs.
     * @return A list of loaded mods, or an empty list if none exist at the target directory
     * @throws IOException If an I/O error occurs
     * @throws ClassNotFoundException Lint
     */
    public static List<Mod> reloadMods(Path path,boolean throwOnInvalid) throws IOException, ClassNotFoundException {
        mods.clear();
        return loadMods(path,throwOnInvalid);
    }

    /**
     * Gets the number of loaded mods.
     * @return The number of mods loaded
     */
    public static int loadedMods(){
        return mods.size();
    }

    /**
     * Tests if a mod with the given name exists.
     * @param name The name of the mod to find
     * @return True if the mod exists and is loaded, false otherwise
     */
    public static boolean hasMod(String name){
        return mods.stream().filter(m -> m.name().equals(name)).findFirst().isPresent();
    }

    /**
     * Gets a list of loaded mods.
     * @return The cached list of mods
     */
    public static List<Mod> getLoaded(){
        return new ArrayList<>(mods);
    }
}
