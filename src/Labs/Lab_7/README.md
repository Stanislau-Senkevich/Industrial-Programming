## Lab Work 7 - Tours System

The Tours system is a Java application for managing and manipulating tour data. It includes several classes to handle data storage, indexing, and user interactions.

### Files:

1. **Buffer.java:**
    - Manages serialization and compression of objects.
    - Provides methods to convert objects to byte arrays and vice versa.
    - Supports writing and reading objects to and from a RandomAccessFile.

2. **Index.java:**
    - Implements the main index structure for the Tours system.
    - Utilizes different IndexBase implementations for one-to-one and one-to-many relationships.
    - Supports adding tours to the index and loading/saving the index.

3. **KeyNotUniqueException.java:**
    - Custom exception class for handling key uniqueness violations.

4. **Tour.java:**
    - Represents a tour with various attributes such as tour name, name, day cost, day amount, transport price, and trip price.
    - Supports reading from and writing to a file, and provides validation methods.

5. **Tours.java:**
    - Main executable class with a command-line interface for interacting with the Tours system.
    - Supports commands for appending data, printing data, clearing data, deleting records by key, finding records by key, and more.
    - Utilizes the Buffer, Index, and Tour classes for data management.

### Usage:

- Compile the Java files using a Java compiler (e.g., `javac Buffer.java Index.java KeyNotUniqueException.java Tour.java Tours.java`).
- Run the Tours system using the command-line interface with various options and commands.

### Commands:

- `-a`: Append data to the file.
- `-az`: Append compressed data to the file.
- `-d`: Clear all data.
- `-dk {t|n|d} key`: Clear data by key.
- `-p`: Print data unsorted.
- `-ps {t|n|d}`: Print data sorted by tour/name/days.
- `-psr {t|n|d}`: Print data reverse sorted by tour/name/days.
- `-f {t|n|d} key`: Find record by key.
- `-fr {t|n|d} key`: Find records > key.
- `-fl {t|n|d} key`: Find records < key.
- `-?`, `-h`: Display command-line syntax.

Note: Some commands support additional optional arguments such as file and encoding.

### Example:

```bash
# Append data from input file.txt with UTF-8 encoding
java Tours -a file.txt utf-8

# Print data sorted by tour name
java Tours -ps t
```

Feel free to explore and modify the Tours system according to your requirements.