OBJS = Audio.c

OBJ_NAME = sound_lib

WIN_DEP = ./SDL2-2.0.12/x86_64-w64-mingw32/include

WIN_LIB = ./SDL2-2.0.12/x86_64-w64-mingw32/lib

linux : $(OBJS)
	gcc -fPIC -g -c $(OBJS) -o $(OBJ_NAME).o -lSDL2main -lSDL2 -lSDL2_mixer -Wall -g
	gcc -shared -Wl,-soname,libaudio.so -o libaudio.so sound_lib.o -lc

win : $(OBJS)
	x86_64-w64-mingw32-gcc -g -c $(OBJS) -o $(OBJ_NAME).o -I $(WIN_DEP) -L $(WIN_LIB) -lmingw32 -lSDL2main -lSDL2 -lSDL2_mixer -mwindows -Wall -g -D Windows
	x86_64-w64-mingw32-gcc -shared -Wl,--out-implib,libaudio.a -o libaudio.dll sound_lib.o -I $(WIN_DEP) -L $(WIN_LIB) -lmingw32 -lSDL2main -lSDL2 -lSDL2_mixer -mwindows -Wall
