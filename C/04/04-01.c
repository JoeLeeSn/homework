
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main() {
    printf("%d\n", rand() % 1000); // ��Զ����̶�ֵ
    srand(time(0));
    printf("%d\n", rand() % 1000); // ÿ�����ж���ͬ
    return 0;
} 
