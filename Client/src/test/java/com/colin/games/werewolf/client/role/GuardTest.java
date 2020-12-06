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

import com.colin.games.werewolf.common.Player;
import org.junit.jupiter.api.Test;

import java.client.role.groups.DefaultGroups;

import static org.junit.jupiter.api.Assertions.*;

class GuardTest {
    static Guard guard = new Guard();
    @org.junit.jupiter.api.Test
    void name() {
        assertEquals("Guard",guard.name());
        assertNotEquals("MFK",guard.name());
    }

    @org.junit.jupiter.api.Test
    void callbackName() {
        assertEquals("guard_next", guard.callbackName());
        assertNotEquals("Eric",guard.callbackName());
    }

    @org.junit.jupiter.api.Test
    void lastSaved() {
        guard.setSaved(new Player("name",new Guard()));
        assertEquals(new Player("name", new Guard()),guard.lastSaved());
        assertNotEquals(new Player("Bob",new Guard()),guard.lastSaved());
    }
    @Test
    void lastSavedThrows() {
        assertThrows(NullPointerException.class,() -> guard.setSaved(null));
    }
    @Test
    void getGroup(){
        assertEquals(DefaultGroups.VILLAGER,guard.getGroup());
        assertNotEquals(DefaultGroups.WEREWOLF,guard.getGroup());
    }
}