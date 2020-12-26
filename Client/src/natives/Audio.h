#include <stdbool.h>
bool initSDL();
bool playMusic(const char* path);
bool playSound(const char* path, bool stopMus);
char* getPlaying();
void setVolume(int i);
void quitSDL();
int getMusicVolume();
void setMusicVolume(int i);
void setSoundVolume(int i);
bool isPlayingMusic();