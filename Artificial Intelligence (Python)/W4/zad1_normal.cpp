#pragma GCC optimize("Ofast")
#pragma GCC target("avx2,bmi,bmi2,lzcnt,popcnt,fma")

#include <cassert>
#include <cctype>
#include <cerrno>
#include <cfloat>
#include <ciso646>
#include <climits>
#include <clocale>
#include <cmath>
#include <csetjmp>
#include <csignal>
#include <cstdarg>
#include <cstddef>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <ctime>
#include <ccomplex>
#include <cfenv>
#include <cinttypes>
#include <cstdalign>
#include <cstdbool>
#include <cstdint>
#include <ctgmath>
#include <cwchar>
#include <cwctype>
#include <algorithm>
#include <bitset>
#include <complex>
#include <deque>
#include <exception>
#include <fstream>
#include <functional>
#include <iomanip>
#include <ios>
#include <iosfwd>
#include <iostream>
#include <istream>
#include <iterator>
#include <limits>
#include <list>
#include <locale>
#include <map>
#include <memory>
#include <new>
#include <numeric>
#include <ostream>
#include <queue>
#include <set>
#include <sstream>
#include <stack>
#include <stdexcept>
#include <streambuf>
#include <string>
#include <typeinfo>
#include <utility>
#include <valarray>
#include <vector>
#include <array>
#include <atomic>
#include <chrono>
#include <condition_variable>
#include <forward_list>
#include <future>
#include <initializer_list>
#include <mutex>
#include <random>
#include <ratio>
#include <regex>
#include <scoped_allocator>
#include <system_error>
#include <thread>
#include <tuple>
#include <typeindex>
#include <type_traits>
#include <unordered_map>
#include <unordered_set>

using namespace std;

//! ---- PROGRAM SETTINGS ----
int DEPTH = 6;                      // how many moves ahead AI (minimax and alpha beta) will look thru
#define wanna_go_faster
// #define measure_time
// const int PREPRINT = 10;
// #define enable_sorting
// #define SORTING_TRESHOLD 10         // after how many moves sorting is disabled
//! --------------------------

using Clock = std::chrono::high_resolution_clock;

/*
black player starts
starting sheme:
...WB...
...BW...
*/

class Reversi{
    const int DIRS[8][2] = {{-1, -1}, {-1, 0}, {-1, 1}, {0, 1},
                            {1, 1},   {1, 0},  {1, -1}, {0, -1}};

    const vector<vector<int>> CELL_WEIGHTS = {{20, -3, 11,  8,  8,  11, -3,  20},
                                              {-3, -7, -4,  1,  1, -4,  -7, -3},
                                              {11, -4,  2,  2,  2,  2,  -4 , 11},
                                              {8,   1,  2, -3, -3,  2,   1,  8},
                                              {8,   1,  2, -3, -3,  2,   1,  8},
                                              {11, -4,  2,  2,  2,  2,  -4,  11},
                                              {-3, -7, -4,  1,  1, -4,  -7, -3},
                                              {20, -3, 11,  8,  8,  11, -3,  20}};

    const int CORNERS[4][2] = {{0, 0}, {0, 7}, {7, 0}, {7, 7}};

public:
    bool player[8][8];          // active player player's pieces
    bool opponent[8][8];        // opponent's pieces

    Reversi(){}
    Reversi(bool black_starts){
        reset(black_starts);
    }

    void reset(bool black_starts){
        memset(player, 0, sizeof(player));
        memset(opponent, 0, sizeof(opponent));
        opponent[3][3] = opponent[4][4] = true;
        player[3][4] = player[4][3] = true;   
        if(!black_starts) swap_players();
    }

    /* #region //* setters getters */
    inline void set_player_cell(int x, int y, bool val){
        if(safe(x, y))
        player[x][y] = val;
    }
    inline bool get_player_cell(int x, int y) const {
        return player[x][y];
    }
    inline void set_opponent_cell(int x, int y, bool val){
        if(safe(x, y))
        opponent[x][y] = val;
    }
    inline bool get_opponent_cell(int x, int y) const {
        return opponent[x][y];
    }
    /* #endregion */

