CC = g++
CFLAGS = -Wall -g
HEADERS = src/

COMMON_DEPS = bin/Main.o bin/Message.o bin/Logger.o bin/Client.o bin/Server.o bin/player.pb.o bin/PlayerCommand.o
PLATFORM_DEPS = bin/Process.o bin/ServerWindows.o bin/EventListener.o bin/sock_util.o

.PHONY: build clean

.build: Main

Main: $(COMMON_DEPS) $(PLATFORM_DEPS)
	$(CC) $^ -o $@ -lprotobuf -lws2_32

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
bin/Process.o: src/platform/windows/Process.cpp
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@

bin/ServerWindows.o: src/platform/windows/ServerWindows.cpp
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@

bin/EventListener.o: src/platform/windows/EventListener.cpp
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@

bin/sock_util.o: src/platform/windows/sock_util.c
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@

clean:
	rm -rf bin/*.o Main.exe
