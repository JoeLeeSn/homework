# 2. 通过Python程序产生IndexError，并用try捕获异常处理

try:
    a = ('1', '2', '4')
    print(a[4])
except IndexError as e:
    print('通过Python程序产生IndexError，并用try捕获异常处理')
    print(e)
