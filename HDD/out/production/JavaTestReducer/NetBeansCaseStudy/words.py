from random import *

words = []
wordcount = 4
words.append('one')
words.append('two')
words.append('theree')
words.append('four')

words.append('five')
words.append('six')
words.append('seven')
words.append('eight')

seed(10000)
wordcount = len(words)

def GiveMeRandomWords():
    return words

def GiveMeSingleRandomWord():
    x = randint(0,wordcount -1 )
    return words[x]

def GiveMeTenWords():
    word = ''
    for i in range(0,10):
        x = randint(0, wordcount - 1)
        word = word + ' ' + words[x]

    return word

