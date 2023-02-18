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

## What is the output of this splice? "KavinKishto"[2:9:2]
%
* 'vnih'

## How to create anki from this markdown file
* mdanki Python_Anki.md Python_Anki.md.apkg --deck "Mohan::Kavin::Pattern::Python_Anki.md"