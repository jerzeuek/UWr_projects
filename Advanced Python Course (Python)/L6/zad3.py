# Ksawery Plis
# Lista 6 Zadanie 3

import re
import requests
from bs4 import BeautifulSoup


def index(URL, words, depth):
    global ind
    ind = {}

    for word in words:
        ind[word] = []

    global used_urls
    used_urls = []
    szukaj(URL, words, depth)

    for key in ind:
        lst = ind[key]
        lst = sorted(lst, key=lambda x: x[1], reverse=True)

        for i in range(len(lst)):
            lst[i] = lst[i][0]
        ind[key] = lst

    return ind


def szukaj(URL, words, depth):
    if (URL in used_urls):
        return

    used_urls.append(URL)
    page = requests.get(URL)
    soup = BeautifulSoup(page.content, 'html.parser')

    x = [word.lower() for word in soup.get_text().split()]

    for word in words:
        if x.count(word.lower()) > 0:
            ind[word] += [(URL, x.count(word.lower()))]

    if (depth <= 1):
        return

    for link in soup.findAll('a', attrs={'href': re.compile("^http://")}):
        szukaj(link["href"], words, depth-1)


print(index('https://ii.uni.wroc.pl/',
      ['Instytut', 'Informatyka', 'Dziekanat', 'Uniwersytet', 'Puste'], 2))
