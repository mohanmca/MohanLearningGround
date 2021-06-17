numbers = [i for i in range(100)]
oddnumbers = [odd for odd in numbers if odd%2!=0]
result = [str(s) for s in oddnumbers if s%10==1 or '1' in  str(s) ]
print(len(result))
print(result)