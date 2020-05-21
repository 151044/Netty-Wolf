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
 * A collection of mathematical utilities.
 * @author Colin
 */
public class MathUtils {
    private MathUtils(){
        throw new AssertionError();
    }

    /**
     * Returns the value of the factorial of n. If n {@literal <}= 0, then 1 is returned.
     * @param n The number to factorial
     * @return The result of n!
     */
    public static long fact(int n){
        if(n <= 0){
            return 1;
        }
        long result = 1;
        for(int i = 1; i <= n; i++){
            result *= i;
        }
        return result;
    }

    /**
     * Returns the combination of the given values.
     * @param n The n value in nCr
     * @param r The r value in nCr
     * @return The value of the expression nCr
     */
    public static long comb(int n,int r){
        if(n < r){
            throw new IllegalArgumentException("n < r in nCr");
        }
        return fact(n)/(fact(r)*fact(n-r));
    }
}
