# 练习四 元组的基本操作

# 1. 定义一个任意元组，对元组使用append() 查看错误信息
mete1 = (1, 'a', 100)
print(mete1)

# 2. 访问元组中的倒数第二个元素
print(mete1[1])

# 3. 定义一个新的元组，和 1. 的元组连接成一个新的元组
mete2 = (2, 'b', 200)
mete3 = mete1 + mete2
print(mete3)

# 4. 计算元组元素个数
print(len(mete3))
print(mete3.__len__())
