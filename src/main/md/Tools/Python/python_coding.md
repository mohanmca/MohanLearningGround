## How to generate random nmber in python
```
import random
rand = random.randint(0,10)
```

## How to tuple in reverse order
```
>>> for i in range(10):
...     j.append((random.randint(0,10),i))
...     
>>> j
[(7, 0), (5, 1), (3, 2), (1, 3), (9, 4), (0, 5), (7, 6), (5, 7), (2, 8), (2, 9)]
>>> j.sort(reverse=True)
>>> j
[(9, 4), (7, 6), (7, 0), (5, 7), (5, 1), (3, 2), (2, 9), (2, 8), (1, 3), (0, 5)]
```

## How to initialize variable with inf
```
import math
MAX_INT = math.inf     # Positive infinity
MIN_INT = -math.inf    # Negative infinity
```

## How to sort a list ascending

```python
nums = [5, 1, 3]
nums.sort()
```

## How to sort a list descending

```python
nums = [5, 1, 3]
nums.sort(reverse=True)
```

## How to reverse a list

```python
nums = [1, 2, 3]
nums.reverse()
```

## How to find max in a list

```python
nums = [3, 7, 2]
max_val = max(nums)
```

## How to check if element in list

```python
exists = 5 in [1, 2, 5]
```

## How to convert tuple to list

```python
lst = list((1, 2, 3))
```

## How to convert list to tuple

```python
tpl = tuple([1, 2, 3])
```

## How to iterate with index

```python
nums = [10, 20]
for i, val in enumerate(nums):
    print(i, val)
```

## How to create a dictionary

```python
d = {"a": 1, "b": 2}
```

## How to check if value exists

```python
exists = 2 in d.values()
```

## How to iterate over dictionary

```python
for k, v in d.items():
    print(k, v)
```

## How to remove key from dictionary

```python
d.pop("a", None)
```

## How to get value with default

```python
val = d.get("c", 0)
```

## How to generate random float

```python
import random
val = random.random()  # 0.0 to 1.0
```

## How to sort list by custom key

```python
words = ["apple", "banana", "kiwi"]
words.sort(key=len)
```

## How to fill list with value

```python
nums = [5] * 10
```

## How to copy list

```python
copy = nums[:]
```

## How to merge two lists

```python
merged = list1 + list2
```

## How to sum list elements

```python
total = sum([1, 2, 3])
```

## How to convert string to int

```python
num = int("123")
```

## How to convert int to string

```python
s = str(123)
```

## How to split string by space

```python
parts = "a b c".split()
```


## How to reverse string
```python
rev = "hello"[::-1]
```

## How to check palindrome

```python
s = "madam"
is_pal = s == s[::-1]
```

## How to compare strings ignoring case

```python
eq = s1.lower() == s2.lower()
```

## How to get substring

```python
sub = "abcdef"[2:5]
```

## How to strip whitespace

```python
trimmed = "  hi  ".strip()
```

## How to replace substring

```python
new_s = "abc abc".replace("a", "x")
```

## How to convert list to upper case

```python
words = ["a", "b"]
words = [w.upper() for w in words]
```

## How to read input from console

```python
line = input()
```

## How to format string

```python
name, age = "John", 30
s = f"Name: {name} Age: {age}"
```

## How to create set

```python
s = {1, 2, 3}
```

## How to check if set contains element

```python
exists = 2 in {1, 2, 3}
```

## How to find set intersection

```python
a = {1, 2}
b = {2, 3}
inter = a & b
```

## How to find set union

```python
a = {1, 2}
b = {2, 3}
uni = a | b
```

## How to remove duplicates from list

```python
nums = list(set([1, 1, 2]))
```

## How to sort dictionary by value

```python
d = {"a": 2, "b": 1}
sorted_items = sorted(d.items(), key=lambda x: x[1])
```

## How to enumerate in reverse

```python
for i, val in enumerate(reversed(nums)):
    print(i, val)
```

## How to flatten list of lists

```python
flat = [x for sub in [[1, 2], [3]] for x in sub]
```

## How to find index of element

```python
idx = nums.index(3)
```
