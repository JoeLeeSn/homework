#!/usr/bin/env python

import sys

def read_input(file):
	for line in file:
		yield line.split()

def main(separator='\t'):
	data = read_input(sys.stdin)
	for words in data:
#		for word in words:
			print '%s%s%d%s%d' % (words[1], separator, int(words[7]), separator, int(words[8]))


if __name__=="__main__":
	main()
