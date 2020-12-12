#!/bin/bash
#
# Netty-Wolf
# Copyright (C) 2020  Colin Chow
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
#  the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <https://www.gnu.org/licenses/>.
#

curl https://www.libsdl.org/release/SDL2-devel-2.0.12-mingw.tar.gz > dev.tar.gz
tar -xzvf dev.tar.gz
curl https://www.libsdl.org/projects/SDL_mixer/release/SDL2_mixer-devel-2.0.4-mingw.tar.gz > mixer.tar.gz
tar -xzvf mixer.tar.gz
mv SDL2-2.0.12 SDL_temp
mkdir SDL2-2.0.12
cp -r SDL_temp/* SDL2-2.0.12
cp -r SDL2_mixer-2.0.4/* SDL2-2.0.12
rm dev.tar.gz mixer.tar.gz
rm -r SDL_temp/ SDL2_mixer-2.0.4/