#Ksawery Plis
#Lista 1 Zadanie 3

def tabliczka (x1, x2, y1, y2):
    
    print("\t", end="")
    for i in range(x1, x2+1):
        print(f"{i :>3}", end="\t")
    print()
    
    for y in range(y1, y2+1):
        print(f"{y :>3}", end="\t")
        for x in range(x1,x2+1):
            print (f"{x*y :>3}", end="\t")
        print()

tabliczka(3, 5, 2, 4)



