mkdir make_win
mv libaudio.dll Audio.h make_win/
mv ./SDL2-2.0.12/x86_64-w64-mingw32/bin/* make_win/
zip -q -r win_dev.zip make_win/
rm -r make_win
