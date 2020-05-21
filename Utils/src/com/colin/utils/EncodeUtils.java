/*
 *     Netty-Wolf
 *     Copyright (C) 2020  Colin Chow
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.colin.utils;

import java.util.*;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;
/**
 * The encoding system.
 * @author Colin
 */
public class EncodeUtils {
    private EncodeUtils(){
        throw new AssertionError();
    }

    /**
     * Encodes a string.
     * @param msg The string to encode
     * @return The encrypted string
     */
    public static String encode(String msg){
        Encoder en = Base64.getEncoder();
        List<Integer> toReverse = en.encodeToString(msg.getBytes()).chars().boxed().collect(Collectors.toList());
        Collections.reverse(toReverse);
        byte[] bytes;
        List<Byte> temp = toReverse.stream().map(Integer::byteValue).collect(Collectors.toList());
        bytes = new byte[temp.size()];
        for(int i = 0; i <= bytes.length -1; i++){
            bytes[i] = temp.get(i);
        }
        return en.encodeToString(bytes);
    }

    /**
     * Decodes the supplied string.
     * @param scrambled The scrambled string
     * @return The decoded, normal string
     */
    public static String decode(String scrambled){
        Decoder de = Base64.getDecoder();
        List<Integer> toReverse = new ArrayList<>();
        for(byte b : de.decode(scrambled)){
            toReverse.add((int) ((char) b));
        }
        Collections.reverse(toReverse);
        StringBuilder sb = new StringBuilder();
        for(char c : toReverse.stream().map(Character::toChars).flatMap(cArr -> {
            List<Character> chars = new ArrayList<>();
            for(char c : cArr){
                chars.add(c);
            }
            return chars.stream();
        }).collect(Collectors.toList()).toArray(new Character[0])){
            sb.append(c);
        }
        return new String(de.decode(sb.toString().getBytes()));
    }

    /**
     * Encodes the string with the specified seed.
     * @param msg The string to encode
     * @param seed The seed to encode with
     * @return The scrambled string
     */
    public static String getSeededEncode(String msg,long seed){
        Random rand = new Random(seed);
        List<Integer> done = new ArrayList<>();
        char[] dest = new char[msg.length()];
        for(char c : msg.toCharArray()){
            int random = rand.nextInt(msg.length());
            while(true){
                if(!done.contains(random)){
                    done.add(random);
                    dest[random] = c;
                    break;
                }else{
                    random = rand.nextInt(msg.length());
                }
            }
        }
        return encode(new String(dest));
    }

    /**
     * Decodes the string with the given seed.
     * @param msg The scrambled string
     * @param seed The scrambling seed
     * @return The decoded string
     */
    public static String getSeededDecode(String msg,long seed){
        Random rand = new Random(seed);
        msg = decode(msg);
        Queue<Integer> getUntil = new ArrayBlockingQueue<>(msg.length(),false,rand.ints(0,msg.length()).sequential().limit(msg.length() * 30).boxed().distinct().collect(Collectors.toList()));
        char[] toDecode = new char[msg.length()];
        char[] toGet = msg.toCharArray();
        for(int i = 0; i < msg.length(); i++){
            toDecode[i] = toGet[getUntil.remove()];
        }
        return new String(toDecode);
    }
    public static String getSeededLongEncode(String msg, long seed){
        Random rand = new Random(seed);
        List<Integer> done = new ArrayList<>();
        char[] dest = new char[msg.length()];
        StringBuilder acc = new StringBuilder();
        for(char c : msg.toCharArray()){
            int random = rand.nextInt(msg.length());
            while(true){
                if(!done.contains(random)){
                    done.add(random);
                    acc.append(random);
                    acc.append("-");
                    dest[random] = c;
                    break;
                }else{
                    random = rand.nextInt(msg.length());
                }
            }
        }
        String res = encode(new String(dest));
        return encode(res.concat("\\+++++\\").concat(encode(acc.substring(0,acc.length() - 1))));
    }
    public static String getSeededLongDecode(String msg){
        msg = decode(msg);
        int first = msg.indexOf("\\+++++\\");
        String toDecode = decode(msg.substring(0, first));
        String scramble = decode(msg.substring(first + 7));
        Queue<Integer> getUntil = new ArrayBlockingQueue<>(first + 8,false);
        StringBuilder buff = new StringBuilder(scramble);
        while(true){
            int find = buff.indexOf("-");
            if(find == -1){
                int parse = Integer.parseInt(buff.toString());
                getUntil.add(parse);
                break;
            }
            int parse = Integer.parseInt(buff.substring(0,find));
            getUntil.add(parse);
            buff = buff.delete(0, find + 1);
        }
        char[] out = new char[msg.length()];
        char[] toGet = toDecode.toCharArray();
        for(int i = 0; i < toDecode.length(); i++){
            out[i] = toGet[getUntil.remove()];
        }
        return new String(out).trim();
    }
}
