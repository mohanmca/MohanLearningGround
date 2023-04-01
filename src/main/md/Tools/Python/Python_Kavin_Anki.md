## What is the output of 
```bash
print("Mohan", "Kavin", sep="-------- ", end="!")
```
%
* Mohan-------- Kavin!

## What is the difference between variable and function

1. variable can't do any task, it just remembers name or value
2. function is a specific task, function always has parenthesis

## What is the characteristics of function
1. Function is tiny program
2. Function might take one or more input
3. Function would always produce output
4. If function not producing output, we assume it produced None as its output

## How many arguments are there for list splice
1. 3 arguments
2. [start:stop:increment]

## What is the output of this splice? 
```python
[0,1,2,3,4][-1::1] and [0,1,2,3,4][-1]
```
%
* [4] and 4
* One is list and another is int

## How to print list of numbers in reverse order?

```python
>>> [1,2,3,4][::-1]
[4, 3, 2, 1]
```

## What is the output of this splice? "KavinKisho"[2:9:2]
%
* 'vnih'

## How to convert string into number in python?

* We should convert string into int using int(string) function
* int("3")

## How to find middleIndex of a variable "testString"
```python
    middleIndex = int((len(testString) - 1) / 2)
```

## What is the output of the following?
```python
testString="Kavin"
middleIndex = int((len(testString) - 1) / 2)
print(middleIndex)
```
%
* 2

## How to print 0 to 9 using python

```python
>>> for i in range(0,10):
...     print(i)
```


## How to print infinite Kavin in screen
```python
>>> while True:
...     print("Kavin")
```

## How to get input from Keyboard?

```python
str1 = input("Please enter a word\sentence: ")
```

## How to get number input from Keyboard?

```python

stringVariable = input("Please enter a word\sentence: ")
number = int(stringVariable)
```

## How randomly generate a number between 10 and 25?

```python
>>> import random
>>> random.randint(10,25)
23
```

## How to print 2 table?

```python
for i in range(1,11):
    print(i*2)
    
2
4
6
8
10
12
14
16
18
20
```

## How to print 2,3,4 table?

```python
for table in range(2,5):
    for line in range(1,5):
        print(table*line)
    print("\n")
    
2
4
6
8

3
6
9
12

4
8
12
16
```

## How to print only middle 3 characters of the String whose length is more than 7?
%
```python
def mid3(testString):
    # Check if the number is odd or even
    # using the modules operator
    if len(testString) % 2 == 1 and len(testString) > 7:
        middleIndex = int((len(testString) - 1) / 2)
        print(testString[middleIndex - 1])
        print(testString[middleIndex])
        print(testString[middleIndex + 1])
```

## How to print String in vertical manner?
%
```python
def VerticalPrint():
    stringVariable = input("Please enter a word\sentence: ")
    print("This input has " + str(len(stringVariable)) + " characters")
    for i in range(0, len(stringVariable), 1):
        print(stringVariable[i])
```


## How to print String in Reverse manner?
%
```python
def ReverseString():
    # Reverse print a string
    stringVariable = input("Please enter a word: ")
    for i in range(len(stringVariable) - 1, -1, -1):
        print(stringVariable[i], end="")
```

## How to print 1-to-10?
%
```python
for i in range(1,11):
    print(i)
```

## How to print table-2 from 1-to-010?
%
```python
>>> for i in range(1,11):
...     print(i*2)
... 
```

## How to print 1-* and 2-* till 10-* in same line?
%
```python
>>> for i in range(1,11):
...     print(" ")
...     for count in range(1,i+1):
...             print("*", end=" ")
... 
```

## How to Pyramid of numbers like below
"""
1
1 2
1 2 3 
1 2 3 4 
1 2 3 4 5
"""
%
```python
def NumPyramid2(rowNum):
    for j in range(2, rowNum + 2, 1):
        for i in range(1, j, 1):
            print(i, end=" ")

        print("\r")
```

## How to print different symbols based on value of the score?
if score = 12 ^_^, score greater than 15 *_*, > 5 %_%
%
```python
if score > 12:
    print("^_^")
elif score > 15:
    print("*_*")
elif score > 5:
    print("%_%")
else:
    print("#_#")
```

## How to write a program like below - cookie_count?
```
wow
wow
going to get 5
going to get 5
I got 5
```
%
```python
cookie_count = 0
while True:
    cookie_count = cookie_count + 1
    if cookie_count > 4:
        print("I got 5")
        break
    elif cookie_count < 3:
        print("wow")
    else:
        print("going to get 5")
```

## How to create list that has 10 random numbers in it.

```python
>>> from random import randint
>>> [randint(1,21) for i in range(1,11)]
[10, 17, 3, 19, 20, 7, 11, 4, 4, 18]
```

## What are all the datatypes in python, 

```python
>>> type("Kavin")   -> <class 'str'>
>>> type(1)         -> <class 'int'>
>>> type(True)      -> <class 'bool'>
>>> type(1.0)       -> <class 'float'>
>>> type(len)       -> <class 'builtin_function_or_method'>
```

## What are 20 most used python built-in functions

1. abs()
2. bool()
3. hash()
4. help()
5. id()
6. input()
7. int()
8. list()
9. max()
10. min()
11. print()
12. range()
13. reversed()
14. round()
15. slice()
16. sorted()
17. str()
18. sum()
19. type()
20. vars()

## How to create anki from this markdown file
* mdanki Python_Kavin_Anki.md Python_Kavin.apkg --deck "Kavin::Python"