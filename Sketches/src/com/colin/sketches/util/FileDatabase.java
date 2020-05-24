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

package com.colin.sketches.util;

import com.colin.utils.EncodeUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A representation of a in-memory database.
 * @author Colin
 * @param <K> The key type of the database
 * @param <V> The value type of the database
 */
public class FileDatabase <K,V> {
    private final Path loadPath;
    private Map<K,V> values;
    private final Function<String,K> keyMap;
    private final Function<String,V> valueMap;
    private final String delimiter;
    private final boolean needsDecrypt;
    private boolean isCommitting = false;
    /**
     * Loads a file from the given path, and splits and transforms the elements read.
     * #s form comments, and will be ignored.
     * Duplicate entries will result in the previous data being lost.
     * @param toLoad The path to load the data from
     * @param delimiter The delimiter to use to split the lines
     * @param keyMapper The mapping function from the first split element to the desired type K
     * @param valueMapper The mapping function from the second split element to the desired type V
     * @param needsDecrypt Whether the file needs to be decrypted
     * @throws IOException If the file cannot be read
     */
    public FileDatabase(Path toLoad,String delimiter,Function<String,K> keyMapper,Function<String,V> valueMapper,boolean needsDecrypt) throws IOException{ loadPath = toLoad;
        values = mapLoader(toLoad,delimiter,keyMapper,valueMapper,needsDecrypt);
        keyMap = keyMapper;
        valueMap = valueMapper;
        this.delimiter = delimiter;
        this.needsDecrypt = needsDecrypt;
    }

     /**
     * Retrieves the value corresponding to the given key.
     * @param key The key with which t retrieve the value
     * @return The corresponding value
     */
    public V get(K key){
        return values.get(key);
    }

    /**
     * Filters by keys of the database, and returns a list of results consisting of the values.
     * @param toFilter The filtering function
     * @return The values whose key matches the filter
     */
    public List<V> matches(Predicate<K> toFilter){
        return values.entrySet().stream().filter(ent -> toFilter.test(ent.getKey())).map(Map.Entry::getValue).collect(Collectors.toList());
    }

    /**
     * Filters by keys of the database using the filtering function, then maps the corresponding values with the given mapping function.
     * @param <T> The type to transform to
     * @param filter The filtering function on the keys
     * @param mapper The mapping function on the values
     * @return The matching values
     */
    public <T> List<T> forEachMatching(Predicate<K> filter,Function<V,T> mapper){
        return matches(filter).stream().map(mapper).collect(Collectors.toList());
    }

    /**
     * Reloads the database.
     * @throws IOException If the file cannot be read
     */
    public void reload() throws IOException{
        values = mapLoader(loadPath,delimiter,keyMap,valueMap,needsDecrypt);
    }

    /**
     * Adds the record to the database.
     * @param key The key to add
     * @param value The value to associate with this key
     */
    public void addRecord(K key,V value){
        values.put(key,value);
    }

    /**
     * Removes the record with the given key from the database.
     * @param key The key of the database
     */
    public void deleteRecord(K key){
        values.remove(key);
    }
    
    /**
     * Commits the changes to the underlying file.
     * If another commit is in progress, does nothing.
     * Uses Object.toString() to represent the values.
     * @param encrypt If the file is to be encrypted
     * @throws IOException If the file cannot be opened
     */
    public void commit(boolean encrypt) throws IOException{
        commit(encrypt, Object::toString, Object::toString);
    }

    /**
     * Commits the changes to the underlying file with mappings by the given functions.
     * If another commit is in progress, does nothing.
     * @param encrypt If the file is to be encrypted
     * @param keyMap The function to map the key to String
     * @param valueMap The function to map the value to String
     * @throws IOException If the file cannot be opened
     */
    public void commit(boolean encrypt,Function<K,String> keyMap,Function<V,String> valueMap) throws IOException{
        commit(encrypt,keyMap,valueMap,loadPath.toFile());
    }
    public void commit(boolean encrypt, Function<K, String> keyMap, Function<V, String> valueMap, File write) throws IOException {
        if(isCommitting) {
            return;
        }
        if(!write.exists()){
            write.createNewFile();
        }
        isCommitting = true;
        try (BufferedWriter buff = new BufferedWriter(new FileWriter(write))) {
            for (Map.Entry<K, V> ent : values.entrySet()) {
                StringBuilder writeTo = new StringBuilder();
                writeTo.append(keyMap.apply(ent.getKey())).append(delimiter).append(valueMap.apply(ent.getValue()));
                if (encrypt) {
                    writeTo = new StringBuilder(EncodeUtils.encode(writeTo.toString()));
                }
                buff.write(writeTo.toString(), 0, writeTo.length());
                buff.newLine();
            }
        }
        isCommitting = false;
    }

