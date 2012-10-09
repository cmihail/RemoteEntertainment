CC = g++
CFLAGS = -Wall -g

COMMON_DEPS = bin/Main.o bin/Client.o bin/Message.o bin/Server.o bin/player.pb.o bin/PlayerCommand.o
PLATFORM_DEPS = bin/Process.o bin/ServerUnix.o bin/EventListener.o

.PHONY: build clean

.build: Main

Main: $(COMMON_DEPS) $(PLATFORM_DEPS)
	$(CC) $^ -o $@ -lprotobuf

bin/Main.o: src/common/Main.cpp
	$(CC) $^ $(CFLAGS) -c -o $@

bin/Client.o: src/common/Client.cpp
	$(CC) $^ $(CFLAGS) -c -o $@

bin/Message.o: src/common/Message.cpp
	$(CC) $^ $(CFLAGS) -c -o $@

bin/Server.o: src/common/Server.cpp
	$(CC) $^ $(CFLAGS) -c -o $@

bin/player.pb.o: src/common/proto/player.pb.cc
	$(CC) $^ $(CFLAGS) -c -o $@

bin/PlayerCommand.o: src/common/proto/PlayerCommand.cpp
	$(CC) $^ $(CFLAGS) -c -o $@

bin/Process.o: src/unix/Process.cpp
	$(CC) $^ $(CFLAGS) -c -o $@

bin/ServerUnix.o: src/unix/ServerUnix.cpp
	$(CC) $^ $(CFLAGS) -c -o $@

bin/EventListener.o: src/unix/mac/EventListener.cpp
	$(CC) $^ $(CFLAGS) -c -o $@

clean:
	rm -rf bin/*.o bin/Main
