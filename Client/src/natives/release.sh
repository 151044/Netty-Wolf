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

mkdir linux
mkdir win
cp libaudio.a libaudio.so ../../../build/libs/Client-0.1.0.jar ../../resources/resources/geg.mp3 ./linux/
cp libaudio.dll ../../../build/libs/Client-0.1.0.jar ../../resources/resources/geg.mp3 ./SDL2-2.0.12/x86_64-w64-mingw32/bin/* win/
zip -q -r win_release.zip win/
rm -r win
tar -cvzf linux_release.tar.gz linux/
rm -r linux