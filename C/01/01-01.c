#include <stdio.h>

int main() {
    int x;                   // �������x
    scanf("%d", &x);
    int f = 0;               // ������� f
    for (int i = 2; i * i <= x; i++) { // ����ѭ������i     
        if (x % i) continue;
        f = 1;
        break;
    }
    if (f) printf("F\n");
    else printf("T");
    return 0;
}