    void swap_players(){
        swap(player, opponent);
    }

    void make_move(int x, int y){
        set_player_cell(x, y, true);
        for(auto [dx, dy] : DIRS){
            if(can_beat(x, y, {dx, dy})){
                int tx = x + dx, ty = y + dy;
                while(get_opponent_cell(tx, ty)){
                    set_opponent_cell(tx, ty, false);
                    set_player_cell(tx, ty, true);
                    tx += dx;
                    ty += dy;
                }
            }
        }
    }

    inline bool safe(int x, int y) const {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    } 

    bool can_beat(int x, int y, pair<int, int> dir) const {
        int dx = dir.first, dy = dir.second;
        x += dx, y += dy;

        if(!safe(x, y) || !get_opponent_cell(x, y)) return false;
        while(safe(x, y) && get_opponent_cell(x, y)){
            x += dx;
            y += dy;
        }

        if(safe(x, y) && get_player_cell(x, y)) return true;
        return false;
    }

    vector<pair<int, int>> free_cells() const {
        // long long occupied_cells = (player | opponent);
        vector<pair<int, int>> res;

        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 8; y++){
                if(get_player_cell(x, y) || get_opponent_cell(x, y)) continue;
                for(auto [dx, dy] : DIRS){
                    if(can_beat(x, y, {dx, dy})){
                        res.push_back({x, y});
                        break;
                    }
                }
            }
        }

        return res;
    }

    int result() const {
        int res = 0;
        for(int i = 0; i < 8; i++) for(int j = 0; j < 8; j++) {
            if(get_player_cell(i, j)) res++;
            else if(get_opponent_cell(i, j)) res--;
        }

        return res;
    }

    inline int calc_ratio(int player, int opponent) const {
        return 100 * ((double)(player - opponent)) / (player + opponent);
    }

    int heuristic_result() const {
        int weighted_sum = 0;
        int player_cells = 0;
        int opponent_cells = 0;
        for(int i = 0; i < 8; i++) for(int j = 0; j < 8; j++) {
            if(get_player_cell(i, j)){
                weighted_sum += CELL_WEIGHTS[i][j];
                player_cells++;
            }
            else if(get_opponent_cell(i, j)){
                weighted_sum -= CELL_WEIGHTS[i][j];
                opponent_cells++;
            }
        }

        int ratio = calc_ratio(player_cells, opponent_cells);


        int player_corners = 0;
        int opponent_corners = 0;
        for(auto [x, y] : CORNERS){
            if(get_player_cell(x, y)) player_corners++;
            else if(get_opponent_cell(x, y)) opponent_corners++;
        }

        int corner_ratio = 0;
        if(player_corners + opponent_corners)
            corner_ratio = calc_ratio(player_corners, opponent_corners);


        int player_close_corners = 0;
        int opponent_close_corners = 0;
        for(auto [x, y] : CORNERS){
            if(not (get_player_cell(x, y) || get_opponent_cell(x, y))){
                for(auto [dx, dy] : DIRS){      // could be more efficient, but quite uglier
                    int tx = x + dx, ty = y + dy;
                    if(not safe(tx, ty)) continue;

                    if(get_player_cell(tx, ty)) player_close_corners++;
                    else if(get_opponent_cell(tx, ty)) opponent_close_corners++;
                }
            }
        }

        int close_corner_value = -12.5 * (player_close_corners - opponent_close_corners);
        
        return ((10*ratio) + (801*corner_ratio) + (382*close_corner_value) + (78*weighted_sum));
    }

    bool terminal() const {
        return free_cells().empty();
    }
    inline bool terminal(vector<pair<int, int>> free_cells) const {
        return free_cells.empty();
    }

    void play(int x, int y){
        make_move(x, y);
        swap_players();
    }

    Reversi gen_next_state(int x, int y){
        Reversi res;
        for(int i = 0; i < 8; i++) for(int j = 0; j < 8; j++) res.player[i][j] = player[i][j];
        for(int i = 0; i < 8; i++) for(int j = 0; j < 8; j++) res.opponent[i][j] = opponent[i][j];
        if(x != -1) res.make_move(x, y);
        res.swap_players();
        return res;
    }

    friend ostream &operator<<(ostream &out, const Reversi &board) {
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                out << (board.get_player_cell(i, j) ? 'P' : board.get_opponent_cell(i, j) ? 'O' : '.');
            }
            out << '\n';
        }
        return out;
    }
};

