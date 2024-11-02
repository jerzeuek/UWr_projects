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
    return fast_pow(x, N, e)[0]  # x ** e % N

def dec(c, N, d, e):
    r = randint(1, N-1)  # Generate a random blinding factor
    while gcd(r, N) != 1:
        r = randint(1, N-1)
    
    r_e = fast_pow(r, N, e)[0]  # r^e % N
    blinded_c = (c * r_e) % N   # Blinded ciphertext

    blinded_m = fast_pow(blinded_c, N, d)[0]  # Decrypt the blinded ciphertext

    r_inv = mod_inverse(r, N)  # Inverse of the blinding factor
    m = (blinded_m * r_inv) % N  # Remove the blinding factor

    return m

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
ciphertext = enc(message, N, e)
decrypted_message = dec(ciphertext, N, d, e)

print(f"Original message: {message}")
print(f"Encrypted message: {ciphertext}")
print(f"Decrypted message: {decrypted_message}")