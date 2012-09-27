################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../src/server/Main.cpp \
../src/server/ServerMac.cpp \
../src/server/ServerUnixCommon.cpp 

OBJS += \
./src/server/Main.o \
./src/server/ServerMac.o \
./src/server/ServerUnixCommon.o 

CPP_DEPS += \
./src/server/Main.d \
./src/server/ServerMac.d \
./src/server/ServerUnixCommon.d 


# Each subdirectory must supply rules for building sources it contributes
src/server/%.o: ../src/server/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


