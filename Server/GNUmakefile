CC = g++
CFLAGS = -Wall -g

.PHONY: build clean

.build: Main

Main: bin/Main.o bin/player.pb.o bin/Process.o bin/ServerMac.o bin/ServerUnixCommon.o
	$(CC) $^ -o $@ -lprotobuf

bin/Main.o: src/server/Main.cpp
	$(CC) $^ $(CFLAGS) -c -o $@

bin/player.pb.o: src/proto/player.pb.cc
	$(CC) $^ $(CFLAGS) -c -o $@

bin/Process.o: src/player/Process.cpp
	$(CC) $^ $(CFLAGS) -c -o $@

bin/ServerMac.o: src/server/ServerMac.cpp 
	$(CC) $^ $(CFLAGS) -c -o $@

bin/ServerUnixCommon.o: src/server/ServerUnixCommon.cpp
	$(CC) $^ $(CFLAGS) -c -o $@

clean:
	rm -rf bin/*.o bin/Main
