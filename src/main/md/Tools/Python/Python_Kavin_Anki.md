## What is the output of 
```bash
print("Mohan", "Kavin", sep="-------- ", end="!")
```
%
* Mohan-------- Kavin!

## What is the output of this splice? 
```python
[0,1,2,3,4][-1::1] and [0,1,2,3,4][-1]
```
%
* [4] and 4
* One is list and another is int


## What is the output of this splice? "KavinKisho"[2:9:2]
%
* 'vnih'

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
    str1 = input("Please enter a word\sentence: ")
    print("This input has " + str(len(str1)) + " characters")
    for i in range(0, len(str1), 1):
        print(str1[i])
```


## How to print String in Reverse manner?
%
```python
def ReverseString():
    # Reverse print a string
    str1 = input("Please enter a word: ")
    for i in range(len(str1) - 1, -1, -1):
        print(str1[i], end="")
```

## How to print 1-to-010?
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

## How to write a program like below?
```
wow
wow
going to get 5
going to get 5
I got 5
```
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


## How to create anki from this markdown file
* mdanki Python_Kavin_Anki.md Python_Kavin.apkg --deck "Kavin::Pattern::Python"