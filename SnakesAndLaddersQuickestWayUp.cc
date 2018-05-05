#include <bits/stdc++.h>

using namespace std;

const unsigned length = 100;

int quickestWayUp(vector < vector<int> > ladders, vector < vector<int> > snakes) {
    // Complete this function
    bool canReachTileIn[length][length];
    int transport[length];
    int d, r;
    for (d = 0; d < length; d++) {
        transport[d] = d;
        for (r = 0; r < length; r++)
            canReachTileIn[d][r] = 0;
    }
    const unsigned ladder_count = ladders.size();
    for (d = 0; d < ladder_count; d++)
        transport[ladders[d][0] - 1] = ladders[d][1] - 1;
    const unsigned snake_count = snakes.size();
    for (d = 0; d < snake_count; d++)
        transport[snakes[d][0] - 1] = snakes[d][1] - 1;
    int e;
    for (e = 1; e <= 6; e++) {
        if (transport[e] == length - 1)
            return 1;
        canReachTileIn[transport[e]][0] = 1;
    }
    for (r = 1; r < length; r++)
        for (d = 1; d < length; d++)
            if (canReachTileIn[d][r - 1])
                for (e = d + 1; e < length && e <= d + 6; e++) {
                    if (transport[e] == length - 1)
                        return r + 1;
                    canReachTileIn[transport[e]][r] = 1;
                }
    return -1;
}

int main() {
    int t;
    cin >> t;
    for(int a0 = 0; a0 < t; a0++){
        int n;
        cin >> n;
        vector< vector<int> > ladders(n,vector<int>(2));
        for(int ladders_i = 0;ladders_i < n;ladders_i++){
           for(int ladders_j = 0;ladders_j < 2;ladders_j++){
              cin >> ladders[ladders_i][ladders_j];
           }
        }
        int m;
        cin >> m;
        vector< vector<int> > snakes(m,vector<int>(2));
        for(int snakes_i = 0;snakes_i < m;snakes_i++){
           for(int snakes_j = 0;snakes_j < 2;snakes_j++){
              cin >> snakes[snakes_i][snakes_j];
           }
        }
        int result = quickestWayUp(ladders, snakes);
        cout << result << endl;
    }
    return 0;
}
