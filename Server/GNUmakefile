CC = g++
CFLAGS = -Wall -g

.PHONY: build clean

.build: bin/Main

bin/Main: bin/Main.o bin/Process.o bin/ServerMac.o bin/ServerUnixCommon.o

bin/Main.o: src/server/Main.cpp
	$(CC) $^ $(CFLAGS) -c -o $@

bin/Process.o: src/player/Process.cpp
	$(CC) $^ $(CFLAGS) -c -o $@

bin/ServerMac.o: src/server/ServerMac.cpp 
	$(CC) $^ $(CFLAGS) -c -o $@

bin/ServerUnixCommon.o: src/server/ServerUnixCommon.cpp
	$(CC) $^ $(CFLAGS) -c -o $@

clean:
	rm -rf bin/*.o bin/Server
