## Lab Work 9 - Remote Newspaper System

### Overview
This project implements a Remote Newspaper System using Java RMI (Remote Method Invocation). It consists of a server that provides news articles stored in a file and a client that can retrieve these articles based on certain criteria.

### Server
- **RemoteNewsServer.java**: This class represents the server component of the system. It extends `UnicastRemoteObject` to make its methods available for remote invocation. The server reads news articles from a file, stores them in memory, and provides methods for clients to retrieve all news articles or articles for a specific date.

### Client
- **Newspaper.java**: This class contains the definition of the remote interface `RemoteNewspaper`, which defines the methods available for clients to invoke remotely. It also contains the definition of the `News` class, representing individual news articles. Additionally, it includes the `Client` class, which serves as the client application. The client can request either all news articles or articles for a specific date from the server using RMI.

### Usage
1. Compile all Java files.
2. Start the server by running `RemoteNewsServer.java`.
3. Start the client by running `Newspaper.Client`, optionally providing command-line arguments:
    - To request all news articles: `allnews` or `a`
    - To request news articles for a specific date: `newsondate [date]` or `n [date]`

### Note
- The server reads news articles from a file named `input.txt`.
- Communication between the client and server is achieved using Java RMI.
- Error handling is implemented for various scenarios such as file reading errors and remote method invocation errors.