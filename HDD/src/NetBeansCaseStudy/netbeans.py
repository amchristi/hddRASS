'''
LDTP v2 gedit example

@author: Eitan Isaacson <eitan@ascender.com>
@copyright: Copyright (c) 2009 Eitan Isaacson
@license: LGPL

http://ldtp.freedesktop.org

This file may be distributed and/or modified under the terms of the GNU Lesser General
Public License version 2 as published by the Free Software Foundation. This file
is distributed without any warranty; without even the implied warranty of 
merchantability or fitness for a particular purpose.

See "COPYING" in the source distribution for more information.

Headers in this file shall remain intact.
'''

from ldtp import *

from time import sleep
from ldtputils import *
from random import *
from words import *
from complex import *

words = []
copyPasteTexts = []

words.append('one')
words.append('two')
words.append('three')

copyPasteTexts.append('abcdefghijklmnopqrstuvwxyz')
copyPasteTexts.append('abcdefghinklmnopqrstuvwxyz<return>abcdefghijklmnopqrstuvwxyz')


def writeSingleCharacter(ch) :
	enterstring('  <ALT><TAB> ' + ch + '  <ALT><TAB>')

def writeSingleWord(word) :
	enterstring('  <ALT><TAB>' + '  ' + word + '   ' +  '  <ALT><TAB>')

def writeCombinedWord(word1, word2):
	enterstring('  <ALT><TAB>' + '  ' + word1 + word2 + '  ' +'  <ALT><TAB>') 

def writeSingleLine(line):
	enterstring('  <ALT><TAB>' + '  ' + line +  '  ' + '  <return>' + '  ' +'  <ALT><TAB>')

def writeline():
	enterstring('  <ALT><TAB>' + '  ' + '  <return>    ' + '  '+ '  <ALT><TAB>')

def undo():
	enterstring('  <ALT><TAB>' + '  ' + '   <CTRL>y    ' + '  ' + '  <ALT><TAB>')

def redo():
	enterstring('  <ALT><TAB>' + '  ' + '   <CTRL>y    ' + '  ' + '  <ALT><TAB>')


def delay():
	time.sleep(1)

def delay3():
	time.sleep(3)


# one pass shoudl contain 100 simple word and 10 complex lines


for i in range(0, 5):
	delay()
	seed(3000)

	tenwords = GiveMeTenWords()
	delay()
	writeSingleLine(tenwords)
	tenwords = GiveMeTenWords()
	delay()
	writeSingleLine(tenwords)
	tenwords = GiveMeTenWords()
	delay()
	writeSingleLine(tenwords)
	tenwords = GiveMeTenWords()
	delay()
	writeSingleLine(GiveMeSingleRandomWComplexWord())
	delay3()
	writeline()
	delay()
	writeSingleLine(GiveMeSingleRandomWComplexWord())
	delay3()
	undo()
	writeSingleLine(GiveMeSingleRandomWComplexWord())
	delay()
	writeline()
	delay()
	writeSingleLine(GiveMeSingleRandomWComplexWord())
	delay3()
	redo()
	delay()
	writeSingleLine(tenwords)
	tenwords = GiveMeTenWords()
	delay()
	writeSingleLine(tenwords)
	writeline()
	delay()
	writeSingleLine(tenwords)
	writeline()
	delay()
	writeSingleLine(GiveMeSingleRandomWComplexWord())
	delay3()
	writeline()
	delay()
	writeSingleLine(GiveMeSingleRandomWComplexWord())
	delay3()
	undo()
	writeSingleLine(GiveMeSingleRandomWComplexWord())
	delay()
	writeline()
	delay()
	writeSingleLine(GiveMeSingleRandomWComplexWord())
	delay3()
	redo()
	delay()
	writeline()
	delay()
	writeSingleLine(tenwords)
	writeline()
	delay()
	writeSingleLine(tenwords)
	writeline()
	delay()
	writeSingleLine(GiveMeSingleRandomWComplexWord())
	delay3()
	writeline()
	delay()
	writeSingleLine(GiveMeSingleRandomWComplexWord())
	delay3()
	undo()
	writeSingleLine(GiveMeSingleRandomWComplexWord())
	delay()
	writeline()
	delay()
	writeSingleLine(GiveMeSingleRandomWComplexWord())
	delay3()
	redo()
	delay()
	delay()
	writeSingleLine(tenwords)
	tenwords = GiveMeTenWords()
	delay()
	writeSingleLine(tenwords)
	writeline()
	delay()
	writeSingleLine(tenwords)
	writeline()
	delay()
