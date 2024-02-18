## Lab Work 6 - Internet Shop System

This Java program simulates an internet shop system with functionalities for administrators and customers. The system allows administrators to manage products, register sales, and maintain a blacklist of customers. Customers can make orders, pay for them, and be added to the blacklist if necessary.

### Main Class

The `Main` class serves as the entry point of the program. It creates instances of shops, administrators, customers, and products, performs various operations like adding products, making orders, and registering sales, and then saves and retrieves data using a `Connector` object.

### Admin Class 

The `Admin` class represents an administrator of the shop. It has methods to add, delete, and update products, register orders, and add customers to the blacklist. Admins are identified by their login credentials.

### AppException Class

The `AppException` class is a custom exception used to handle application-specific errors.

### Connector Class 

The `Connector` class provides methods to serialize and deserialize shop data, including shops, customers, orders, sales, and administrators. It uses object streams to write and read data to and from files.

### Customer Class 

The `Customer` class represents a customer of the shop. Customers have a surname and a balance, and they can make orders, pay for them, and be added to the blacklist if necessary.

### Order Class 

The `Order` class represents an order made by a customer. It contains information about the customer, the products in the order, and the total cost. Orders can be created with or without products, and products can be added or removed later.

### Product Class 

The `Product` class represents a product available in the shop. It has a name and a cost, and these attributes can be accessed and modified as needed.

### Sale Class

The `Sale` class represents a sale registered by an administrator. It contains information about the associated order and whether it has been paid for or not.

### Shop Class 

The `Shop` class represents the internet shop itself. It maintains lists of administrators, products, orders, sales, and customers. It provides methods to manage these entities, such as adding and deleting products, registering orders, and maintaining a blacklist of customers.

### AppLocale Class
The `AppLocale` class provides localization support for the internet shop system. It allows the program to display messages and labels in different languages based on the current locale.

### Testing

The `Main` class contains a test scenario where various operations are performed, including adding products, making orders, registering sales, and saving and retrieving data. The output is displayed to the console to verify the correctness of the operations.

### Usage

Run the `Main` class to execute the test scenario and observe the output in the console. The program demonstrates the functionality of the internet shop system, including adding products, making orders, and managing customers and sales.

Feel free to reach out for any questions or improvements!