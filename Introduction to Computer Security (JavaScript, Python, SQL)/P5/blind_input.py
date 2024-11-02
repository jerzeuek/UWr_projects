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


def dec(c, N, d, e):
    r = randint(1, N-1)  # Generate a random blinding factor
    while gcd(r, N) != 1:
        r = randint(1, N-1)
    
    r_e = fast_pow(r, N, e)[0]  # r ** e % N
    blinded_c = (c * r_e) % N   # Blinded ciphertext

    blinded_m, h, inversions = fast_pow(blinded_c, N, d)  # Decrypt the blinded ciphertext

    r_inv = mod_inverse(r, N)  # Inverse of the blinding factor
    m = (blinded_m * r_inv) % N  # Remove the blinding factor

    return m, h, inversions

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

def ran(N,i):
    x = randint(ceil(N ** (1/(i+2))), floor(N ** (1/(i+1)))) # x^3 < N
    y = randint(ceil(N ** (1/(i+1))), floor(N ** (1/i))) # y^2 < N <= y^3
    return x, y

N, e, d, p, q = GenRSA("example"*80)
print(N, e, d, p, q)
num_samples = 100

def collect_data(N, e, d, num_samples):
    try:
        p = 2
        wynik = "1"
        for _ in range(num_samples):
            pain = 0
            r = 10
            for _ in range(r):
                x, y = ran(N, p)
                _, _, reductions_dec0 = dec(x, N, d, e)
                _, _, reductions_dec1 = dec(y, N, d, e)

                if reductions_dec1 > reductions_dec0:
                    pain+=1

            if pain/r >= 0.6:
                wynik+="1"
                p+=1
            else:
                wynik+="0"
            p*=2
    except ValueError as o:
        print(o)
    return wynik

data = collect_data(N, e, d, num_samples)
print("Estimated starting bits of d:", data)
binary = bin(d)[2:len(data)+2]
print(f"Real starting bits of d:      {binary}")