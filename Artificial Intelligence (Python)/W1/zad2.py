def words_to_set(file_path):
    with open(file_path, 'r', encoding='utf8') as file:
        return set(file.read().split("\n"))

def reconstruction(text, word_set):
    n = len(text)
    dp = [0] * (n + 1)
    words = [''] * (n + 1)
    
    for i in range(1, n + 1):
        max_score = 0
        best_word = ''
        for j in range(i):
            word = text[j:i]
            if word in word_set:
                score = len(word) ** 2 + dp[j]
                if score > max_score:
                    max_score = score
                    best_word = word
        dp[i] = max_score
        words[i] = words[i-len(best_word)] + ' ' + best_word
        print(words[i])
    return(words[n])

if __name__ == "__main__":
    word_set = words_to_set("words_for_ai1.txt")
    with open("zad2_input.txt", 'r', encoding='utf8') as input, open("zad2_output.txt", 'w', encoding='utf8') as output:
        for line in input.read().split("\n"):
            segmented_text = reconstruction(line, word_set)
            output.write(segmented_text + '\n')

