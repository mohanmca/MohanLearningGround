## What is hello world program in CPP?

```c++
#include <iostream>

using namespace std;
int main(void) {
  cout << "It's working\n" << endl;
}
```


## Data types
1. 3E8 == 300,000,000 = 3 x 10^8
2. Plancks constant - 6.62607E-34
3. Divide by zero might cause - compilation error, runtime error or some message at runtime.
   4. Division by 0 invokes undefined behaviour
4. Operators of larger (higher) priority perform their operations before the operators with lower priority.
5. Binding, C++ mostly left binding. 

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

## Tidbits about C++

1. in C++, all strings are considered as arrays
2. Char is just subset of number, that would be treated as alphabets. ASCII mapping.
3. In ASCII, --- space is 32, A = 65, A+___ (space) = a, 97
4. \0 = nul char, represent no-character
5. '\47' = Octal chracter, 39 decimal == '\'', '\x27'
6. you can mix and match int with char and perform arithmatic operation
7.   'a' - ' ', a - 32, 'A' + 32, 'A' + ' '

## Stream has two responsible
1. Converting any types into human readable
2. Transfer them to output device
3. You can use *manipulator* along with stream to transform values
4. hex is one manipulator, changes the output streams properties called *basefield*
5. hex, dec, oct are the other manipulators
6. Manipulators
7. endl is a manipulator

## Manipulator Examples
```C++
int main() {
    int byte = 255;
    cout << hex << byte << endl;
    cout << byte << endl << dec << byte<<endl;
}
```

```c++
#include <iostream>
#include <iomanip>
using namespace std;
 
int main()
{
    float PI = 3.14;
    int num = 100;
    cout << "Entering a new line." << endl;
    cout << setw(10) << "Output" << endl;
    cout << setprecision(10) << PI << endl;
    cout << setbase(16) << num << endl; //sets base to 16
}
```

* The sign << is called the insertion operator.
* The sign >> is called the extraction operator.
* cout keyword is used to print.
* cin keyword is used to take input at run time.


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

## datatype modifers to reduce memory or increase width of the number
1. modifiers such as long/short/unsigned would either decrease the memory usage of primitivie-data-type such as int, or increase to suit larger number
2. long int == long, short int = shot, unsigned int = unsigned
3. char == [-128 to 127], unsigned char == 0 255
4. long can be declred in 3 days
	5. literal value goes beyond the acceptable range of type int; 98l or 987L (suffix L)
 6. short modifer never works with float, but `long float` would work, 'long float == double'
 7. double can store larger number and with better accuracy
 8. float would store 8 digits after decimal point for accuracy, double would store 15-17 accuracy
 9. numerical anomaly - is unavoidable

## boolean
1. bool developer_is_hubgry =  true
2. boolean is like int, but it uses only 8-bit (1 - byte)
3. zero == false, non-zero == true

## empty for-loop
1. when data missing, c++ assumes 1 is there, hence below is infinite;
```c++
for( ; ; ) {
  /* the body goes here */ 
}
```

## Logical opertion on it
```
C++ also uses - two's complement numbers
i: 00000000000000000000000000001111
j: 00000000000000000000000000010110
int log = i && j;
log:00000000000000000000000000000001
int bit = i & j;
bit:00000000000000000000000000000110
```

## Unsigned CPP (double the range)
```
unsigned int myUnsignedVariable = 0;
myUnsignedVariable--;  // Decrementing from 0
It will wrap around, and the result will be the maximum value representable by an unsigned int. For a 32-bit unsigned int, the maximum value is 4294967295.
```

## Reference
1. [cpp.sh](https://cpp.sh)
2. [https://ideone](https://ideone.com/HmZVro](https://ideone.com/HmZVro)
3. 
