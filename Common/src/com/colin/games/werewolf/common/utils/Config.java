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

package com.colin.games.werewolf.common.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * A representation of a config file.
 * @author Colin
 */
public class Config {
    private final Path p;
    private final Map<String,String> map = new HashMap<>();
    private final Map<String,String> comment = new HashMap<>();
    private Config(Path p,String comment) throws IOException{
        this.p = p;
        try(BufferedReader buff = new BufferedReader(new FileReader(p.toFile()))){
            String s;
            while((s = buff.readLine()) != null){
                if(!s.startsWith(comment)){
                    String[] split = s.split("=");
                    if(split.length != 2){
                        throw new MalformedConfigException("Config file malformed.\nLine : " + s);
                    }
                    map.put(split[0].trim(), split[1].trim());
                }
            }
        }
    }

    /**
     * Creates a new Config instance.
     * @param writeTo The path to write to
     */
    public Config(Path writeTo){
        p = writeTo;
    }

    /**
     * Creates a new Config instance.
     * @param s The String to be converted to a Path
     */
    public Config(String s){
        p = Paths.get(s);
    }

    /**
     * Stores a config value, according to the given key.
     * Overwrites the past value associated with the key.
     * @param key The key value
     * @param value The value to store
     */
    public void store(String key,String value){
        map.put(key,value);
    }

    /**
     * Writes this config file to the path given.
     * @param toWrite The path to write this config file to
     * @throws IOException If the file cannot be opened or read
     */
    public void write(Path toWrite) throws IOException{
        if(!toWrite.toFile().exists()){
            Files.createDirectories(toWrite.getParent());
            Files.createFile(toWrite);
        }
        try(BufferedWriter buff = new BufferedWriter(new FileWriter(toWrite.toFile()))){
            for(Map.Entry<String,String> ent : map.entrySet()){
                String s;
                if((s = comment.get(ent.getKey())) != null){
                    buff.write("# " + s);
                    buff.newLine();
                }
                buff.write(ent.getKey() + " = " + ent.getValue());
                buff.newLine();
            }
        }
    }

    /**
     * Adds a comment before the specified config.
     * @param comment The comment String
     * @param beforeProp The property before which the comment shall be written
     */
    public void addComment(String comment,String beforeProp){
        this.comment.put(beforeProp, comment);
    }

    /**
     * Writes the config file.
     * @throws IOException If the file cannot be written
     */
    public void write() throws IOException{
        write(p);
    }

    /**
     * Reads the config file and returns a config instance.
     * @param p The path to read from
     * @param commentSym The symbol for comments, default "#"
     * @return The read config file
     * @throws FileNotFoundException If the config file is not found
     * @throws IOException If an I/O error occurs
     */
    public static Config read(Path p,String commentSym) throws FileNotFoundException, IOException{
        return new Config(p,commentSym);
    }

    /**
     * Converts the String related to this key to T with the given function.
     * @param <T> The type to change to
     * @param key The key to get the property from
     * @param mapper The function which can be applied to transform String to T
     * @return The transformed type
     */
    public <T> T fromString(String key,Function<String,T> mapper){
        String before = map.get(key);
        if(before == null){
            throw new MalformedConfigException("Value of " + key + " does not exist.");
        }
        return mapper.apply(map.get(key));
    }

    /**
     * Retrieves and returns the value associated with this key.
     * @param s The key to retrieve with
     * @return The value associated with this key
     */
    public String get(String s){
        return map.get(s);
    }
        /**
     * Reads the config file,parses with the comment identifier "#",and returns a config instance.
     * @param p The path to read from
     * @return The read config file
     * @throws FileNotFoundException If the config file is not found
     * @throws IOException If an I/O error occurs
     */
    public static Config read(Path p) throws FileNotFoundException, IOException{
        return new Config(p,"#");
    }

    /**
     * Returns every entry in the config file.
     * @return The underlying map's copy
     */
    public Map<String,String> getAsMap(){
        return new HashMap<>(map);
    }

    /**
     * Tests if the config file has this key.
     * @param s The key to test for
     * @return True if the key exists, false otherwise
     */
    public boolean hasKey(String s){
        return map.containsKey(s);
    }

    /**
     * Creates the key in this config file if it does not exist, then returns the value associated with the key.
     * @param s The key to search for
     * @param fallback The fallback value if the key does not exist
     * @return The value associated with the key
     */
    public String get(String s,String fallback) {
        String str = map.get(s);
        if(str == null){
            map.put(s,fallback);
            try {
                write();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
            return fallback;
        }else{
            return str;
        }
    }
    /**
     * Converts the String related to this key to T with the given function, creating the key if it does not exist.
     * @param <T> The type to change to
     * @param key The key to get the property from
     * @param mapper The function which can be applied to transform String to T
     * @param fallback The fallback value if the key does not exist
     * @return The transformed type
     */
    public <T> T fromString(String key,Function<String,T> mapper,String fallback){
        String before = map.get(key);
        if(before == null){
            map.put(key,fallback);
            try {
                write();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        return mapper.apply(map.get(key));
    }
}
