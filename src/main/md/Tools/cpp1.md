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
```
The sign << is called the insertion operator.
The sign >> is called the extraction operator.
cout keyword is used to print.
cin keyword is used to take input at run time.
```
 
