# Używamy bfsa na możliwych ruchach wyszukanych przy danym położeniu
# Jeśli po ruchu białymi będzie szach i mat to kończymy
# Z racji że korzystamy z bfsa to pierwsze znalezione rozwiązanie będzie posiadało najmniej ruchów 

# Zasady znajdowania możliwych pozycji:
    # biały król:
        # nie może być na pozycji białej wieży ani czarnego króla
        # nie może sąsiadować z czarnym królem
    # czarny król
        # nie może być na pozycji białej wieży ani białego króla
        # nie może sąsiadować z białym królem ani białą wieżą
        # nie może być w tym samym rzędzie/kolumnie co biała wieża (jeśli nie zasłania jej biały król)
    # biała wieża
        # nie może być na pozycji białego króla ani czarnego króla
        # nie może sąsiadowac z czarnym królem
        # nie może pójść na pozycje które są zasłaniane przez inne figury

from collections import deque

def print_moves(moves):
    i = 0
    for move in moves:
        print_move(move, i)
        i+=1

def print_move(move, num_of_move):
    letters = 'abcdefgh'

    chessboard = [["◼","◻","◼","◻","◼","◻","◼","◻"], 
                  ["◻","◼","◻","◼","◻","◼","◻","◼"],
                  ["◼","◻","◼","◻","◼","◻","◼","◻"], 
                  ["◻","◼","◻","◼","◻","◼","◻","◼"],
                  ["◼","◻","◼","◻","◼","◻","◼","◻"], 
                  ["◻","◼","◻","◼","◻","◼","◻","◼"],
                  ["◼","◻","◼","◻","◼","◻","◼","◻"], 
                  ["◻","◼","◻","◼","◻","◼","◻","◼"]]
    
    if(num_of_move == 0):
        print("Stan początkowy: \n")
    else:
        print("Ruch nr", num_of_move, ": \n")

    chessboard[8 - int(move[1][1])][letters.index(move[1][0])] = "♔"
    chessboard[8 - int(move[2][1])][letters.index(move[2][0])] = "♖"
    chessboard[8 - int(move[3][1])][letters.index(move[3][0])] = "♚"

    for line in chessboard:
        print(' '.join(line))
    
    print()

def king_positions(king_pos, other_king_pos, rook_pos):
    letters = 'abcdefgh'
    pos_list = []

    letter = letters.index(king_pos[0])
    number = int(king_pos[1])

    for i in range(letter - 1, letter + 2):
        for j in range(number - 1, number + 2):
            if(i in range(0, 8) and j in range(1, 9)):
                possible_position = letters[i] + str(j)
                if(possible_position != king_pos and possible_position != other_king_pos and possible_position != rook_pos):
                    pos_list.append(possible_position)

    return pos_list

def rook_positions(rook_pos, w_king_pos, b_king_pos):
    letters = 'abcdefgh'
    pos_list = []

    letter = letters.index(rook_pos[0])
    number = int(rook_pos[1])

    for i in range(letter - 1, -1, -1):
        if(i in range(0,8)):
            possible_position = letters[i] + str(number)
            if(possible_position == w_king_pos):
                break
            pos_list.append(possible_position)
            if(possible_position == b_king_pos):
                break
        
    for i in range(letter + 1, 8):
        if(i in range(0,8)):
            possible_position = letters[i] + str(number)
            if(possible_position == w_king_pos):
                break
            pos_list.append(possible_position)
            if(possible_position == b_king_pos):
                break
        
    for i in range(number - 1, 0, -1):
        if(i in range(1,9)):
            possible_position = letters[letter] + str(i)
            if(possible_position == w_king_pos):
                break
            pos_list.append(possible_position)
            if(possible_position == b_king_pos):
                break
        
    for i in range(number + 1, 9):
        if(i in range(1,9)):
            possible_position = letters[letter] + str(i)
            if(possible_position == w_king_pos):
                break
            pos_list.append(possible_position)
            if(possible_position == b_king_pos):
                break

    return pos_list

