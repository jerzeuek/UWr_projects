#Ksawery Plis
#Lista 1 Zadanie 1

import decimal as d

def vat_faktura(lista):
	calosc = 0
	for i in range(len(lista)):
		calosc+=lista[i]
	return(calosc*0.23)
	
def vat_paragon(lista):
	calosc = 0
	for i in range(len(lista)):
		calosc+=(0.23*lista[i])
	return(calosc)

def vat_faktura_dec(lista):
	calosc = 0
	for i in range(len(lista)):
		calosc+=lista[i]
	return(calosc*d.Decimal('0.23'))
	
def vat_paragon_dec(lista):
	calosc = 0
	for i in range(len(lista)):
		calosc+=(d.Decimal('0.23')*lista[i])
	return(calosc)

zakupy = [0.2, 0.5, 4.59, 6]
print(vat_faktura(zakupy) == vat_paragon(zakupy))

zakupy = [d.Decimal('0.2'), d.Decimal('0.5'), d.Decimal('4.59'), d.Decimal('6')]
print(vat_faktura_dec(zakupy) == vat_paragon_dec(zakupy))

#Z modułem Decimal wyniki są takie same, bez modułu wyniki różnią się.		