# 3. 创建一个函数，传入一个参数n，返回n的阶乘

def n_jiecheng(n):
    i = n
    m = n
    while i > 1:
        i -= 1
        m *= i

    print(m)


n_jiecheng(4)
