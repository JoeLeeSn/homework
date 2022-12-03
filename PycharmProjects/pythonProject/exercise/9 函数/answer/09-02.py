# 2. 创建一个函数，传入n个整数，返回其中最大的数和最小的数

def find_min_max(*nums):
    print(max(list(nums)))
    print(min(list(nums)))


find_min_max(1, 5, 8, 32, 654, 765, 4, 6, 7)
