CC = g++
CFLAGS = -Wall -g

.PHONY: build clean

.build: Main

Main: bin/Main.o bin/player.pb.o bin/PlayerCommand.o bin/Process.o bin/ServerMac.o bin/ServerUnixCommon.o
	$(CC) $^ -o $@ -lprotobuf

bin/Main.o: src/common/Main.cpp
	$(CC) $^ $(CFLAGS) -c -o $@

bin/player.pb.o: src/common/proto/player.pb.cc
	$(CC) $^ $(CFLAGS) -c -o $@

bin/PlayerCommand.o: src/common/proto/PlayerCommand.cpp
	$(CC) $^ $(CFLAGS) -c -o $@

bin/Process.o: src/unix/Process.cpp
	$(CC) $^ $(CFLAGS) -c -o $@

bin/ServerMac.o: src/unix/mac/ServerMac.cpp 
	$(CC) $^ $(CFLAGS) -c -o $@

bin/ServerUnixCommon.o: src/unix/ServerUnixCommon.cpp
	$(CC) $^ $(CFLAGS) -c -o $@

clean:
	rm -rf bin/*.o bin/Main
