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

## How to create assembly code?
```bash
clang++  -S main.cpp
## we can find main.s file
```