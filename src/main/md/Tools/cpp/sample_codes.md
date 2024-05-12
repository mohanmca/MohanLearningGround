## Handle Signal

```cpp
#include <iostream>
#include <csignal>
#include <atomic>
#include <thread>
#include <mutex>

using namespace std;

// Atomic flag to indicate whether the signal was received
atomic<bool> signalReceived(false);

// Mutex to protect shared resources
mutex mtx;

// Signal handler function
void signalHandler(int signum) {
    // Set the atomic flag to true
    signalReceived.store(true, memory_order_relaxed);
}

// Worker thread function
void workerThread() {
    while (true) {
        // Check if the signal was received
        if (signalReceived.load(memory_order_relaxed)) {
            // Lock the mutex before accessing shared resources
            unique_lock<mutex> lock(mtx);

            // Perform any necessary operations here
            cout << "Signal received! Performing cleanup...\n";

            // Reset the signal flag
            signalReceived.store(false, memory_order_relaxed);

            // Release the lock
            lock.unlock();
        }

        // Simulate some work being done
        this_thread::sleep_for(chrono::seconds(1));
    }
}

int main() {
    // Register signal SIGINT and signal handler
    signal(SIGINT, signalHandler);

    cout << "Signal handling example. Press Ctrl+C to trigger SIGINT.\n";

    // Start the worker thread
    thread worker(workerThread);

    // Wait for the worker thread to finish (which it won't)
    worker.join();

    return 0;
}
```

## When to use Anonymous namespace

```cpp

#include <iostream>

namespace {
    // This variable is accessible only within this translation unit.
    int internalValue = 42;

    // This function is accessible only within this translation unit.
    void internalFunction() {
        std::cout << "Internal function called\n";
    }
}

// Public function
void publicFunction() {
    // Access the internal variable
    std::cout << "Internal value: " << internalValue << std::endl;

    // Call the internal function
    internalFunction();
}

int main() {
    // Call the public function
    publicFunction();

    return 0;
}
```

## How to initialise struct

```cpp
struct MyStruct {
    int a;
    double b;
    char c;
};

// Initializing a struct using aggregate initialization
MyStruct s = {10, 3.14, 'X'};

// Initializing a struct using direct initialization
MyStruct s(10, 3.14, 'X');
```

## How to initialize member varaible in class using Direct Initialization Technique

```cpp
class MyClass {
public:
    MyClass(int a, int b) : member1(a), member2(b) {}
private:
    int member1;
    int member2;
};
```

## Structured binding example
1. The auto [n,v] declares two local variables n and v with their types deduced from read_entry()’s return type. This mechanism for giving local names to members of a class object is called structured binding.
```cpp
struct Entry {
        string name;
        int value;
};

Entry read_entry(istream& is)            // naive read function (for a better version, see §11.5)
{
        string s;
        int i;
        is >> s >> i;
        return {s,i};
}

auto e = read_entry(cin);

cout << "{ " << e.name << " , " << e.value << " }\n";
“auto [n,v] = read_entry(is);
cout << "{ " << n << " , " << v << " }\n";”


map<string,int> m;
// ... fill m ...
for (const auto [key,value] : m)
        cout << "{" << key << "," << value << "}\n";

void incr(map<string,int>& m)        // increment the value of each element of m
{
    for (auto& [key,value] : m)
            ++value;
}
```

## Generate MdAnki
```bash
mdanki sample_codes.md sample_codes.apkg --deck "Mohan::DeepWork::sample_code_cpp"
```
