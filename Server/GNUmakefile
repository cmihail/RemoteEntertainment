CC = g++
CFLAGS = -Wall -g

.PHONY: build clean

.build: Main

Main: bin/Main.o bin/Client.o bin/player.pb.o bin/PlayerCommand.o bin/Process.o bin/EventListener.o bin/ServerUnix.o bin/ServerUnixCommon.o
	$(CC) $^ -o $@ -lprotobuf

bin/Main.o: src/common/Main.cpp
	$(CC) $^ $(CFLAGS) -c -o $@

bin/Client.o: src/common/Client.cpp
	$(CC) $^ $(CFLAGS) -c -o $@

bin/player.pb.o: src/common/proto/player.pb.cc
	$(CC) $^ $(CFLAGS) -c -o $@

bin/PlayerCommand.o: src/common/proto/PlayerCommand.cpp
	$(CC) $^ $(CFLAGS) -c -o $@

bin/Process.o: src/unix/Process.cpp
	$(CC) $^ $(CFLAGS) -c -o $@

#bin/ServerMac.o: src/unix/mac/ServerMac.cpp 
#	$(CC) $^ $(CFLAGS) -c -o $@

bin/EventListener.o: src/unix/mac/EventListener.cpp
	$(CC) $^ $(CFLAGS) -c -o $@

bin/ServerUnix.o: src/unix/ServerUnix.cpp
	$(CC) $^ $(CFLAGS) -c -o $@

bin/ServerUnixCommon.o: src/unix/ServerUnixCommon.cpp
	$(CC) $^ $(CFLAGS) -c -o $@

clean:
	rm -rf bin/*.o bin/Main
