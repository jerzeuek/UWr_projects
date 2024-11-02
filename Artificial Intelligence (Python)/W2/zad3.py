# Rozwiązywanie problemu z komandosem za pomocą algorytmu A*
# Heurystyka: dla każdego pola na którym może stanąć komandos obliczamy długość najkrótszej ścieżki do najbliższego celu
# Potem priorytet do kolejki obliczamy sumując ilość poprzednich kroków i największą wartość heurystyki ze wszystkich obecnych pozycji graczy
# Stan to: pozycje komandosów, współrzędne celów, współrzędne ścian, szerokość i długość planszy, dokonane do tej pory ruchy

import queue
import copy
import heapq

# state == player_pos, goals, walls, x, y, moves
VISITED = set()

def finished(state):
    player_pos = state[0]
    goals = state[1]
    for pos in player_pos:
        if pos not in goals:
            return False
    return True


def merge_positions(state):
    player_pos = state[0]
    merged = False
    sorted_pos = list(sorted(player_pos))
    for i in range(len(sorted_pos)-1):
        if sorted_pos[i] == sorted_pos[i+1]:
            player_pos.remove(sorted_pos[i])
            merged = True
    return merged


# state == player_pos, goals, walls, x, y, moves
def find_moves(state):
    walls = state[2]
    result = []
    for i in range(2):  # 0 -> L/R; 1 -> U/D
        for j in range(-1, 2, 2):
            player_pos = []
            moves = copy.copy(state[5])
            for k in range(len(state[0])):
                old_pos = state[0][k]
                if i == 0:
                    new_pos = (state[0][k][0], state[0][k][1] + j)
                else:
                    new_pos = (state[0][k][0] + j, state[0][k][1])
                if new_pos not in walls:
                    player_pos.append(new_pos)
                else:
                    player_pos.append(copy.copy(old_pos))
            if i == 0:
                if j == 1:
                    moves.append('R')
                else:
                    moves.append('L')
            else:
                if j == 1:
                    moves.append('D')
                else:
                    moves.append('U')

            player_pos = sorted(player_pos)
            new_state = [player_pos, state[1], state[2], state[3], state[4], moves]
            if tuple(player_pos) not in VISITED:
                VISITED.add(tuple(player_pos))
                result.append(new_state)

    return result


def convert(stat):
    res = ""
    for x in stat:
        res += x
    return res

# state == player_pos, goals, walls, x, y, moves
def heuristic(state, f_pos, DISTANCES):
    goals = state[1]
    walls = state[2]
    Q = queue.Queue()
    Q.put((f_pos, 0))
    visited = [f_pos]
    DISTANCES[f_pos] = 99999999

    while not Q.empty():
        current = Q.get()
        pos = current[0]
        dist = current[1]
        if pos in goals:
            DISTANCES[f_pos] = min(DISTANCES[f_pos], dist)

        for i in range(2):  # 0 -> moving in LEFT/RIGHT; 1 -> moving in UP/DOWN
            for j in range(-1, 2, 2):
                if i == 0:
                    new_pos = (pos[0], pos[1] + j)
                else:
                    new_pos = (pos[0] + j, pos[1])
                if new_pos not in visited and new_pos not in walls:
                    visited.append(new_pos)
                    Q.put((new_pos, dist + 1))


# state == player_pos, goals, walls, x, y, moves
def find_all_distances(state, DISTANCES):
    x = state[3]
    y = state[4]
    walls = state[2]
    for i in range(y):
        for j in range(x):
            if (i, j) not in walls:
                heuristic(state, (i, j), DISTANCES)


def priority(state):
    distances = [DISTANCES[pos] for pos in state[0]]
    return len(state[5]) + max(distances) 


# state == player_pos, goals, walls, x, y, moves
def A_STAR(state):
    Q = []  # heap
    heapq.heappush(Q, (priority(state), state))
    VISITED.add(tuple(state[0]))

    while len(Q) > 0:
        st_from_Q = heapq.heappop(Q)[1]
        merge_positions(st_from_Q)
        new_states = find_moves(st_from_Q)
        for st in new_states:
            if finished(st):
                file = open("zad_output.txt", "w+")
                file.write(convert(st[5]))
                return st
            else:
                heapq.heappush(Q, (priority(st), st))


if __name__ == '__main__':
    DISTANCES = {}
    goals = set()
    walls = set()
    player_pos = []
    file = open("zad_input.txt")
    lines = file.readlines()
    i = 0
    for y in lines:
        j = 0
        for x in y:
            if x == 'S':
                player_pos.append((i, j))
            elif x == '#':
                walls.add((i, j))
            elif x == 'G':
                goals.add((i, j))
            elif x == 'B':
                goals.add((i, j))
                player_pos.append((i, j))
            j += 1
        i += 1
    x = j
    y = i
    moves = []
    find_all_distances([player_pos, goals, walls, x-1, y, moves], DISTANCES)
    A_STAR([player_pos, goals, walls, x-1, y, moves])