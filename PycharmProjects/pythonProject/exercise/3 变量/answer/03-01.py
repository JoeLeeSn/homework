# 练习一 变量的定义和使用

# 1. 定义两个变量分别为美元和汇率
doller = 100
ratio = 6.7

# 2. 通过搜索引擎找到美元兑人民币汇率

# 3. 使用Python计算100美元兑换的人民币数量并用print( )进行输出
RMB = doller / ratio
print(RMB)
print('{dol}美元兑换的人民币数量为{yuan}'.format(dol=doller, yuan=doller / ratio))
