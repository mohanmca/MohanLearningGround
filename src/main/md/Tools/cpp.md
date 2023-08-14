## What is simplest C++ program that processes command line String

```c++
#include <iostream>
#include <vector>
#include <string>

int main(int argc, char** argv) {
    std::vector<std::string> arguments;

    for (int i = 0; i < argc; ++i) {
        arguments.push_back(argv[i]);
    }

    for (const std::string& arg : arguments) {
        std::cout << arg << std::endl;
    }

    return 0;
}
```
