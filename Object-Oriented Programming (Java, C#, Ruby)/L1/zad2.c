// Ksawery Plis
// Lista 1 Zadanie 2
// gcc 11.2.0

#include <stdio.h>
#include <stdlib.h>

typedef struct u
{
    int licznik;
    int mianownik;
} Ulamek;

int NWD(int a, int b)
{
    if(a == 0){
        return 1;
    }

    while (a != b)
    {
        if (a > b)
        {
            a -= b;
        }
        else
        {
            b -= a;
        }
    }
    return a;
}

void simplify(Ulamek *u)
{
    if (u->mianownik < 0)
    {
        u->licznik *= -1;
        u->mianownik *= -1;
    }

    int nwd = NWD(abs(u->licznik), abs(u->mianownik));

    u->licznik /= nwd;
    u->mianownik /= nwd;
}

Ulamek *nowy_ulamek(int num, int denom)
{
    if (denom == 0)
    {
        printf("Mianownik nie może być zerem!");
        exit(-1);
    }

    Ulamek *nowy = malloc(sizeof(Ulamek));
    nowy->licznik = num;
    nowy->mianownik = denom;
    simplify(nowy);

    return nowy;
}

void show(Ulamek *u)
{
    printf("%f \n", (float)u->licznik / (float)u->mianownik);
}

void add_mod(Ulamek *a, Ulamek *b)
{
    Ulamek temp = *a;

    temp.licznik = (a->licznik * b->mianownik) + (a->mianownik * b->licznik);
    temp.mianownik = a->mianownik * b->mianownik;

    b->licznik = temp.licznik;
    b->mianownik = temp.mianownik;

    simplify(b);
}

void sub_mod(Ulamek *a, Ulamek *b)
{
    b->licznik *= -1;
    add_mod(a, b);
}

void mult_mod(Ulamek *a, Ulamek *b)
{
    b->licznik *= a->licznik;
    b->mianownik *= a->mianownik;

    simplify(b);
}

void div_mod(Ulamek *a, Ulamek *b)
{
    int temp_licznik = b->licznik;
    b->licznik = b->mianownik;
    b->mianownik = temp_licznik;

    mult_mod(a, b);
}

Ulamek *add_ptr(Ulamek a, Ulamek b)
{
    Ulamek *nowy = malloc(sizeof(Ulamek));

    nowy->licznik = (a.licznik * b.mianownik) + (a.mianownik * b.licznik);
    nowy->mianownik = a.mianownik * b.mianownik;

    simplify(nowy);
    return nowy;
}

Ulamek *sub_ptr(Ulamek a, Ulamek b)
{
    b.licznik *= -1;
    return add_ptr(a, b);
}

Ulamek *mult_ptr(Ulamek a, Ulamek b)
{
    Ulamek *nowy = malloc(sizeof(Ulamek));

    nowy->licznik = a.licznik * b.licznik;
    nowy->mianownik = a.mianownik * b.mianownik;

    simplify(nowy);

    return nowy;
}

Ulamek *div_ptr(Ulamek a, Ulamek b)
{
    Ulamek *nowy = malloc(sizeof(Ulamek));

    nowy->licznik = a.licznik * b.mianownik;
    nowy->mianownik = a.mianownik * b.licznik;

    simplify(nowy);
    return (nowy);
}

int main()
{
    Ulamek *a = nowy_ulamek(8, 16);
    Ulamek *b = nowy_ulamek(-6, -24);

    show(a);
    show(b);

    Ulamek *suma = add_ptr(*a, *b);
    show(suma);

    Ulamek *roznica = sub_ptr(*a, *b);
    show(roznica);

    Ulamek *iloczyn = mult_ptr(*a, *b);
    show(iloczyn);

    Ulamek *iloraz = div_ptr(*a, *b);
    show(iloraz);

    add_mod(a, b);
    show(b);

    sub_mod(a, b);
    show(b);

    mult_mod(a, b);
    show(b);

    div_mod(a, b);
    show(b);
    
    Ulamek *zly= nowy_ulamek(4, 0);
}