def all_positions(w_king, w_rook, b_king):
    letters = 'abcdefgh'
        
    w_king_positions = king_positions(w_king, b_king, w_rook)
    b_king_positions = king_positions(b_king, w_king, w_rook)
    w_rook_positions = rook_positions(w_rook, w_king, b_king)

    w_king_and_b_king = [i for i in w_king_positions if i in b_king_positions]
    w_rook_and_b_king = [i for i in w_rook_positions if i in b_king_positions]

    w_king_positions = [i for i in w_king_positions if i not in w_king_and_b_king]
    b_king_positions = [i for i in b_king_positions if i not in w_king_and_b_king or i not in w_king_and_b_king]
    w_rook_positions = [i for i in w_rook_positions if i not in w_rook_and_b_king]

    b_king_letter = letters.index(b_king[0])
    b_king_number = int(b_king[1])

    for letter in range(b_king_letter - 1, b_king_letter + 2):
        if(letter in range(0, 8)):
            for number in range(b_king_number-1, 0, -1):
                possible_location = letters[letter] + str(number)
                if(possible_location == w_king):
                    break
                if(possible_location == w_rook):
                    b_king_positions = [i for i in b_king_positions if i[0] != letters[letter]]
            
            for number in range(b_king_number + 1, 9):
                possible_location = letters[letter] + str(number)
                if(possible_location == w_king):
                    break
                if(possible_location == w_rook):
                    b_king_positions = [i for i in b_king_positions if i[0] != letters[letter]]

    for number in range(b_king_number -1, b_king_number + 2):
        if(number in range(1, 9)):
            for letter in range(b_king_letter - 1, -1, -1):
                possible_location = letters[letter] + str(number)
                if(possible_location == w_king):
                    break
                if(possible_location == w_rook):
                    b_king_positions = [i for i in b_king_positions if i[1] != str(number)]
            
            for letter in range(b_king_letter + 1, 8):
                possible_location = letters[letter] + str(number)
                if(possible_location == w_king):
                    break
                if(possible_location == w_rook):
                    b_king_positions = [i for i in b_king_positions if i[1] != str(number)]
    
    return [w_king_positions, w_rook_positions, b_king_positions]

def check(w_rook_positions, b_king):
    return b_king in w_rook_positions

def mate(b_king_positions):
    return len(b_king_positions) == 0

def least_moves(move, w_king, w_rook, b_king, debug):
    visited = set()
    queue = deque()
    queue.append((move, w_king, w_rook, b_king))
    queue_moves = deque()
    queue_moves.append([(move, w_king, w_rook, b_king)])
    queue_len = deque()
    queue_len.append(0)
    while(len(queue) > 0):
        new_data = queue.popleft()
        new_len = queue_len.popleft()+1
        new_moves = queue_moves.popleft()
        new_moves.append(new_data)
        if(new_data not in visited):
            visited.add(new_data)
            w_king_pos, w_rook_pos, b_king_pos = all_positions(new_data[1], new_data[2], new_data[3])
            if(new_data[0] == "black" and check(w_rook_pos, new_data[3])) and mate(b_king_pos):
                if(debug):
                    print_moves(new_moves)
                return new_len - 1 
            else:
                if(new_data[0] == "black"):
                    for position in b_king_pos:
                        queue.append(("white", new_data[1], new_data[2], position))
                        queue_len.append(new_len)
                        queue_moves.append(list(new_moves))
                else:
                    for position in w_rook_pos:
                        queue.append(("black", new_data[1], position, new_data[3]))
                        queue_len.append(new_len)
                        queue_moves.append(list(new_moves))

                    for position in w_king_pos:
                        queue.append(("black", position, new_data[2], new_data[3]))
                        queue_len.append(new_len)
                        queue_moves.append(list(new_moves))
    
    return('INF')
            

if __name__  == "__main__":
    with open('zad1_input.txt', 'r', encoding='utf8') as input, open("zad1_output.txt", 'w', encoding='utf8') as output:
        for line in input.read().split("\n"):
            args = line.split(" ")
            if(len(args) == 4):
                answer = least_moves(args[0], args[1], args[2], args[3], True)
                output.write(str(answer) + '\n')