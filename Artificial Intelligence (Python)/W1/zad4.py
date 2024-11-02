# Sprawdzam minimalną liczbę zmian dla każdej sekwencji długości length

def opt_dist(numbers, length):
    
    min_ops = float('inf')

    for i in range(len(numbers) - length + 1):
        cur_ops = numbers.count('1', 0, i) + numbers.count('0', i, i+length) + numbers.count('1', i+length)
        min_ops = min(min_ops, cur_ops)

    return min_ops


if __name__  == "__main__":
    with open('zad4_input.txt', 'r', encoding='utf8') as input, open("zad4_output.txt", 'w', encoding='utf8') as output:
        for line in input.read().split("\n"):
            args = line.split(" ")
            if(len(args) == 2):
                answer = opt_dist(args[0], int(args[1]))
                output.write(str(answer) + '\n')