################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CC_SRCS += \
../src/proto/player.pb.cc 

OBJS += \
./src/proto/player.pb.o 

CC_DEPS += \
./src/proto/player.pb.d 


# Each subdirectory must supply rules for building sources it contributes
src/proto/%.o: ../src/proto/%.cc
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


