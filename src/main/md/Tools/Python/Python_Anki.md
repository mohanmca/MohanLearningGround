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


## What is the output of this splice? "KavinKishto"[2:9:2]
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



## How to create anki from this markdown file
* mdanki Python_Anki.md Python_Anki.md.apkg --deck "Kavin::Pattern::Python_Anki"