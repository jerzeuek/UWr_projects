// Ksawery Plis
// Lista 1 Zadanie 4
// gcc 11.2.0

#include <stdio.h>

void tabliczka(float x1, float x2, float y1, float y2, float skok){
    float max_value = x2 * y2;

    int width = 3; // przecinek i dwa miejsca po przecinku
    while((int) max_value > 0){
        max_value/=10;
        width++;
    }

    printf("%*s", width+2, "");
    for(float i = y1; i<y2; i+=skok){
        printf("%*.2f ",width, i);
    }

    printf("\n");

    for(float i = x1; i < x2; i+=skok){
        printf("%*.2f: ", width, i);
        for (float j = y1; j <= y2; j+=skok){
            printf("%*.2f ", width, i * j);
        }

        printf("\n");
    }
}

int main(){
    tabliczka(0.2, 1.3, 0.2, 3.14, 0.3);
}
