## What is del in python? what is del(x)

1. del is keyword and statement in python
2. del removes reference (doesn't delete object)
3. if reference was the last one, it might leads to invoke __del__ in python
4. in python x and (x) are same
   5. del(x) == del x
   6. Hence it is nothing special

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
3. ```python
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

## Generate ANKI
* mdanki FluentPython_Anki.md FluentPython_Anki.md.apkg --deck "Mohan::Python::FluentPython_Anki"