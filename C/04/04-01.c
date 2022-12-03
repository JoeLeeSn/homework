
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main() {
    printf("%d\n", rand() % 1000); // 永远输出固定值
    srand(time(0));
    printf("%d\n", rand() % 1000); // 每次运行都不同
    return 0;
} 