class AI{
    bool SORTING = false;
    const int  MAX_DEPTH = DEPTH;
    const int  MAX_PLAYER = 1, MIN_PLAYER = 0;
public:
    pair<int, int> get_best_move(Reversi state){
        return alphabeta_root(state);
    }
    pair<int, int> get_best_move(Reversi state, bool sorting){
        SORTING = sorting;
        return alphabeta_root(state);
    }

    /* #region //* ----minimax---- */
    pair<int, int> minimax_root(Reversi state){        // returns best move
        int best_score = INT_MIN;
        pair<int, int> best_move = {-1, -1};
        for(auto [x, y] : state.free_cells()){
            int score = minimax(state.gen_next_state(x, y), MAX_DEPTH, MIN_PLAYER);
            if(score > best_score){
                best_score = score;
                best_move = {x, y};
            }
        }

        return best_move;
    }

    int minimax(Reversi state, int depth, int player){
        vector<pair<int, int>> free_cells = state.free_cells();

        if(state.terminal(free_cells)){
            Reversi next = state.gen_next_state(-1, -1);
            if(next.terminal()) return state.result() * (player == MAX_PLAYER ? 1 : -1);
            else return minimax(next, depth - 1, 1 - player);
        }

        if(depth <= 0) return state.heuristic_result() * (player == MAX_PLAYER ? 1 : -1);


        int min_score = INT_MAX, max_score = INT_MIN;
        for(auto [x, y] : free_cells){
            int score = minimax(state.gen_next_state(x, y), depth - 1, 1 - player);
            min_score = min(min_score, score);
            max_score = max(max_score, score);
        }

        if(player == MIN_PLAYER){
            return min_score;
        } else{
            return max_score;
        }
    }
    /* #endregion */

    /* #region //* ----alphabeta---- */
    pair<int, int> alphabeta_root(Reversi state){        // returns best move
        int best_score = INT_MIN;
        pair<int, int> best_move = {-1, -1};
        vector<pair<int, int>> free_cells = state.free_cells();

        #ifdef enable_sorting
        if(SORTING) sort(free_cells.begin(), free_cells.end(), 
            [&](pair<int, int> a, pair<int, int> b){
            return (state.gen_next_state(a.first, a.second)).heuristic_result() >
                   (state.gen_next_state(b.first, b.second)).heuristic_result();
        });
        #endif

        #ifdef wanna_go_faster
        vector<future<int>> scores;
        scores.reserve(free_cells.size());
        for(unsigned int i = 0; i < free_cells.size(); i++){
            scores.push_back(async(launch::async, &AI::alphabeta, this, 
                                                    state.gen_next_state(free_cells[i].first, free_cells[i].second), 
                                                    MAX_DEPTH, MIN_PLAYER, INT_MIN, INT_MAX));
            // alphabeta(state.gen_next_state(x, y), MAX_DEPTH, MIN_PLAYER, INT_MIN, INT_MAX);
        }
        for(unsigned int i = 0; i < free_cells.size(); i++){
            int score = scores[i].get();
            if(score > best_score){
                best_score = score;
                best_move = free_cells[i];
            }
        }
        #else
        for(auto [x, y] : free_cells){
            int score = alphabeta(state.gen_next_state(x, y), MAX_DEPTH, MIN_PLAYER, INT_MIN, INT_MAX);
            if(score > best_score){
                best_score = score;
                best_move = {x, y};
            }
        }
        #endif

        return best_move;
    }

