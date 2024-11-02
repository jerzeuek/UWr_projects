# Ksawery Plis
# Lista 2 Zadanie 2

def pierwiastek(n):
    suma = 0
    for i in range(n):
        suma += (2*i-1)
        if (suma >= n):
            return (i-1)


print(pierwiastek(42))
