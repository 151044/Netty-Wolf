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

package com.colin.games.werewolf.client.role;

import com.colin.games.werewolf.client.role.groups.DefaultGroups;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class HunterTest {
    static Hunter hunter = new Hunter();
    @Test
    void name() {
        assertEquals("Hunter",hunter.name());
        assertNotEquals("KCA",hunter.name());
    }

    @Test
    void callbackName() {
        assertEquals("hunter_next", hunter.callbackName());
        assertNotEquals("Eric",hunter.callbackName());
    }

    @Test
    void getGroup() {
        assertEquals(DefaultGroups.VILLAGER,hunter.getGroup());
        assertNotEquals(DefaultGroups.WEREWOLF,hunter.getGroup());
    }

    @Test
    void testToString() {
        assertEquals(hunter.name(),hunter.toString());
    }
}