CC = g++
CFLAGS = -Wall -g
HEADERS = src/

COMMON_DEPS = bin/Main.o bin/Message.o bin/Logger.o bin/Client.o bin/Server.o bin/player.pb.o\
bin/PlayerCommand.o bin/PlayerMessageHeader.o

# Default platform is Mac. On Linux run as "make PLATFORM=Linux" and on Windows as "make PLATFORM=Windows"
PLATFORM = Mac

ifeq ($(PLATFORM), Windows)
LIBS = -lprotobuf -lws2_32
PLATFORM_DEPS = bin/Process.o bin/ServerWindows.o bin/EventListener.o bin/sock_util.o
else
LIBS = -lprotobuf
PLATFORM_DEPS = bin/Process.o bin/ServerUnix.o bin/EventListener.o
endif

.PHONY: build clean

.build: Main

Main: $(COMMON_DEPS) $(PLATFORM_DEPS)
	$(CC) $^ -o $@ $(LIBS)

# root package
bin/Main.o: src/Main.cpp
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@
bin/Message.o: src/Message.cpp
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@

# event package
ifeq ($(PLATFORM), Windows)
bin/EventListener.o: src/event/EventListenerWindows.cpp
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@
else
ifeq ($(PLATFORM), Linux)
bin/EventListener.o: src/event/EventListenerLinux.cpp
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@
else # default MAC
bin/EventListener.o: src/event/EventListenerMac.cpp
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@
endif
endif

# logger package
bin/Logger.o: src/logger/Logger.cpp
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@

# process package
ifeq ($(PLATFORM), Windows)
bin/Process.o: src/process/ProcessWindows.cpp
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@
else # default unix
bin/Process.o: src/process/ProcessUnix.cpp
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@
endif

# proto pacakge
bin/player.pb.o: src/proto/player.pb.cc
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@
bin/PlayerCommand.o: src/proto/PlayerCommand.cpp
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@
bin/PlayerMessageHeader.o: src/proto/PlayerMessageHeader.cpp
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@

# server package
bin/Client.o: src/server/Client.cpp
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@
bin/Server.o: src/server/Server.cpp
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@

ifeq ($(PLATFORM), Windows)
bin/ServerWindows.o: src/server/ServerWindows.cpp
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@
bin/sock_util.o: src/server/windows/sock_util.c
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@
else # default unix
bin/ServerUnix.o: src/server/ServerUnix.cpp
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@
endif

clean:
	rm -rf bin/*.o Main*
