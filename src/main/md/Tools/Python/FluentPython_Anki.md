## What is del in python? what is del(x)

1. del is keyword and statement in python
2. del removes reference (doesn't delete object)
3. if reference was the last one, it might leads to invoke __del__ in python
4. in python x and (x) are same
   1. del(x) == del x
   1. Hence it is nothing special

## What is __del__ method?

7. __del__ does not cause the disposal of the instance, and should not be called by your code. 
8. __del__ is invoked by the Python interpreter when the instance is about to be destroyed to give it a chance to release external resources. 
9.  You will seldom need to implement __del__ in your own code, 

## What is finalizer and how to understand using weakref?

```python
>>> import weakref
>>> s1 = {1, 2, 3}
>>> s2 = s1         1
>>> def bye():      2
...     print('...like tears in the rain.')
...
>>> ender = weakref.finalize(s1, bye)  3
>>> ender.alive  4
True
>>> del s1
>>> ender.alive  5
True
>>> s2 = 'spam'  6
...like tears in the rain.
>>> ender.alive
False
```

## What is positional only parameters?

1. User-defined function signatures may specify positional-only parameters. 
2. This feature always existed for built-in functions, such as divmod(a, b).
3. which can only be called with positional parameters, and not as divmod(a=10, b=4).


## How to define positional only parameters?

1. To define a function requiring positional-only parameters, use / in the parameter list.
2. All arguments to the left of the / are positional-only. After the /, you may specify other arguments, which work as usual.
3. 
   ```python
   def divmod(a, b, /):
       return (a // b, a % b)
   ```

## What is itemgetter

1. itemgetter, the function it builds will return tuples with the extracted values, which is useful for sorting on multiple keys:
```python
    >>> cc_name = itemgetter(1, 0)
    >>> for city in metro_data:
    ...     print(cc_name(city))
```

## How to support *keyword-only* arguments?
1. put a * by itself in the signature
```python
>>> def f(a, *, b):
...     return a, b
...
>>> f(1, b=2)
(1, 2)
>>> f(1, 2)
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
TypeError: f() takes 1 positional argument but 2 were given
```

## What is decorator?

```python
>>> def deco(func):
...     def inner():
...         print('running inner()')
...     return inner  1
...
>>> @deco
... def target():  2
...     print('running target()')
...
>>> target()  3
running inner()
>>> target  4
<function deco.<locals>.inner at 0x10063b598>
```

## How decorator works?
1. It is based on closure
2. @decorate === decorate(function)
3. Stacked Decorators (when multiple decorators are there)
4. There are decorator factory that returns decorator

## How python variable lookup logic works? - Variable Lookup Logic

1. If there is a global x declaration, x comes from and is assigned to the x global variable module.4
1. If there is a nonlocal x declaration, x comes from and is assigned to the x local variable of the nearest surrounding function where x is defined.
1. If x is a parameter or is assigned a value in the function body, then x is the local variable.
1. If x is referenced but is not assigned and is not a parameter:
    1. x will be looked up in the local scopes of the surrounding function bodies (nonlocal scopes).
    1. If not found in surrounding scopes, it will be read from the module global scope.
    1. If not found in the global scope, it will be read from __builtins__.__dict__.


## Sample clock decorator ?

```python
import time
import functools


def clock(func):
    @functools.wraps(func)
    def clocked(*args, **kwargs):
        t0 = time.perf_counter()
        result = func(*args, **kwargs)
        elapsed = time.perf_counter() - t0
        name = func.__name__
        arg_lst = [repr(arg) for arg in args]
        arg_lst.extend(f'{k}={v!r}' for k, v in kwargs.items())
        arg_str = ', '.join(arg_lst)
        print(f'[{elapsed:0.8f}s] {name}({arg_str}) -> {result!r}')
        return result
    return clocked
```


## What are all the use-cases of decorator

1. logging
2. performance timing
3. registering function to a registry
4. @lrucache / caching
5. btw, decorators are stacked

## What is SingleDispatch

```python
r"""
htmlize(): generic function example

# tag::HTMLIZE_DEMO[]

>>> htmlize({1, 2, 3})  # <1>
'<pre>{1, 2, 3}</pre>'
>>> htmlize(abs)
'<pre>&lt;built-in function abs&gt;</pre>'
>>> htmlize('Heimlich & Co.\n- a game')  # <2>
'<p>Heimlich &amp; Co.<br/>\n- a game</p>'
>>> htmlize(42)  # <3>
'<pre>42 (0x2a)</pre>'
>>> print(htmlize(['alpha', 66, {3, 2, 1}]))  # <4>
<ul>
<li><p>alpha</p></li>
<li><pre>66 (0x42)</pre></li>
<li><pre>{1, 2, 3}</pre></li>
</ul>
>>> htmlize(True)  # <5>
'<pre>True</pre>'
>>> htmlize(fractions.Fraction(2, 3))  # <6>
'<pre>2/3</pre>'
>>> htmlize(2/3)   # <7>
'<pre>0.6666666666666666 (2/3)</pre>'
>>> htmlize(decimal.Decimal('0.02380952'))
'<pre>0.02380952 (1/42)</pre>'

# end::HTMLIZE_DEMO[]
"""

# tag::HTMLIZE[]

from functools import singledispatch
from collections import abc
import fractions
import decimal
import html
import numbers

@singledispatch  # <1>
def htmlize(obj: object) -> str:
    content = html.escape(repr(obj))
    return f'<pre>{content}</pre>'

@htmlize.register  # <2>
def _(text: str) -> str:  # <3>
    content = html.escape(text).replace('\n', '<br/>\n')
    return f'<p>{content}</p>'

@htmlize.register  # <4>
def _(seq: abc.Sequence) -> str:
    inner = '</li>\n<li>'.join(htmlize(item) for item in seq)
    return '<ul>\n<li>' + inner + '</li>\n</ul>'

@htmlize.register  # <5>
def _(n: numbers.Integral) -> str:
    return f'<pre>{n} (0x{n:x})</pre>'

@htmlize.register  # <6>
def _(n: bool) -> str:
    return f'<pre>{n}</pre>'

@htmlize.register(fractions.Fraction)  # <7>
def _(x) -> str:
    frac = fractions.Fraction(x)
    return f'<pre>{frac.numerator}/{frac.denominator}</pre>'

@htmlize.register(decimal.Decimal)  # <8>
@htmlize.register(float)
def _(x) -> str:
    frac = fractions.Fraction(x).limit_denominator()
    return f'<pre>{x} ({frac.numerator}/{frac.denominator})</pre>'

# end::HTMLIZE[]
```

## Using Sql-Lite insert-emp, update_pay, get_emps_by-name?

```python
import sqlite3
from employee import Employee

conn = sqlite3.connect(':memory:')

c = conn.cursor()

c.execute("""CREATE TABLE employees (
            first text,
            last text,
            pay integer
            )""")


def insert_emp(emp):
    with conn:
        c.execute("INSERT INTO employees VALUES (:first, :last, :pay)", {'first': emp.first, 'last': emp.last, 'pay': emp.pay})


def get_emps_by_name(lastname):
    c.execute("SELECT * FROM employees WHERE last=:last", {'last': lastname})
    return c.fetchall()


def update_pay(emp, pay):
    with conn:
        c.execute("""UPDATE employees SET pay = :pay
                    WHERE first = :first AND last = :last""",
                  {'first': emp.first, 'last': emp.last, 'pay': pay})


def remove_emp(emp):
    with conn:
        c.execute("DELETE from employees WHERE first = :first AND last = :last",
                  {'first': emp.first, 'last': emp.last})

emp_1 = Employee('John', 'Doe', 80000)
emp_2 = Employee('Jane', 'Doe', 90000)

insert_emp(emp_1)
insert_emp(emp_2)

emps = get_emps_by_name('Doe')
print(emps)

update_pay(emp_2, 95000)
remove_emp(emp_1)

emps = get_emps_by_name('Doe')
print(emps)

conn.close()
```

## Why tuple is faster than list

* [Why tuple is faster than list](https://stackoverflow.com/questions/3340539/why-is-tuple-faster-than-list-in-python)
* Tuple is constant, hence holds data into stack
* List has memory in stack and heap

## What does tuple(tuple) would return?

* Since tuple is a immutable, tuple function would return without cloning tuple argument

## What are all the other Python questions
1.  __init__ vs __new__
1. Difference between list and set
1. Abstract classes use
1. Generators vs iterators
1. How yield works
1. Is python call by ref/val or both. How to implement each
1. How python list allocates space, how it is implemented
1. what is cpython, pypy

## Generate ANKI
* mdanki FluentPython_Anki.md FluentPython_Anki.md.apkg --deck "Mohan::Python::FluentPython_Anki"
