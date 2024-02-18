## Lab Work 8 - News Broadcasting System

### Overview
This project implements a News Broadcasting System consisting of a server and multiple clients. The server stores news messages for the current month and broadcasts them to all connected clients. Clients can also request news for a specific date.

### Server
- **ServerMain.java**: This class represents the main server application. It listens for incoming client connections and spawns a new thread for each client. The server stores news messages and handles client requests.

- **ServerThread.java**: Each instance of this class represents a thread handling communication with a single client. It reads client requests, processes them, and sends back appropriate responses.

- **ServerStopThread.java**: This thread allows stopping the server gracefully by waiting for a command from the console.

### Client
- **ClientMain.java**: This class represents the main client application. It connects to the server, handles user input, sends requests to the server, and displays the received news messages.

### Message Classes
- **Message.java**: Base class for all types of messages exchanged between clients and the server.

- **MessageConnect.java** / **MessageConnectResult.java**: Messages for client-server connection establishment.

- **MessageDisconnect.java**: Message for client disconnection.

- **MessageAllNews.java** / **MessageAllNewsResult.java**: Messages for requesting and receiving all news messages.

- **MessageNewsDay.java** / **MessageNewsDayResult.java**: Messages for requesting and receiving news messages for a specific day.

- **MessageResult.java**: Base class for result messages returned by the server.

### Other Classes
- **News.java**: Represents a news message with text and date.

- **Protocol.java**: Defines constants for command IDs, result codes, and the server port number.

### How to Run
1. Compile all Java files.
2. Start the server by running `ServerMain.java`.
3. Start one or more clients by running `ClientMain.java`, providing arguments for username and full name.

### Usage
- Once connected, clients can request all news messages or news for a specific day.
- To stop the server, enter the quit command (`q` or `quit`) in the server console.

### Note
- Make sure to have the appropriate Java environment set up to run the application.
- Input data for news messages is read from a file named `input.txt`.
- The system architecture follows a client-server model for communication.
- Error handling is implemented for various scenarios such as invalid commands and client disconnections.