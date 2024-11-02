# Ksawery Plis
# Lista 2 Zadanie 4

import random as rand
import string

with open('inwokacja.txt', encoding='utf8') as f:
    inwokacja = f.read()


def uprosc_zdanie(tekst, dl_slowa, liczba_slow):
    stare = tekst.translate(str.maketrans('', '', string.punctuation)).split()
    nowe = []
    for slowo in stare:
        if (len(slowo) <= dl_slowa):
            nowe.append(slowo)

    while (len(nowe) > liczba_slow):
        nowe.remove(nowe[rand.randint(0, len(nowe)-1)])

    return (" ".join(nowe).capitalize()+".")


print(uprosc_zdanie(inwokacja, 7, 25))
