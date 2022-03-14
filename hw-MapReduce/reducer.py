#!/usr/bin/env python

from itertools import groupby
from operator import itemgetter
import sys

def read_mapper_output(file, separator='\t'):
	for line in file:
		yield line.rstrip().split(separator)

def main(separator='\t'):
	data = read_mapper_output(sys.stdin, separator=separator)
        print data
	for phone_num,group in groupby(data, itemgetter(0)):
                print group
		try:
			total_up = sum(int(count) for phone_num, group[1] in group)
			total_down = sum(int(count) for phone_num, group[2] in group)
                        total_sum = total_up + total_down
			print "%s%s%d%s%d%s%d" % (phone_num, separator, total_up, separator, total_down, separator, total_sum)
		except ValueError:
			print ValueError
			pass

if __name__ == "__main__":
	main()
