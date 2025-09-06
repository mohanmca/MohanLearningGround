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

## How to iterate over two lists together

```python
a = [1, 2, 3]
b = ['x', 'y', 'z']
for x, y in zip(a, b):
    print(x, y)
```

## How to enumerate starting from 1

```python
nums = [10, 20, 30]
for i, val in enumerate(nums, start=1):
    print(i, val)
```

## How to sort with custom key using lambda

```python
words = ["aaa", "b", "cc"]
words.sort(key=lambda x: len(x))
```

## How to create dictionary from two lists

```python
keys = ["a", "b"]
vals = [1, 2]
d = dict(zip(keys, vals))
```

## How to unpack list into variables

```python
nums = [1, 2, 3]
x, y, z = nums
```

## How to unpack with star

```python
a, *b = [1, 2, 3, 4]
```

## How to merge two dictionaries

```python
d1 = {"a": 1}
d2 = {"b": 2}
merged = {**d1, **d2}
```

## How to count elements in list

```python
from collections import Counter
cnt = Counter([1, 2, 2, 3])
cnt[2]==2
```

## How to get most common elements

```python
from collections import Counter
cnt = Counter("banana")
top = cnt.most_common(2)
cnt.most_common(0)==[]
cnt.most_common(1)==[('a', 3)]
cnt.most_common(2)==[('a', 3), ('n', 2)] 
```

## How to create default dictionary

```python
from collections import defaultdict
d = defaultdict(int)
d["a"] += 1
```

## How to group list by key

```python
from collections import defaultdict
groups = defaultdict(list)
for k, v in [("a", 1), ("b", 2), ("a", 3)]:
    groups[k].append(v)
```

## How to use heap as min-heap

```python
import heapq
heap = []
heapq.heappush(heap, 3)
heapq.heappush(heap, 1)
min_val = heapq.heappop(heap)
```

## How to use heap as max-heap

```python
import heapq
heap = []
heapq.heappush(heap, -3)
heapq.heappush(heap, -1)
max_val = -heapq.heappop(heap)
```

## How to get n smallest elements

```python
import heapq
nums = [5, 1, 3, 2]
smallest = heapq.nsmallest(2, nums)
```

## How to get n largest elements

```python
import heapq
nums = [5, 1, 3, 2]
largest = heapq.nlargest(2, nums)
```

## How to use deque for queue

```python
from collections import deque
q = deque()
q.append(1)
q.popleft()
```

## How to use deque for stack

```python
from collections import deque
stack = deque()
stack.append(1)
stack.pop()
```

## How to rotate deque

```python
from collections import deque
d = deque([1, 2, 3])
d.rotate(1)
```

## How to slice list with step

```python
nums = [0, 1, 2, 3, 4, 5]
evens = nums[::2]
evens == [0, 2, 4]
```

## How to reverse slice

```python
nums = [1, 2, 3]
rev = nums[::-1]
```

## How to flatten list of lists with sum

```python
nested = [[1, 2], [3, 4]]
flat = sum(nested, [])
```

## How to flatten with itertools.chain

```python
import itertools
flat = list(itertools.chain.from_iterable([[1, 2], [3, 4]]))
```

## How to create infinite counter

```python
import itertools
counter = itertools.count(start=1, step=2)
next(counter) == 1
next(counter) == 3
next(counter) == 5
```

## How to cycle through list

```python
import itertools
for val in itertools.islice(itertools.cycle([1, 2, 3]), 5):
    print(val)
```

## How to accumulate sums

```python
import itertools
nums = [1, 2, 3]
acc = list(itertools.accumulate(nums))
```

## How to all elements satisfy condition

```python
nums = [2, 4, 6]
res = all(x % 2 == 0 for x in nums)
```

## How to any element satisfy condition

```python
nums = [1, 2, 3]
res = any(x > 2 for x in nums)
```

## How to zip with index

```python
nums = [10, 20]
for i, val in zip(range(len(nums)), nums):
    print(i, val)
```

## How to transpose matrix

```python
matrix = [[1, 2], [3, 4]]
transposed = list(zip(*matrix))
```

## How to get unique elements preserving order
```python
nums = [1, 2, 1, 3]
seen = set()
res = [x for x in nums if not (x in seen or seen.add(x))]
```

## How to find index with condition
```python
nums = [10, 20, 30]
idx = next(i for i, x in enumerate(nums) if x > 15)
```

## How to safely find index or -1
```python
nums = [10, 20, 30]
idx = next((i for i, x in enumerate(nums) if x > 50), -1)
```

## How to sort by multiple keys
```python
items = [(1, 2), (1, 1), (0, 3)]
items.sort(key=lambda x: (x[0], -x[1]))
```

## How to create dictionary comprehension
```python
d = {x: x*x for x in range(5)}
```

## How to create set comprehension
```python
s = {x*x for x in range(5)}
```

## How to filter list comprehension

```python
nums = [1, 2, 3, 4]
evens = [x for x in nums if x % 2 == 0]
```

## How to use defaultdict with list

```python
from collections import defaultdict
d = defaultdict(list)
d["a"].append(1)
```

## How to count frequency without Counter

```python
freq = {}
for num in [1, 2, 1]:
    freq[num] = freq.get(num, 0) + 1
```

## How to get min with key

```python
nums = [1, 2, 3]
val = min(nums, key=lambda x: -x)
```

## How to get max with key

```python
nums = ["a", "bb", "ccc"]
val = max(nums, key=len)
```

## How to sort dict by key

```python
d = {"b": 2, "a": 1}
sorted_items = sorted(d.items())
```

## How to sort dict by value

```python
d = {"a": 2, "b": 1}
sorted_items = sorted(d.items(), key=lambda x: x[1])
```

## How to zip into sorted pairs

```python
keys = ["a", "b"]
vals = [2, 1]
pairs = sorted(zip(keys, vals), key=lambda x: x[1])
```

## How to flatten deeply nested list with recursion

```python
def flatten(lst):
    for x in lst:
        if isinstance(x, list):
            yield from flatten(x)
        else:
            yield x
```

## Trie Implementation

```python
class TrieNode:
    def __init__(self):
        self.children = [None] * 26
        self.end = False

class Trie:
    def __init__(self):
        self.root = TrieNode()
    
    def _idx(self, c: str) -> int:
        return ord(c) - ord('a')
    
    def insert(self, word: str) -> None:
        node = self.root
        for c in word:
            i = self._idx(c)
            if not node.children[i]:
                node.children[i] = TrieNode()
            node = node.children[i]
        node.end = True
    
    def search(self, word: str) -> bool:
        node = self.root
        for c in word:
            i = self._idx(c)
            if not node.children[i]:
                return False
            node = node.children[i]
        return node.end
    
    def startsWith(self, prefix: str) -> bool:
        node = self.root
        for c in prefix:
            i = self._idx(c)
            if not node.children[i]:
                return False
            node = node.children[i]
        return True
```
