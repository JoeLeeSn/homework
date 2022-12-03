# 1. 创建一个文件，并写入当前日期

import datetime

file1 = open('a.txt', 'w')
file1.write(str(datetime.date.today()))
file1.close()
