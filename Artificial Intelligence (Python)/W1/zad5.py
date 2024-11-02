import random

def solve_nonogram(data):
    solution = [['.' for i in range(data[0])] for j in range(data[1])]
    while(not is_solved(data, solution)):
        row_or_col = random.choice([0, 1])
        if(row_or_col == 0):
            row_num = random.randint(0, data[0]-1)
            line = solution[row_num]
            line_len = data[2+row_num]
        elif(row_or_col == 1):
            col_num = random.randint(0, data[1]-1)
            line = [solution[i][col_num] for i in range(data[0])]
            line_len = data[2+data[0]+ col_num]
        if(not check_row_col(line_len, line)):
            pos_improvement = [] 
            for i in range(line_len):
                new_line = change_pixel(line, i)
                if(row_or_col == 0):
                    



def check_row_col(correct_len, row):
    if row.count('#') == correct_len:
        first_index = row.index('#')
        for i in range(first_index, first_index + correct_len):
            if(row[i]) == '.':
                return False
        return True
    else:
        return False

def is_solved(data, solution):
    for i in range(data[0]):
        if(not check_row_col(data[2+i], solution[i])):
            return False
    
    for i in range(data[1]):
        column = [solution[j][i] for j in range(data[0])]
        if(not check_row_col(data[2+data[0]+i], column)):
            return False
    
    return True

print(is_solved([3, 3, 1, 3, 1, 3, 1, 1], [['#', '#', '.'], ['#', '#', '#'], ['#', '.', '.']]))

# #.
# ###
# #..