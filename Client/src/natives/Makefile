OBJS = Audio.c

OBJ_NAME = sound_lib

WIN_DEP = ./SDL2-2.0.12/x86_64-w64-mingw32/include

WIN_LIB = ./SDL2-2.0.12/x86_64-w64-mingw32/lib

all : $(OBJS)
	gcc $(OBJS) -w -o $(OBJ_NAME) -lSDL2main -lSDL2 -lSDL2_mixer

win : $(OBJS)
	x86_64-w64-mingw32-gcc $(OBJS) -w -o $(OBJ_NAME) -I $(WIN_DEP) -L $(WIN_LIB) -lmingw32 -lSDL2main -lSDL2 -lSDL2_mixer -mwindows

