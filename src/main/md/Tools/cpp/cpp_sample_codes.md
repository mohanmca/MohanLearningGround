## [Learn CPP by Example](https://cppbyexample.com/)
1. [CPP by example](https://cppbyexample.com/)

## How to get the address of the underlying object of the smart pointer?

```bash
int main() {
    std::shared_ptr<MyClass> ptr = std::make_shared<MyClass>(10);
    MyClass* raw_ptr = ptr.get();
    return 0;
}
```

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

## Find SQRT of a float

```C++
#include <iostream>
#include <cmath>

using namespace std;

int main(void) {
        float value, squareroot;

        cout << "Give me a number and I will find its square root:" << endl;
        cin >> value;
        
	        squareroot = sqrtf(value);
	        cout << "You have given: " << value << endl;
	        cout << "The square root is: " << squareroot << endl;
        
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

## How to do pretty print for a class?

```cpp
#include <iostream>

class Point {
public:
    int x, y;
    Point(int x = 0, int y = 0) : x(x), y(y) {}
};

// Overload the operator<< for Point
std::ostream& operator<<(std::ostream& os, const Point& point) {
    os << "Point(" << point.x << ", " << point.y << ")";
    return os;
}

int main() {
    Point p1(3, 4);
    std::cout << p1 << std::endl; // Output: Point(3, 4)
    return 0;
}
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

## What is the output of this program

```cpp
/**
* Number is: 205
* Extension is: .txt
**/ 
#include <iomanip>
#include <iostream>
#include <sstream>

using namespace std;

int main() {
    stringstream filename {"00205.txt"};
    int num = 0, n=0;
    string name, ext;
    filename >> num >> ext;
    cout << "Number is: " << num << endl;
    cout << "Extension is: " << ext << endl;
    return 0;
}
```

## What is the output of the following code?

```c
/***
Copy this!
Copy this!
this!
**/
#include <cstring> 
#include <iostream>

int main() {
    const char source[] = "Copy this!"; 
    char dest [5];
    std::cout << source << '\n'; 
    std::strcpy(dest, source);
    std::cout << dest << '\n';
    // source is const, no problem right?
    std::cout << source << '\n'; return 0;
}
```

## What is the failure?
//Program terminated with signal: SIGSEGV
```cpp
#
#include <cstring> 
#include <iostream>

int& MultiplyBy10(int num) {
    int ret = 0;
    ret = 10 * num;
    return ret;

}

int main() {
    std::cout << MultiplyBy10(11) << std::endl;
    return 0;
}
```
Never return reference to local variable



## Function Arguments
1. use function name as CamelCase
1. use argument name as snake_case
1. Always try to pass arguments as const-pass-by-reference (const std::string& huge_string);


## Namespace
1. Avoid using namespace <name>, local definition might shadow other namespace declaration
1. namespace splits the project into logical unit
1. Never use using namespace name in *.hpp files
1. Prefer using explicit using even in *.cpp files
1. Use using correctly,... "using my_namepspace::myFunc"

## Namless namespace [Stroustrup chapter-014]
1. If you find yourself relying on some constants in a file and these constants should not be see in any other fiile
1. Put them into nameless namespace on the top of this file.

```cpp
namespace {
    const int  kLocal = 13;
    const float kLocalFloat = 13.0f
}
```

## Pointer to rescue stack space
```
#include <iostream>
#include <cstdlib>
// Type your code here, or load an example.
int square(int num) {
    return num * num;
}

using std::cout;

int main(int argc, char** argv) {
    int size=2;
    int* ptr = nullptr;
    {
        int arr[2] = {2, 3};
        ptr = arr;
    }
    for(int i=0;i<2;i++) cout << ptr[i] << std::endl;
}
```

## Behaviour of shared pointer 

```cpp
#include <iostream>
#include <memory> // For std::shared_ptr

class MyClass {
public:
    MyClass() {
        ++instance_count;
        std::cout << "MyClass constructor called! Instance count: " << instance_count << std::endl;
    }
    
    ~MyClass() {
        --instance_count;
        std::cout << "MyClass destructor called! Instance count: " << instance_count << std::endl;
    }
    
    void display() const {
        std::cout << "MyClass instance method called! Current instance count: " << instance_count << std::endl;
    }

    static int getInstanceCount() {
        return instance_count;
    }

private:
    static int instance_count;
};

// Define and initialize the static member variable
int MyClass::instance_count = 0;

void useSharedPtr(std::shared_ptr<MyClass> ptr) {
    std::cout << "In function, use count: " << ptr.use_count() << std::endl;
    ptr->display();
}

int main() {
    std::cout << "Initial instance count: " << MyClass::getInstanceCount() << std::endl;

    // Create a shared_ptr instance
    std::shared_ptr<MyClass> ptr1 = std::make_shared<MyClass>();
    std::cout << "After creation, use count: " << ptr1.use_count() << std::endl;
    std::cout << "Instance count: " << MyClass::getInstanceCount() << std::endl;

    {
        // Create another shared_ptr instance that shares ownership with ptr1
        std::shared_ptr<MyClass> ptr2 = ptr1;
        std::cout << "In scope, use count: " << ptr1.use_count() << std::endl;
        std::cout << "Instance count: " << MyClass::getInstanceCount() << std::endl;

        useSharedPtr(ptr2);
        std::cout << "After function call, use count: " << ptr1.use_count() << std::endl;
    }
    
    std::cout << "Out of inner scope, use count: " << ptr1.use_count() << std::endl;
    std::cout << "Instance count: " << MyClass::getInstanceCount() << std::endl;

    // ptr1 goes out of scope here, and the managed object will be deleted
    return 0;
}
```


## How to get the address of the underlying object of the smart pointer?

```bash
int main() {
    std::shared_ptr<MyClass> ptr = std::make_shared<MyClass>(10);
    MyClass* raw_ptr = ptr.get();
    return 0;
}
```

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

## How to force compiler not to generate implicit constructors?

```cpp
class MyClass {
public:
    MyClass() = delete;                      // Delete default constructor
    MyClass(const MyClass&) = delete;        // Delete copy constructor
    MyClass(MyClass&&) = delete;             // Delete move constructor
    MyClass& operator=(const MyClass&) = delete; // Delete copy assignment operator
    MyClass& operator=(MyClass&&) = delete;  // Delete move assignment operator

    // Add your own constructors or methods here
    MyClass(int x) {
        // constructor code
    }
};
```

## static variables
1. static is happens during compile time
1. Dynami is only during run-time
1. Avoid static as much as possible
1. f::counter  has been statically allcoated
```cpp
void Counter() {
    static int counter = 0;
    counter++;
}
f(); // counter = 1
f(); // counter = 2
```


## how to run benchmark?
1. [Sample cpp benchmark](https://quick-bench.com/q/yovU63tEGtde-VjFD1xB2e_VhPY)

## Function calls
1. It is expensive
1. make it inline if it is small
1. inline is a hint to the compiler
1. [Manipulate function definition with inline](https://godbolt.org/z/EGd6aG)


## Namless namespace [Stroustrup chapter-014]
1. If you find yourself relying on some constants in a file and these constants should not be see in any other fiile
1. Put them into nameless namespace on the top of this file.

```cpp
namespace {
    const int  kLocal = 13;
    const float kLocalFloat = 13.0f
}
```

## Pointer to rescue stack space
```
#include <iostream>
#include <cstdlib>
// Type your code here, or load an example.
int square(int num) {
    return num * num;
}

using std::cout;

int main(int argc, char** argv) {
    int size=2;
    int* ptr = nullptr;
    {
        int arr[2] = {2, 3};
        ptr = arr;
    }
    for(int i=0;i<2;i++) cout << ptr[i] << std::endl;
}
```

## Behaviour of shared pointer 

```cpp
#include <iostream>
#include <memory> // For std::shared_ptr

class MyClass {
public:
    MyClass() {
        ++instance_count;
        std::cout << "MyClass constructor called! Instance count: " << instance_count << std::endl;
    }
    
    ~MyClass() {
        --instance_count;
        std::cout << "MyClass destructor called! Instance count: " << instance_count << std::endl;
    }
    
    void display() const {
        std::cout << "MyClass instance method called! Current instance count: " << instance_count << std::endl;
    }

    static int getInstanceCount() {
        return instance_count;
    }

private:
    static int instance_count;
};

// Define and initialize the static member variable
int MyClass::instance_count = 0;

void useSharedPtr(std::shared_ptr<MyClass> ptr) {
    std::cout << "In function, use count: " << ptr.use_count() << std::endl;
    ptr->display();
}

int main() {
    std::cout << "Initial instance count: " << MyClass::getInstanceCount() << std::endl;

    // Create a shared_ptr instance
    std::shared_ptr<MyClass> ptr1 = std::make_shared<MyClass>();
    std::cout << "After creation, use count: " << ptr1.use_count() << std::endl;
    std::cout << "Instance count: " << MyClass::getInstanceCount() << std::endl;

    {
        // Create another shared_ptr instance that shares ownership with ptr1
        std::shared_ptr<MyClass> ptr2 = ptr1;
        std::cout << "In scope, use count: " << ptr1.use_count() << std::endl;
        std::cout << "Instance count: " << MyClass::getInstanceCount() << std::endl;

        useSharedPtr(ptr2);
        std::cout << "After function call, use count: " << ptr1.use_count() << std::endl;
    }
    
    std::cout << "Out of inner scope, use count: " << ptr1.use_count() << std::endl;
    std::cout << "Instance count: " << MyClass::getInstanceCount() << std::endl;

    // ptr1 goes out of scope here, and the managed object will be deleted
    return 0;
}
```

## How to parse URL path using STD algorithm in C++

```cpp
#include <iostream>
#include <string>
#include <algorithm>
#include <iterator>

void parse_url(const std::string& url) {
    // Find the protocol using std::search
    auto protocol_end = std::search(url.begin(), url.end(), "://", "://" + 3);
    std::string protocol(url.begin(), protocol_end);

    // Find the host using std::find
    auto host_start = protocol_end;
    if (host_start != url.end()) {
        std::advance(host_start, 3); // Skip "://"
    }
    auto path_start = std::find(host_start, url.end(), '/');
    std::string host(host_start, path_start);

    // Find the path using std::find
    auto query_start = std::find(path_start, url.end(), '?');
    std::string path(path_start, query_start);

    // Find the query
    std::string query;
    if (query_start != url.end()) {
        ++query_start; // Skip '?'
        query = std::string(query_start, url.end());
    }

    // Output the results
    std::cout << "Protocol: " << protocol << std::endl;
    std::cout << "Host: " << host << std::endl;
    std::cout << "Path: " << path << std::endl;
    std::cout << "Query: " << query << std::endl;
}

int main() {
    std::string url = "https://www.example.com/path/to/resource?query=example";
    parse_url(url);
    return 0;
}
```

## Standard way of catching exceptions

```cpp
#define EXPECT_NO_THROW_WITH_DETAIL(stmt) \
    try { \
        stmt; \
    } catch (const std::invalid_argument& e) { \
        std::cerr << "Caught invalid_argument: " << e.what() << std::endl; \
        FAIL() << "invalid_argument thrown: " << e.what(); \
    } catch (const std::out_of_range& e) { \
        std::cerr << "Caught out_of_range: " << e.what() << std::endl; \
        FAIL() << "out_of_range thrown: " << e.what(); \
    } catch (const std::runtime_error& e) { \
        std::cerr << "Caught runtime_error: " << e.what() << std::endl; \
        FAIL() << "runtime_error thrown: " << e.what(); \
    } catch (const std::logic_error& e) { \
        std::cerr << "Caught logic_error: " << e.what() << std::endl; \
        FAIL() << "logic_error thrown: " << e.what(); \
    } catch (const std::exception& e) { \
        std::cerr << "Caught exception: " << e.what() << std::endl; \
        FAIL() << "Exception thrown: " << e.what(); \
    } catch (...) { \
        std::cerr << "Caught unknown exception" << std::endl; \
        FAIL() << "Unknown exception thrown"; \
    }
```

## Generate MdAnki
```bash
mdanki cpp_sample_codes.md cpp_sample_codes.apkg --deck "Mohan::DeepWork::sample_code_cpp"
```
