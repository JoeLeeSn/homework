#!/usr/bin/env python

from itertools import groupby
from operator import itemgetter
import sys

def read_mapper_output(file, separator='\t'):
	for line in file:
		#yield line.rstrip().split(separator)
		yield line.split()

def main(separator='\t'):
	data = read_mapper_output(sys.stdin, separator=separator)
	for current_word, group in groupby(data, itemgetter(0)):
                print current_word
                print group
		try:
			total_count = sum(int(count) for current_word, count in group)
			print "%s%s%d" % (current_word, separator, total_count)
		except ValueError:
			print "%s" % ("you lost!")
			pass

if __name__ == "__main__":
	main()
