#include <iostream>
using namespace std;

int main(void)
{
    int n, k;
    do 
    {
        cin >> n;
    } while (n < 2 || n > 100);
    do 
    {
        cin >> k;
    } while (k < 1 || k > 100);
    int A[n];
    for (unsigned a = 0; a < n; ++a)
        do
        {
            cin >> A[a];  
        } while (A[a] < 1 || A[a] > 100);
    unsigned numberOfPairs = 0;
    for (int i = 0; i < n - 1; ++i)
        for (int j = 1 + i; j < n; ++j) 
            if ((A[i] + A[j]) % k == 0)
                numberOfPairs++;
    cout << numberOfPairs;
    return 0;
}
