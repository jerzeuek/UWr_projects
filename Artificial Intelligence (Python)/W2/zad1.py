# Metoda jak z P1, przechodzi 10/12 testów z cachem
# python validator2.py --cases 1-9,12 zad1 pypy zad1.py
# Klasa Nonogram ma pola: row_nums, col_nums, rows, cols, cache, MAX_NUM_TRIES
# reset: Resetuje planszę, tworząc nową macierz składającą się z zer i losowo zakolorowuje niektóre kratki przy użyciu metody flip.
# try_to_solve: Próbuje rozwiązać łamigłówkę, wykonując maksymalnie MAX_NUM_TRIES prób. W każdej próbie losowo wybiera, czy zmienić kolumnę, czy wiersz
# opt_dist:
    # oblicza minimalną liczbę zmian, które należy wykonać, aby dane sekwencje kratek odpowiadały wskazówkom, wykorzystuję cache aby nie dokonywać niepotrzebnych obliczeń 
# is_solved: sprawdza czy łamigłówka została rozwiązana
# change_random_row / change_random_col:
    # wybiera losowo wiersz lub kolumnę i próbują znaleźć najlepszą pojedynczą zmianę (flip) w tym wierszu/kolumnie, która najbardziej zbliży planszę do stanu rozwiązania.
# flip: Zmienia stan wybranej kratki z zakolorowanej na niezakolorowaną lub odwrotnie zarówno w wierszu, jak i kolumnie.

import random

class Nonogram:
    def __init__(self, row_nums, col_nums):
        self.row_nums = row_nums
        self.col_nums = col_nums
        self.cache = {}
        self.MAX_NUM_TRIES = len(row_nums) * len(col_nums) * 500
        self.reset()
    
    def reset(self):
        self.rows = [[0] * len(self.col_nums) for _ in self.row_nums]
        self.cols = [[0] * len(self.row_nums) for _ in self.col_nums]
        for x in range(len(self.cols)):
            for y in range(len(self.rows)):
                if random.randint(0, 1):
                    self.flip(x, y)

    def try_to_solve(self):
        for _ in range(self.MAX_NUM_TRIES):
            if self.is_solved():
                return
            
            if random.randint(0, 1):
                self.change_random_row()
            else:
                self.change_random_col()
    
    def opt_dist(self, line, line_num):
        key = tuple(line), tuple(line_num)
        if key in self.cache:
            return self.cache[key]

        if not line_num:
            return sum(line)
        
        n = line_num[0] # pierwsza wskazówka
        last = len(line) - sum(line_num) - len(line_num) + 1 # ostatnie miejsce od którego można zacząć rozwiązanie żeby zmieściło się w linii
        flips_before_window = 0
        num_1s_in_window = sum(line[:n])

        to_flip_just_after = len(line) > n and line[n]
        least_flips = (n - num_1s_in_window) + to_flip_just_after + self.opt_dist(line[n+1:], line_num[1:]) #najmniejsza ilość flipów potrzebna żeby była poprawna linia

        if last > 0:
            for i in range(last):
                num_1s_in_window -= line[i]
                flips_before_window += line[i]
                num_1s_in_window += line[i + n]
                to_flip_just_after = len(line) > i + n + 1 and line[i + n + 1]

                least_flips = min(
                    (n - num_1s_in_window) + flips_before_window + to_flip_just_after + self.opt_dist(line[i+n+2:], line_num[1:]),
                    least_flips
                )
        self.cache[key] = least_flips
        return least_flips

    def is_solved(self):
        return all(
            self.opt_dist(row, n) == 0
            for row, n in zip(self.rows, self.row_nums)
        ) and all(
            self.opt_dist(col, n) == 0
            for col, n in zip(self.cols, self.col_nums)
        )
    
    def change_random_row(self):
        y = random.randint(0, len(self.row_nums) - 1)
        row, row_num = self.rows[y], self.row_nums[y]

        best_flip_x, min_dist = 0, len(row) + len(self.col_nums) + 1

        for x, (col, col_num) in enumerate(zip(self.cols, self.col_nums)):
            self.flip(x, y)

            curr_dist = self.opt_dist(row, row_num) + self.opt_dist(col, col_num)
            if curr_dist < min_dist:
                min_dist = curr_dist
                best_flip_x = x

            self.flip(x, y)
        
        self.flip(best_flip_x, y)
    
    def change_random_col(self):
        x = random.randint(0, len(self.col_nums) - 1)
        col, col_num = self.cols[x], self.col_nums[x]

        best_flip_y, min_dist = 0, len(col) + len(self.row_nums) + 1

        for y, (row, row_num) in enumerate(zip(self.rows, self.row_nums)):
            self.flip(x, y)

            curr_dist = self.opt_dist(col, col_num) + self.opt_dist(row, row_num)
            if curr_dist < min_dist:
                min_dist = curr_dist
                best_flip_y = y

            self.flip(x, y)
        
        self.flip(x, best_flip_y)

    def flip(self, x, y):
        self.rows[y][x] ^= 1
        self.cols[x][y] ^= 1

    def as_image(self):
        return '\n'.join(
            ''.join('#' if c == 1 else '.' for c in row)
            for row in self.rows
        )


if __name__ == '__main__':
    with open('zad_input.txt') as inp:
        size_raw = inp.readline()
        num_rows, num_cols = map(int, size_raw.split())
        row_nums = [[int(x) for x in inp.readline().split()] for _ in range(num_rows)]
        col_nums = [[int(x) for x in inp.readline().split()] for _ in range(num_cols)]
    
    puzzle = Nonogram(row_nums, col_nums)
    while not puzzle.is_solved():
        puzzle.reset()
        puzzle.try_to_solve()
    
    with open('zad_output.txt', 'w+') as out:
        out.write(puzzle.as_image())