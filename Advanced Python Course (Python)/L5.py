# Ksawery Plis
# Lista 5 Zadanie 2

class Formula:
    def __add__(w1, w2):
        return Or(w1, w2)

    def __mul__(w1, w2):
        return And(w1, w2)

    def tautologia(self, zmienne):  # metoda zerojedynkowa
        for i in range(2**len(zmienne)):
            slownik = {}
            uklad = len(zmienne)*"0" + str(bin(i))[2:]
            for j in range(len(zmienne)):
                slownik[zmienne[j]] = bool(int(uklad[len(uklad)-j-1]))

            if (self.oblicz(slownik) == False):
                return False

        return True


class Or(Formula):
    def __init__(self, a, b):
        self.a = a
        self.b = b

    def __str__(self):
        return ("(" + str(self.a) + " v " + str(self.b) + ")")

    def oblicz(self, zmienne):
        return (self.a.oblicz(zmienne) | self.b.oblicz(zmienne))

    def uprosc(self):
        if self.a.a == False:
            return self.b
        elif self.b.a == False:
            return self.a
        else:
            return Or(self.a.uprosc(), self.b.uprosc()).uprosc()


class And(Formula):
    def __init__(self, a, b):
        self.a = a
        self.b = b

    def __str__(self):
        return ("(" + str(self.a) + " ^ " + str(self.b) + ")")

    def oblicz(self, zmienne):
        return (self.a.oblicz(zmienne) & self.b.oblicz(zmienne))

    def uprosc(self):
        if self.a.a == False or self.b.a == False:
            return Stala(False)
        else:
            return And(self.a.uprosc(), self.b.uprosc()).uprosc()


class Not(Formula):
    def __init__(self, a):
        self.a = a

    def __str__(self):
        return ("¬" + "(" + str(self.a) + ")")

    def oblicz(self, zmienne):
        return (not self.a.oblicz(zmienne))

    def uprosc(self):
        return Not(self.a)


class Stala(Formula):
    def __init__(self, a):
        self.a = a
        if not isinstance(a, bool):
            raise NoBoolean("Podana wartość nie jest typu Boolean!")

    def __str__(self):
        return str(self.a)

    def oblicz(self, zmienne):
        return self.a

    def uprosc(self):
        return Stala(self.a)


class Zmienna(Formula):
    def __init__(self, a):
        self.a = a
        if not isinstance(a, str):
            raise NoString("Nie podałeś nazwy zmiennej jako string!")

    def __str__(self):
        return self.a

    def oblicz(self, zmienne):
        try:
            return zmienne[self.a]
        except:
            raise NoValueOfVarError("Nie przypisano wartości zmiennej!")

    def uprosc(self):
        return Zmienna(self.a)


class NoValueOfVarError(Exception):
    pass


class NoBoolean(Exception):
    pass


class NoString(Exception):
    pass


test = Or(Not(Zmienna("x")), And(Zmienna("y"), Stala(True)))
print(test.oblicz({"x": True, "y": False}))

print(test)

print(Zmienna("p") + Zmienna("q"))
print(Zmienna("p") * Zmienna("q"))

taut = Or(Zmienna("p"), Not(Zmienna("p")))
print(taut.tautologia(["p", "q"]))

upr = Or(Zmienna("p"), And(Zmienna("p"), Stala(False)))
print(upr.uprosc())
