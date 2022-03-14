#!/usr/bin/env python

import sys

def main():
    current_phone = None
    current_up = 0
    current_down = 0
    current_sum = 0

    phone_num = None

    for line in sys.stdin:
        line = line.strip()
        phone_num,up,down = line.split('\t')

        try:
            up = int(up)
            down = int(down)
            total = up + down
        except ValueError:
            continue

        if current_phone == phone_num:
            current_up += up
            current_down += down
            current_sum += total
        else:
            if current_phone:
                print '%s\t%s\t%s\t%s' % (current_phone, current_up, current_down, current_sum)

            current_up = up
            current_down = down
            current_sum = total
            current_phone = phone_num

    if current_phone == phone_num:
        print '%s\t%s\t%s\t%s' % (current_phone, current_up, current_down, current_sum)


if __name__ == "__main__":
    main()
