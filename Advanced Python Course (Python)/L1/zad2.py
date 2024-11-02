#Ksawery Plis
#Lista 1 Zadanie 2

import string

def is_palindrom(text):
	text = (text.translate((str.maketrans('', '', string.punctuation + " ")))).lower() #usuwanie znaków przestankowych, spacji i zmienianie liter na małe
	
	for i in range(int(len(text)/2)):
		if(text[i]!=text[len(text)-i-1]):
			return False
	
	return True
	
print(is_palindrom("kakao"))
print(is_palindrom("Kobyła ma mały bok."))
print(is_palindrom("Eine güldne, gute Tugend: Lüge nie!"))
print(is_palindrom("Míč omočím."))

#Działa dla tekstów obcojęzycznych!
