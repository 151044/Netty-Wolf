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

curl -L https://github.com/151044/Netty-Wolf/releases/download/latest-dev-build/linux_dev.tar.gz > dev.tar.gz
tar -xzvf dev.tar.gz
mv make_linux/Audio.h make_linux/libaudio.a make_linux/libaudio.so ./
rm -r make_linux
rm dev.tar.gz