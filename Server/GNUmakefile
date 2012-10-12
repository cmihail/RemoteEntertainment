CC = g++
CFLAGS = -Wall -g
HEADERS = src/

PLATFORM_PATH = unix/mac

COMMON_DEPS = bin/Main.o bin/Message.o bin/Logger.o bin/Client.o bin/Server.o bin/player.pb.o bin/PlayerCommand.o
PLATFORM_DEPS = bin/Process.o bin/ServerUnix.o bin/EventListener.o

.PHONY: build clean

.build: Main

Main: $(COMMON_DEPS) $(PLATFORM_DEPS)
	$(CC) $^ -o $@ -lprotobuf

# root package
bin/Main.o: src/Main.cpp
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@

bin/Message.o: src/Message.cpp
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@

bin/Logger.o: src/Logger.cpp
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@

# server package
bin/Client.o: src/server/Client.cpp
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@

bin/Server.o: src/server/Server.cpp
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@

# proto pacakge
bin/player.pb.o: src/proto/player.pb.cc
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@

bin/PlayerCommand.o: src/proto/PlayerCommand.cpp
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@

# platform pacakge
bin/Process.o: src/platform/unix/Process.cpp
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@

bin/ServerUnix.o: src/platform/unix/ServerUnix.cpp
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@

bin/EventListener.o: src/platform/$(PLATFORM_PATH)/EventListener.cpp
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@

clean:
	rm -rf bin/*.o bin/Main
