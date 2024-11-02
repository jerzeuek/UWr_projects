from sympy import randprime, mod_inverse
from random import randint
from math import inf, floor, ceil, gcd

def GenModulus(w):
    n = len(w) // 2
    p = randprime(2 ** n, 2 ** (n+1))
    q = randprime(2 ** n, 2 ** (n+1))
    N = p * q
    return N, p, q


def GenRSA(w):
    N, p, q = GenModulus(w)
    m = (p-1) * (q-1)
    e = 2 ** 16 + 1
    d = mod_inverse(e, m)
    return N, e, d, p, q


def enc(x, N, e):
    return fast_pow(x, N, e)  # x ** e % N


def dec(c, N, d):
    return fast_pow(c, N, d)  # c ** d % N


def fast_pow(c, N, d):
    d_bin = "{0:b}".format(d)
    d_len = len(d_bin)
    reductions = 0
    h = 0
    x = c
    for j in range(1, d_len):
        x, r = mod_reduce(x ** 2, N)
        reductions = reductions + r
        if d_bin[j] == "1":
            x, r = mod_reduce(x * c, N)
            reductions = reductions + r
            h = h + 1
    return x, h, reductions


def mod_reduce(a, b):
    reductions = 0
    if a >= b:
        a = a % b
        reductions = 1
    return a, reductions

# ------------------------------------------------------------------------

N, e, d, p, q = GenRSA("test"*75)
print(N, e, d, p, q)
num_samples = 1000

def x_y_generate(N,i):
    x = randint(ceil(N ** (1/(i+2))), floor(N ** (1/(i+1)))) # x^3 < N
    y = randint(ceil(N ** (1/(i+1))), floor(N ** (1/i))) # y^2 < N <= y^3
    return x, y

def guess(N, e, d, num_samples):
    try:
        p = 2
        bits = "1"
        for _ in range(num_samples):
            ones = 0
            r = 100
            for _ in range(r):
                x, y = x_y_generate(N, p)
                _, _, reductions_0 = dec(x, N, d)
                _, _, reductions_1 = dec(y, N, d)

                if reductions_1 > reductions_0:
                    ones+=1

            if ones/r >= 0.6:
                bits+="1"
                p+=1
            else:
                bits+="0"
            p*=2
    except ValueError as o:
        print(o)
    return bits

data = guess(N, e, d, num_samples)
print("Guessed starting bits:", data)
binary = bin(d)[2:len(data)+2]
print(f"Real starting bits: {binary}")