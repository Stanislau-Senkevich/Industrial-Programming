# Lab Work 4 - Fraction Operations

This Java program defines a class `Fraction` to represent fractions and another class `Test` to perform various operations on fractions, including arithmetic operations and comparisons.

## Fraction Class

### Constructors
- `Fraction()`: Default constructor, initializes fraction to 0/1.
- `Fraction(long x, long y) throws IOException`: Parameterized constructor, initializes fraction to `x/y`, throws IOException if `y` is non-positive.
- `Fraction(long numerator)`: Initializes fraction to `numerator/1`.
- `Fraction(final Fraction f)`: Copy constructor, creates a new fraction object as a copy of the given fraction.

### Methods
- `Print()`: Returns a string representation of the fraction in the form `numerator/denominator`.
- `GetNumerator()`: Returns the numerator of the fraction.
- `GetDenominator()`: Returns the denominator of the fraction.
- `SetNumerator(long num)`: Sets the numerator of the fraction.
- `SetDenominator(long den) throws IOException`: Sets the denominator of the fraction, throws IOException if `den` is non-positive.
- `Set(long num, long den) throws IOException`: Sets both numerator and denominator of the fraction, throws IOException if `den` is non-positive.
- `Reduce()`: Reduces the fraction to its simplest form.
- Static methods for arithmetic operations: `Add`, `Substract`, `Produce`, `Divide`.
- Static methods for comparisons: `Greater`, `Equal`, `GreaterOrEqual`, `Less`, `LessOrEqual`.

## Test Class

### Constants
- `k`: Represents the number of fractions.

### Methods
- `main(String[] args)`: Entry point of the program.
    - Performs various tests on fraction operations and comparisons.
    - Accepts input for `k` fractions from the user and prints them.

## Testing
- The program performs various tests to verify the correctness of fraction operations and comparisons.
- Assertions are used to ensure that the results match the expected values.

## Usage
- Run the program to perform fraction operations and comparisons.
- Follow the on-screen instructions to input fractions.

Feel free to reach out for any questions or improvements!