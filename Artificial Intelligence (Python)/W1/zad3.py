# Losujemy karty dla obu graczy, po czym sprawdzamy który ma wyższy układ

# Układy, ich możliwi posiadacze i numeracja w kodzie:
# królewski poker (NIKT, bo Figurant nie ma 10 a Blotkarz nie ma figur)
# poker (B) -  9
# kareta (F, B) - 7
# ful (F, B) - 6
# kolor (F, B) - 5
# strit (B) - 4
# trójka (F, B) - 3
# dwie pary (F, B) - 2
# para (F, B) - 1
# wysoka karta (B, ale nie ma sensu liczyć bo Figurant ZAWSZE ma co najmniej parę) 

# Dla oryginalnej talii Blotkarz wygrywa w ok. 8% gier
# Dla talii z 8, 9 i 10 każdego koloru wygrywa w >=50% gier


import random

def lista_wartosci(talia):
    return [karta[0] for karta in talia]

def lista_kolorow(talia):
    return [karta[1] for karta in talia]

def poker_strit_kolor(talia):
    wartosci = lista_wartosci(talia)
    kolory = lista_kolorow(talia)
    return kolor(kolory) + strit(wartosci)

def kolor(kolory):
    if (len(set(kolory)) == 1):
        return 5
    return 0

def strit(wartosci):
    wartosci.sort()
    for i in range(1, 5):
        if(wartosci[i]-1 != wartosci[i-1]):
            return 0
    return 4

def kareta_ful_para_dwiepary(talia):
    ilewartosci = [0] * 13
    for i in range(5):
        ilewartosci[talia[i][0]-2] +=1

    if(4 in ilewartosci):
        return 7
    
    if(3 in ilewartosci):
        if(2 in ilewartosci):
            return 6
        return 3
    
    if(2 in ilewartosci):  
        if(ilewartosci.count(2) == 2):
            return 2
        return 1
    
    return 0

def losuj(wartosci):
    kolory = ["trefl", "karo", "kier", "pik"]
    karty = [(wartosc, color) for wartosc in wartosci for color in kolory]
    i = 0
    talia = []
    
    while(i<5):
        karta = random.choice(karty)
        if(karta not in talia):
            talia.append(karta)
            i+=1
    return talia

if __name__ == "__main__":
    figury = [11, 12, 13, 14]
    blotki = [2, 3, 4, 5, 6, 7, 8, 9, 10]

    ilosc_gier = 100000
    wygrane_blotkarza = 0

    for i in range(ilosc_gier):
        talia_f = losuj(figury)
        talia_b = losuj(blotki)
        wynik_f = max(poker_strit_kolor(talia_f), kareta_ful_para_dwiepary(talia_f))
        wynik_b = max(poker_strit_kolor(talia_b), kareta_ful_para_dwiepary(talia_b))

        if(wynik_f < wynik_b):
            wygrane_blotkarza+=1

    print("Blotkarz wygrał w ", (wygrane_blotkarza/ilosc_gier) * 100,  "%", " grach")