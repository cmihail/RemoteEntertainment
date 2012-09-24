CC = g++
CPPFLAGS = -Wall -g
VPATH = player/ server/

.PHONY: build clean

.build: bin/Server

bin/Server: bin/Server.o bin/MediaPlayer.o bin/Process.o

bin/Server.o: src/server/Server.cpp
	$(CC) $^ $(CPPFLAGS) -c -o $@

bin/MediaPlayer.o: src/player/MediaPlayer.cpp
	$(CC) $^ $(CPPFLAGS) -c -o $@

bin/Process.o: src/player/Process.cpp
	$(CC) $^ $(CPPFLAGS) -c -o $@

clean:
	rm -rf bin/*.o bin/Server
