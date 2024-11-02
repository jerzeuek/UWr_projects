import hashlib
from sympy import mod_inverse, randprime, gcd
from random import randint

def generate_keypair(bits):
    p = randprime(2**(bits//2), 2**(bits//2 + 1))
    q = randprime(2**(bits//2), 2**(bits//2 + 1))
    n = p * q
    phi = (p - 1) * (q - 1)
    
    e = 65537
    d = mod_inverse(e, phi)
    
    return ((n, e), (n, d))

def blind(message, n):
    r = randint(2, 100)
    while True:
        if r < n and gcd(r, n) == 1:
            break
        r += 1
    blind_message = (message * pow(r, 65537, n)) % n
    return blind_message, r

def unblind(signature, r, n):
    return (signature * mod_inverse(r, n)) % n

def sign(blind_message, private_key):
    n, d = private_key
    signature = pow(blind_message, d, n)
    return signature

def verify(message, signature, public_key):
    n, e = public_key
    hash_value = int.from_bytes(hashlib.sha256(message.encode()).digest(), byteorder='big')
    decrypted_hash = pow(signature, e, n)
    return decrypted_hash == hash_value

# ------------------------------------------------

message = "Hello, world!"
bits = 1024
public_key, private_key = generate_keypair(bits)
blind_message, r = blind(int.from_bytes(hashlib.sha256(message.encode()).digest(), byteorder='big'), public_key[0])
signature = sign(blind_message, private_key)
unblinded_signature = unblind(signature, r, public_key[0])
verification_result = verify(message, unblinded_signature, public_key)

print("Message:", message)
print("Signature:", unblinded_signature)
print("Verification Result:", verification_result)