import math


def doskonale_imperatywna(n):
    lista = []
    for i in range(2, n):
        suma = 0
        for j in range(1, i):
            if (i % j == 0):
                suma += j

        if (suma == i):
            lista.append(i)

    return (lista)

def doskonale_skladane(n):
    lista = [i for i in range(2, n) if suma == i ]


print(doskonale_imperatywna(10000))
