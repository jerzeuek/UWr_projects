# Ksawery Plis
# Lista 2 Zadanie 5

with open('inwokacja.txt', encoding='utf8') as f:
    inwokacja = f.read().replace(" ", "").replace('\n', "")


def kompresja(tekst):
    litery = []
    znak = tekst[0]
    dl = 1
    tekst += " "
    for i in range(1, len(tekst)):
        if (znak != tekst[i]):
            litery.append((dl, znak))
            znak = tekst[i]
            dl = 1

        else:
            dl += 1

    return litery


def dekompresja(tekst_skompresowany):
    decrypt = ""
    for krotka in tekst_skompresowany:
        decrypt += (krotka[0]*krotka[1])

    return decrypt

print(inwokacja == dekompresja(kompresja(inwokacja)))
