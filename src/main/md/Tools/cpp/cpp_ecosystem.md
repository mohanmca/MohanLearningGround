## Compilation steps
1. [Modern CPP](https://www.ipb.uni-bonn.de/teaching/cpp-2020/index.html)
1. [Compilation steps](https://www.ipb.uni-bonn.de/html/teaching/modern-cpp/slides/lecture_1.pdf)
1. Ensure build-essentials installed prior to compliing in C/C++

## How to compile using clang?
```bash
clang++ main.cpp
./a.out
```

## What can be replaced with includer header.h
1. It is just copying content of header.h into translation-unit or compilation unit
2. we can just copy paste the content into compilation unit and compiler

## How to debug preprocessor
```cpp
clang++ -E main.cpp > main.i
clang++ -E main.cpp ## to view the content
```

## Does pre-processor removes the comment?
1. Yes, -E flag confirms it

## How to create assembly code from code?
```bash
clang++  -S main.cpp
## we can find main.s file
```

## How to create object file from assembly.s?
```bash
clang++  -c main.s
## we can find main.o file
```

## Libraries types
1. .a - static
2. .so  - dynamic

## Compling using higher standard

```bash
clang++ -std=c++17 program.cpp -o main.o
```

## What is the role of linker
1. fn :: Linker == ([objects], [libraries]) --> executable
2. Takes one are more objects + libraries and produces executable

## How to find names inside object file
1. nm is a utility that displays the symbol table of a given object, executable, or binary file in Linux
```bash
nm -gC tools.o
```

## How to create a static library
```bash
gcc -c tool.cpp -o tool.o
ar rcs tools.a tools.o
```

## Why we use AR command instead of tar to create library?

1. "ar" Designed specifically for creating static libraries.
1. Creates an archive of object files that can be linked into executable programs.
1. Generates symbol tables that are necessary for the linker to resolve symbols.
2. Supports updating and maintaining the archive without re-archiving all files.

## Command to find library named tools in current directory to create exeuctable
```bash
clang++  -std=c++17 -L . -ltools -o main
```

## Build system
1. shell < make
2. CMake -> meta build system, it is not build system
3. CMake + (Make or Ninja) ~~> BuildSystem


## CMakeLists.txt

```CMakeLists.txt
cmake_minimum_required(VERSION 3.1) # Mandatory.
project( first_project ) # Mandatory.
set(CMAKE_CXX_STANDARD 17) # Use c++17.
set (COMPILE_CXX_FLAGS "-Wall")
set(CMAKE_EXPORT_COMPILE_COMMANDS on)
# tell cmake where to look for *.hpp, *.h files
include_directories(include /)

# create library "libtools"
add_library(tools src/tools.cpp) # creates libtools.a from tools.cpp

# add executable main
add_executable(main src/tools_main .cpp) # main.o

# tell the linker to bind these objects together
target_link_libraries(main tools) # ./main
```

1. build/compile_commands.json is exported, that has all the commands

## List of libraries being used
1. [https://github.com/hyperrealm/libconfig](https://hyperrealm.github.io/libconfig/libconfig_manual.pdf)
    1. C/C++ library for processing configuration files
2. [A modern formatting library](https://github.com/fmtlib/fmt)
3. [Fast C++ logging library](https://github.com/gabime/spdlog)
4. [JSON for modern C++](https://github.com/nlohmann/json)
5. [Modern open source C++ FIX framework](https://github.com/fix8/fix8)
6. [GRPC C++](https://github.com/grpc/grpc/tree/master/src/cpp)
7. [SBE C++](https://github.com/real-logic/simple-binary-encoding/wiki/Cpp-User-Guide)
8. [K8S-EKS]
9. [Envoy, Istio, Haproxy, Loadbalancer - learn well]

## Why hyperrealm libconfig being used?
1. Libconfig is a library for reading, manipulating, and writing structured configuration files.
2. More readable and compact than XML and more flexible than the obsolete but prevalent Windows “INI” file format.

## [hyperrealm libconfig features](https://hyperrealm.github.io/libconfig/libconfig_manual.html#Introduction)
1.  name = value; or name: value ;
2.  groups - { settings ... } // but each seettings must have a unique name within the group
3.  Arrays - [ value, value ... ]
4.  Lists - ( value, value ... )
1.  A list may have zero or more elements, each of which can be a scalar value, an array, a group, or another list.
1.  The last element in a list may be followed by a comma, which will be ignored.
5. 64-bit Integer Values - For example, ‘0L’ indicates a 64-bit integer value 0.
6. Boolean Values : 'true' or 'false'
7. Include Directives - @include "quote.cfg"
      ```
         # file: test.cfg
         info: {
           name = "Winston Churchill";
           @include "quote.cfg"
           country = "UK";
         };
      ```

## CPP API
1. #include <libconfig.h++>
1. using namespace libconfig;


## Generate MdAnki

```bash
mdanki cpp_ecosystem.md cpp_ecosystem_to_be_more_productive_at_work.apkg --deck "Mohan::DeepWork::cpp::MoreProductive"
```

