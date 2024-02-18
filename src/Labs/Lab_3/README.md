# Lab Work 3 - Matrix Triangle Average Calculator

This Java program generates an `n x n` matrix and divides it into four triangular parts using its main and secondary diagonals. Then, it calculates the average of the elements in each triangular part excluding the diagonal elements.

## Usage

The program prompts the user to enter a positive integer `n`, which represents the size of the matrix. After entering `n`, the program generates a random matrix of size `n x n` and calculates the averages of the elements in each triangular part.

Example:
```bash
java Labs.Lab_3.Main
```

The program will ask for the size `n` of the matrix. After entering `n`, it will generate the matrix and print the original matrix along with the average of elements in each triangular part.

## Algorithm

1. Generate a random `n x n` matrix with values ranging from `-n` to `n`.
2. Divide the matrix into four triangular parts based on its main and secondary diagonals.
3. Calculate the sum of elements in each triangular part while excluding the diagonal elements.
4. Calculate the average of the elements in each triangular part.
5. Print the original matrix and the average of elements in each triangular part.

## Output

The program prints the original matrix and the average of elements in each triangular part in the following format:
- The original matrix.
- Average of elements in the upper triangular part.
- Average of elements in the right triangular part.
- Average of elements in the lower triangular part.
- Average of elements in the left triangular part.

## Notes

- If the value of `n` is less than 3, it indicates that there are no elements in the triangular parts, and the program will inform the user.
- Diagonal elements are excluded from the calculation of averages.

Feel free to reach out for any questions or improvements!