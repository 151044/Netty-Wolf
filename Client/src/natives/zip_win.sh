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

mkdir make_win
cp libaudio.dll Audio.h make_win/
cp ./SDL2-2.0.12/x86_64-w64-mingw32/bin/* make_win/
zip -q -r win_dev.zip make_win/
rm -r make_win