    /**
     * Constructs a new FileDatabase{@literal <String,String>} with no mapping.
     *
     * @param toLoad The path to load the file from
     * @param delimiter The delimiter to parse each line with
     * @param needsDecrypt Whether the file needs decryption
     * @return A new instance of FileDatabase{@literal <String,String>}
     * @throws IOException If the file cannot be read
     */
    public static FileDatabase<String,String> getStringDatabase(Path toLoad,String delimiter,boolean needsDecrypt) throws IOException{
        return new FileDatabase<>(toLoad,delimiter,Function.identity(),Function.identity(),needsDecrypt);
    }

    /**
     * Tests if the database is empty.
     * @return True if the database is empty; false otherwise
     */
    public boolean isEmpty(){
        return values.isEmpty();
    }

    /**
     * Tests if a commit is in progress.
     * @return True if a commit is in progress; false otherwise
     */
    public boolean isCommitting(){
        return isCommitting;
    }

    /**
     * Gets the corresponding key for the value.
     * @param value The value that corresponds to the key
     * @return The key, if present
     */
    public K getKey(V value){
        return values.entrySet().stream().filter(ent -> ent.getValue().equals(value)).map(Map.Entry::getKey).findFirst().get();
    }
    private static <K,V> Map<K,V> mapLoader(Path toLoad,String delimiter,Function<String,K> keyMapper,Function<String,V> valueMapper,boolean needsDecrypt) throws IOException{
        List<String> lines = Files.readAllLines(toLoad);
        if(needsDecrypt){
            lines = lines.stream().map(EncodeUtils::decode).collect(Collectors.toList());
        }
        List<String[]> split = lines.stream().filter(st -> !st.startsWith("#")).map(str -> str.split(delimiter)).collect(Collectors.toList());
        Map<K,V> result;
        result = new HashMap<>();
        for(String[] s : split){
            result.put(keyMapper.apply(s[0]),valueMapper.apply(s[1]));
        }
        return result;
    }

    /**
     * Represents a query to the database.
     * Cannot be executed twice.
     * Construct another Query instead.
     * @param <K> The type of the key
     * @param <V> The value of the key
     */
    public static class Query<K,V> {
        private Stream<Map.Entry<K,V>> transformer;
        private boolean isDone = false;

        /**
         * Constructs a new Query.
         * @param toQuery The FileDatabase to use
         */
        public Query(FileDatabase<K,V> toQuery){
            transformer = toQuery.values.entrySet().stream();
        }

        /**
         * Filters the results of the query by the supplied predicate on the key.
         * @param predicate The predicate to filter by
         * @return This query instance
         */
        public Query<K,V> filterByKey(Predicate<K> predicate){
            transformer = transformer.filter(ent -> predicate.test(ent.getKey()));
            return this;
        }
       /**
         * Filters the results of the query by the supplied predicate on the value.
         * @param predicate The predicate to filter by
         * @return This query instance
         */
        public Query<K,V> filterByValue(Predicate<V> predicate){
            transformer = transformer.filter(ent -> predicate.test(ent.getValue()));
            return this;
        }

        /**
         * Limits the results of the query.
         * @param toLimit The amount of results to display
         * @return This query instance
         */
        public Query<K,V> limitResults(int toLimit){
            transformer = transformer.limit(toLimit);
            return this;
        }

        /**
         * Retrieves the maximum value of the database.
         * @param compare The comparator to use in comparing the values.
         * @return The result, if any
         * @throws NoSuchElementException If the database used is empty
         */
        public V getMaxValue(Comparator<V> compare) throws NoSuchElementException{
            if(isDone){
                throw new IllegalStateException("Terminal collect() used.");
            }
            isDone = true;
            return transformer.map(Map.Entry::getValue).max(compare).get();
        }

        /**
         * Collects the values of the database used.
         * @return The values of the database
         */
        public List<V> collectValues(){
            if(isDone){
                throw new IllegalStateException("Terminal collect() used.");
            }
            isDone = true;
            return transformer.map(Map.Entry::getValue).collect(Collectors.toList());
        }

        /**
         * Collects the keys of the database supplied.
         * @return The collected result
         */
        public List<K> collectKeys(){
             if(isDone){
                throw new IllegalStateException("Terminal collect() used.");
            }
            isDone = true;
            return transformer.map(Map.Entry::getKey).collect(Collectors.toList());
        }

        /**
         * Gets if the query is executed already.
         * @return True if the query is already executed; false otherwise
         */
        public boolean isDone(){
            return isDone;
        }
    }
}
