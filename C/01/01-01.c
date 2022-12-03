#include <stdio.h>

int main() {
    int x;                   // 定义变量x
    scanf("%d", &x);
    int f = 0;               // 定义变量 f
    for (int i = 2; i * i <= x; i++) { // 定义循环变量i     
        if (x % i) continue;
        f = 1;
        break;
    }
    if (f) printf("F\n");
    else printf("T");
    return 0;
}
