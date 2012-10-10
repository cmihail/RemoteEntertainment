CC = g++
CFLAGS = -Wall -g

COMMON_DEPS = bin/Main.o bin/Message.o bin/Logger.o bin/Client.o bin/Server.o bin/player.pb.o bin/PlayerCommand.o
PLATFORM_DEPS = bin/Process.o bin/ServerUnix.o bin/EventListener.o

.PHONY: build clean

.build: Main

Main: $(COMMON_DEPS) $(PLATFORM_DEPS)
	$(CC) $^ -o $@ -lprotobuf

bin/Main.o: src/Main.cpp
	$(CC) $^ $(CFLAGS) -c -o $@

bin/Message.o: src/Message.cpp
	$(CC) $^ $(CFLAGS) -c -o $@

bin/Logger.o: src/Logger.cpp
	$(CC) $^ $(CFLAGS) -c -o $@

bin/Client.o: src/server/Client.cpp
	$(CC) $^ $(CFLAGS) -c -o $@

bin/Server.o: src/server/Server.cpp
	$(CC) $^ $(CFLAGS) -c -o $@

bin/player.pb.o: src/proto/player.pb.cc
	$(CC) $^ $(CFLAGS) -c -o $@

bin/PlayerCommand.o: src/proto/PlayerCommand.cpp
	$(CC) $^ $(CFLAGS) -c -o $@

bin/Process.o: src/platform/unix/Process.cpp
	$(CC) $^ $(CFLAGS) -c -o $@

bin/ServerUnix.o: src/platform/unix/ServerUnix.cpp
	$(CC) $^ $(CFLAGS) -c -o $@

bin/EventListener.o: src/platform/unix/mac/EventListener.cpp
	$(CC) $^ $(CFLAGS) -c -o $@

clean:
	rm -rf bin/*.o bin/Main
