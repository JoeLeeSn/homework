
#include <stdio.h>

int main() {
    int y, m, d, X; // 定义存储 年月日 和 X 的变量
    scanf("%d%d%d", &y, &m, &d); // 读入年月日
    scanf("%d", &X); // 读入 X 值
    for (int i = 0; i < X; i++) { // 循环 X 次，每次向后推一天
        d += 1;
        switch (m) {
            case 1:
            case 3:
            case 5: { // 第一部分逻辑
                if (d > 31) d = 1, m += 1;
                if (m == 13) m = 1, y += 1;
            }; break;
            case 4:
            case 6: { // 第二部分逻辑 
                if (d > 30) d = 1, m += 1;
            } break;
            case 2: { // 第三部分逻辑
                if ((y % 4 == 0 && y % 100 != 0) || y % 400 == 0) {
                    if (d > 29) d = 1, m += 1;
                } else if (d > 28) {
                    d = 1, m += 1;
                }
            } break;
        }
    }
    printf("%d %d %d\n", y, m, d);
    return 0;
} 
