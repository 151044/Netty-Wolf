#include <stdbool.h>
#include <strings.h>
#include <stdio.h>
#include <stdlib.h>
//For compat purposes
#define SDL_MAIN_HANDLED
#include <SDL2/SDL.h>
#include <SDL2/SDL_mixer.h>
//Also for compat purposes
int main(){
    return 0;
}
Mix_Music* music = NULL;
char* musicPath = "";
bool initSDL(){
    SDL_SetMainReady();
    if(SDL_Init(SDL_INIT_AUDIO) < 0){
        printf("SDL audio backend cannot be initialized! Reason: %s", SDL_GetError());
        return false;
    }
    if(Mix_OpenAudio(44100,MIX_DEFAULT_FORMAT,2,2048) < 0){
        printf("SDL audio mixer cannot be initialized! Reason: %s", SDL_GetError());
        return false;
    }
    return true;
}
bool playMusic(const char* path){
    if(music != NULL || Mix_PlayingMusic() != 0){
        Mix_HaltMusic();
        Mix_FreeMusic(music);
    }
    music = Mix_LoadMUS(path);
    if(music == NULL){
        printf("Failed to load music! Reason: %s", Mix_GetError());
        return false;
    }
    if(sizeof(musicPath) <= strlen(path) * sizeof(char)){
        if(!realloc(musicPath,(strlen(path) + 2) * sizeof(char))){
            return false;
        }
    }
    strcpy(musicPath,path);
    return true;
}
bool playSound(const char* path, bool stopMus){
    int isPaused = Mix_PausedMusic();
    if(stopMus){
        if(isPaused != 1){
            Mix_PauseMusic();
        }
    }
    Mix_Chunk* load = Mix_LoadWAV(path);
    if(load == NULL){
        printf("Failed to load sound effect! Reason: %s", Mix_GetError());
        return false;
    }
    Mix_PlayChannel(-1,load,0);
    //TODO add wait for sound to finish
    Mix_FreeChunk(load);
    if(stopMus){
        if(isPaused != 1){
            Mix_ResumeMusic();
        }
    }
    return true;
}
char* getPlaying(){
    return musicPath;
}
//0 to 128
void setVolume(int i){
    Mix_Volume(-1,i);
}
void quitSDL(){
    Mix_Quit();
    SDL_Quit();
}
