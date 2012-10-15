CC = g++
CFLAGS = -Wall -g
HEADERS = src/

COMMON_DEPS = bin/Main.o bin/Message.o bin/Logger.o bin/Client.o bin/Server.o bin/player.pb.o bin/PlayerCommand.o

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
ifeq ($(PLATFORM), Windows)
bin/Process.o: src/platform/windows/Process.cpp
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@
bin/EventListener.o: src/platform/windows/EventListener.cpp
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@
bin/ServerWindows.o: src/platform/windows/ServerWindows.cpp
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@
bin/sock_util.o: src/platform/windows/sock_util.c
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@

else
bin/Process.o: src/platform/unix/Process.cpp
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@
bin/ServerUnix.o: src/platform/unix/ServerUnix.cpp
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@

ifeq ($(PLATFORM), Linux)
bin/EventListener.o: src/platform/unix/linux/EventListener.cpp
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@

else
bin/EventListener.o: src/platform/unix/mac/EventListener.cpp
	$(CC) -I$(HEADERS) $^ $(CFLAGS) -c -o $@

endif
endif

clean:
	rm -rf bin/*.o Main*
