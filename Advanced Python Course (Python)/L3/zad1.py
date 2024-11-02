import math
import timeit

imperatywna = '''
def pierwsze_imperatywna(n):
    lista = []
    for i in range(2, n):
        if (all(i % j != 0 for j in range(2, int(math.sqrt(i))+1))):
            lista.append(i)

    return (lista)
'''

skladana = '''
def pierwsze_skladana(n):
    lista = [i for i in range(2, n) if all(
        i % j != 0 for j in range(2, int(math.sqrt(i))+1))]

    return (lista)
'''

funkcyjna = '''
def pierwsze_funkcyjna(n):
    lista = list(filter(lambda i: all(i % j != 0 for j in range(
        2, int(math.sqrt(i))+1)), list(range(2, n))))

    return (lista)
'''
print("n", "imperatywna", "skladana", "funkcyjna", sep="\t")
for i in range(100, 1000, 100):
    print(i, timeit.timeit(stmt=imperatywna, setup='import math'), timeit.timeit(
        stmt=skladana, setup='import math'), timeit.timeit(stmt=funkcyjna, setup='import math'), sep="\t")

# print(pierwsze_imperatywna(100))
# print(pierwsze_skladana(100))
# print(pierwsze_funkcyjna(100))
