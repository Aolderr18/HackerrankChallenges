englishLetters = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z']

s = raw_input()
numberOfWords = 0
if len(s) > 0:
    numberOfWords += 1
i = 0
while i < len(s):
    if englishLetters.index(s[i]) < 26:
        # If the letter is before index 26, it must be upper case
        numberOfWords += 1
    i += 1
        
print(numberOfWords)
