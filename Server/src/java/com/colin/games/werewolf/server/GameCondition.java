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

package com.colin.games.werewolf.server;

/**
 * A game condition is returned when a call to {@link GameState#applyOutstanding()} and its overloads are used.
 * It determines if the game continues.
 * The game checks if hasWon() returns true. If so, the game terminates.
 */
public interface GameCondition {
    /**
     * Tests if the game is finished.
     * @return True if the game is complete, false otherwise
     */
    boolean hasWon();

    /**
     * Gets the reason for the game ending.
     * @return The reason for completion of this game
     */
    String reason();

    /**
     * Resolves the given game condition with the current one to see which on should take priority.
     * @param game The other game condition to compare against
     * @return The correct game condition with respect t this one
     */
    default GameCondition resolve(GameCondition game){
        if(this.hasWon() && game.hasWon()){
            return this.priority() > game.priority() ? this : game;
        }else if(this.hasWon() && !game.hasWon()){
            return this;
        }else if(!this.hasWon() && game.hasWon()){
            return game;
        }else{
            return DefaultConditions.CONTINUE;
        }
    }

    /**
     * Describes what priority that the game conditions are in when two conflict.
     * @return The integer value, showing its relative priority
     */
    default int priority(){
        return 0;
    }
}
