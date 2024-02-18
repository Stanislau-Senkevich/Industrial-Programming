Sure, here's the README.md for the provided code:

# Lab Work 5 - Payment Processing System

This Java program implements a payment processing system using the `Payment` class. It allows creating payment records with various attributes such as name, salary, workdays, bonus percentage, tax percentage, and accrued/withheld amounts. The program also provides sorting functionality based on different attributes of the payment records.

## Payment Class

### Inner Class
- `ArgException`: Custom exception class for handling invalid arguments during payment creation.

### Constants
- `names`: An array of strings representing the names of payment attributes.

### Fields
- `areas`: An array of strings representing the values of payment attributes.

### Static Fields
- `formatStr`: An array of format strings used for formatting payment records.

### Static Methods
- `getSortByName(int sortBy)`: Returns the name of the attribute based on the given sort index.
- `getComparator(final int sortBy)`: Returns a comparator for sorting payment records based on the specified attribute.

### Methods
- `validSurname`, `validName`, `validPatronymic`, `validStartingWorkYear`, `validSalary`, `validWorkdays`, `validCompletedWorkdays`, `validPercent`, `validAmount`: Helper methods to validate payment attributes.
- `length()`: Returns the number of payment attributes.
- `getArea(int idx)`: Returns the value of the payment attribute at the specified index.
- `setArea(int idx, String value) throws ArgException`: Sets the value of the payment attribute at the specified index, throwing an `ArgException` if the value is invalid.
- `iterator()`: Returns an iterator over the payment attributes.
- `format()`: Formats the payment attributes as a string.
- `format(Payment p)`: Formats the attributes of a given payment record.
- `compareTo(Payment p)`: Compares two payment records based on their formatted representations.
- `toString()`: Returns a string representation of the payment record.
- `setup(String[] args) throws ArgException`: Sets up the payment record with the provided arguments.
- `Payment(String str) throws ArgException`: Constructor that initializes the payment record with values parsed from a string.
- `Payment(String... args) throws ArgException`: Constructor that initializes the payment record with the provided arguments.

### Additional Methods
- `countAccruedSum()`, `countWithheldSum()`, `countSumToWorker()`: Methods to calculate accrued sum, withheld sum, and sum payable to the worker, respectively.

## Test Class

### Methods
- `sortAndPrint(Payment[] pays)`: Sorts and prints an array of payment records in natural order.
- `sortAndPrint(Payment[] pays, int sortBy)`: Sorts and prints an array of payment records based on the specified attribute.
- `main(String[] args)`: Entry point of the program, where payment records are created, processed, and sorted.

## Testing
- The program creates payment records with various attributes and calculates accrued and withheld sums.
- Payment records are sorted based on different attributes and printed to the console.

## Usage
- Run the program to create, process, and sort payment records.
- The program provides various sorting options based on payment attributes.

Feel free to reach out for any questions or improvements!