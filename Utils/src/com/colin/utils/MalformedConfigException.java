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

/**
 * Thrown to indicate a config file is corrupt or malformed.
 * @author Colin
 */
public class MalformedConfigException extends RuntimeException {

    /**
     * Creates a new instance of <code>MalformedConfigException</code> without detail message.
     */
    public MalformedConfigException() {
    }

    /**
     * Constructs an instance of <code>MalformedConfigException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public MalformedConfigException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of <code>MalformedConfigException</code> without detail message and the specified exception as cause.
     * @param exc The cause of this exception
     */
    public MalformedConfigException(Exception exc){
        super(exc);
    }
}