    int alphabeta(Reversi state, int depth, int player, int alpha, int beta){
        vector<pair<int, int>> free_cells = state.free_cells();

        if(state.terminal(free_cells)){
            Reversi next = state.gen_next_state(-1, -1);
            if(next.terminal()) return state.heuristic_result() * (player == MAX_PLAYER ? 1 : -1);
            else return alphabeta(next, depth - 1, 1 - player, alpha, beta);
        }

        if(depth <= 0) return state.heuristic_result() * (player == MAX_PLAYER ? 1 : -1);

        #ifdef enable_sorting
        if(SORTING) sort(free_cells.begin(), free_cells.end(), 
            [&](pair<int, int> a, pair<int, int> b){
            return (state.gen_next_state(a.first, a.second)).heuristic_result() >
                   (state.gen_next_state(b.first, b.second)).heuristic_result();
        });
        #endif

        int best_score = (player == MAX_PLAYER ? INT_MIN : INT_MAX);
        for(auto [x, y] : free_cells){
            int score = alphabeta(state.gen_next_state(x, y), depth - 1, 1 - player, alpha, beta);

            if(player == MIN_PLAYER){
                best_score = min(best_score, score);
                beta = min(beta, score);
            }
            else{
                best_score = max(best_score, score);
                alpha = max(alpha, score);
            }

            if(alpha >= beta) break;
        }

        return best_score;
    }
    /* #endregion */
};


void say(string what){
    printf("%s\n", what.c_str());
    fflush(stdout);
}

void say(string what, int x, int y){
    printf("%s %d %d\n", what.c_str(), x, y);
    fflush(stdout);
}

int main(int argc, char *argv[]){
    ios_base::sync_with_stdio(false);
    cin.tie(0);

    if(argc > 1) DEPTH = atoi(argv[1]);

    Reversi game(true);
    AI ai;
    #ifdef enable_sorting
    bool sorting = true;
    #endif
    #ifdef measure_time
    chrono::milliseconds::rep avg_player_time = 0;
    int moves = 0;
    int preprint = PREPRINT-1;
    #endif

    
    say("RDY");
    while(true){
        string cmd;
        cin >> cmd;

        if(cmd == "UGO"){
            double time_for_move, time_for_game; 
            cin >> time_for_move >> time_for_game;

            #ifdef measure_time
            auto  start  = Clock::now();
            #endif
            
            #ifdef enable_sorting
            auto p = ai.get_best_move(game, sorting);
            #else
            auto p = ai.get_best_move(game);
            #endif

            #ifdef measure_time
            auto  stop  = Clock::now();
            avg_player_time += chrono::duration_cast<chrono::milliseconds>(stop - start).count();
            ++moves;
            #endif

            say("IDO", p.second, p.first);
            game.make_move(p.first, p.second);
            game.swap_players();
        } else if(cmd == "HEDID"){
            double time_for_move, time_for_game; 
            cin >> time_for_move >> time_for_game;

            int x, y; cin >> y >> x;
            if(x != -1) game.make_move(x, y);
            game.swap_players();
            
            #ifdef measure_time
            auto  start  = Clock::now();
            #endif

            #ifdef enable_sorting
            auto p = ai.get_best_move(game, sorting);
            #else
            auto p = ai.get_best_move(game);
            #endif
            
            #ifdef measure_time
            auto  stop  = Clock::now();
            avg_player_time += chrono::duration_cast<chrono::milliseconds>(stop - start).count();
            ++moves;
            #endif

            say("IDO", p.second, p.first);
            game.make_move(p.first, p.second);
            game.swap_players();
        } else if(cmd == "ONEMORE"){
            game.reset(true);

            #ifdef enable_sorting
            sorting = true;
            #endif

            #ifdef measure_time
            preprint--;
            avg_player_time = avg_player_time/moves;
            if(not preprint)
                cerr << "Avg move time: " << avg_player_time << "ms, " << moves << " moves\n";
            moves = 0;
            #endif

            say("RDY");
        } else if(cmd == "BYE"){
            #ifdef measure_time
            cerr << "Avg move time: " << avg_player_time/moves << "ms, " << moves << " moves\n";
            avg_player_time = 0;
            moves = 0;
            #endif

            break;
        }

        #ifdef enable_sorting
        if(moves > SORTING_TRESHOLD) sorting = false;
        #endif
    }
}
