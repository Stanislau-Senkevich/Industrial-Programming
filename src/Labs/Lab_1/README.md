# Lab Work 1 - Computing Taylor Series Approximation

This Java program calculates the Taylor series approximation of the arctangent function for a given input `x` and number of terms `k`.

## Usage

To run the program, provide two command-line arguments:
1. `x`: The value at which to evaluate the arctangent function.
2. `k`: The number of terms to use in the Taylor series approximation.

Ensure that the arguments are provided in the correct order.

Example:
```bash
java Labs.Lab_1.Main 0.5 10
```

This command will compute the Taylor series approximation of arctan(0.5) using 10 terms.

## Error Handling

The program performs basic error checking on the input arguments:
- Checks if the number of arguments provided is exactly 2. If not, it displays an error message.
- Verifies if the provided `x` value is within the valid range (-1 < x < 1). If not, it displays an error message.
- Ensures that the `k` value is greater than 1. If not, it displays an error message.

## Calculation

The program computes the Taylor series approximation using the provided `x` and `k` values. It iteratively calculates the terms of the series until the desired precision is achieved.

## Output

The program outputs the results in the following format:
- Approximation using Taylor series.
- Exact value using the built-in `Math.atan` function.

Both values are displayed with the precision specified by the `k` argument.

## Exit Codes

- The program exits with code 0 upon successful execution.
- If any error occurs during argument parsing or calculation, it exits with code 1.

Feel free to reach out for any questions or improvements!