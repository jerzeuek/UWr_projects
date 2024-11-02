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

# Example usage
w = "Example input"
N, e, d, p, q = GenRSA(w)
message = 42  # Example message
ciphertext = enc(message, N, e)[0]
print(ciphertext)
decrypted_message = dec(ciphertext, N, d)[0]

print(f"Original message: {message}")
print(f"Encrypted message: {ciphertext}")
print(f"Decrypted message: {decrypted_message}")