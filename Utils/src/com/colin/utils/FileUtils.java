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

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Some utilities involving reading files.
 * @author Colin
 */
public class FileUtils {
    private FileUtils(){
        throw new AssertionError();
    }

    /**
     * Reads a file line by line, then parses the file with the given delimiter.
     * @param toRead The file to read
     * @param delimiter The delimiter to parse the file with
     * @return A list of values, mapped per line
     * @throws IOException If an I/O error occurs
     */
    public static List<String[]> readParse(Path toRead,String delimiter) throws IOException{
        return readParse(toRead,StandardCharsets.UTF_8,delimiter);
    }

    /**
     * Reads a file with the supplied charset line by line, then parses the file with the given delimiter.
     * @param toRead The path to read from
     * @param cs The charset to use
     * @param delimiter The delimiter to use
     * @return The parsed results
     * @throws IOException If the file is not readable
     */
    public static List<String[]> readParse(Path toRead,Charset cs,String delimiter) throws IOException{
        return Files.readAllLines(toRead, cs).stream().map(s -> s.split(delimiter)).collect(Collectors.toList());
    }

    /**
     * Reads a file with the supplied charset line by line, then parses the file with the given delimiter and transforms the input with the supplied function.
     * @param <T> The parsed type
     * @param toRead The path to read from
     * @param cs The charset to use
     * @param delimiter The delimiter to use
     * @param transformer The action to apply
     * @return The parsed results
     * @throws IOException When the file cannot be read
     */
    public static <T> List<T> readParse(Path toRead,Charset cs, String delimiter,Function<String[],T> transformer) throws IOException{
        return readParse(toRead,cs,delimiter).stream().map(transformer).collect(Collectors.toList());
    }
}
