# Template engine demo

## What is this project?

This is a demo of a handwritten parser for a simple template language. It demonstrates the use of an lexer and parser as well as how to use the resulting syntax tree to render the template using a context (set of parameters).

## Supported syntax

For this demo only text and if-else-end blocks are supported:

```
%if deleteAllowed%
	<button>Delete item</button>
%else%
	<button>Move item to recycle bin</button>
%end%
```

These can be nested as well:

```
%if deleteAllowed%
	%if deleteCascade%
		<button>Delete item and all dependent items</button>
	%else%
		<button>Delete item</button>
	%end%
%else%
	<button>Move item to recycle bin</button>
%end%
```

## BNF

```
<template> ::= (<ifelse> | <text>)+
<ifelse> ::= "%if " <patternname> "%" <template> "%else%" <template> "%end%"
<text> ::= (<digit> | <lowercaseletter> | <uppercaseletter> | <special> | <whitespace>)+
<patternname> ::= (<digit> | <lowercaseletter> | <uppercaseletter> | <special>)+

<digit> ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
<lowercaseletter> ::= "a" | "b" | "c" | "d" | "e" | "f" | "g" | "h" | "i" | "j" | "k" | "l" | "m" | "n" | "o" | "p" | "q" | "r" | "s" | "t" | "u" | "v" | "w" | "x" | "y" | "z"
<uppercaseletter> ::= "A" | "B" | "C" | "D" | "E" | "F" | "G" | "H" | "I" | "J" | "K" | "L" | "M" | "N" | "O" | "P" | "Q" | "R" | "S" | "T" | "U" | "V" | "W" | "X" | "Y" | "Z"
<special> ::= "_" | "-"
<whitespace> ::= " " | "\n" | "\t"
```