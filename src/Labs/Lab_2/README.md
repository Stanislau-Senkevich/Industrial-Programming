# Lab Work 2 - Anagram Finder

This Java program finds and prints anagrams for each input string. Anagrams are pairs of words where one word can be formed by rearranging the letters of the other word.

## Usage

The program reads input strings from the standard input (stdin). Each input string should consist of words separated by one or more spaces and punctuation marks.

Example:
```bash
java Labs.Lab_2.Main
```

The program will wait for input from the user. Once input is provided, it will find and print any anagrams found in the input string.

## Anagram Finding Algorithm

For each input string, the program:
- Tokenizes the string into individual words.
- Checks for anagrams by comparing each pair of words.
- If an anagram pair is found, it prints the pair in the format `(word1, word2)`.
- If no anagrams are found in the input string, it prints `No anagrams were found.`

## Exit Codes

- The program exits with code 0 upon successful execution.
- It terminates gracefully when no more input is provided.

Feel free to reach out for any questions or improvements!