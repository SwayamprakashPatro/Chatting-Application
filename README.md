# Chatting-Application
Chatting Application using Java Swing and Socket Programming 
This project is a simple Java-based chat application, designed to allow two users to communicate over a local network. The application consists of a server and a client that exchange text messages in real-time. The user interfaces for both the server and the client are built using Java Swing.

 Features

- Real-time Communication: The server and client exchange messages instantly over a socket connection.
- User Interface: The application features a clean and simple UI, with separate panels for displaying messages and sending new ones.
- Keyboard Shortcuts: Users can send messages by pressing the `Enter` key, improving the user experience.
- Timestamps: Messages are displayed with a timestamp, helping users keep track of the conversation timeline.

 Technologies Used

- Java: Core language used to develop the application.
- Java Swing: Used for building the graphical user interface.
- Sockets: For establishing communication between the server and the client.
- Multithreading: To handle simultaneous read and write operations.

 Installation

1. Clone the repository to your local machine:

    ```bash
    git clone https://github.com/your-username/chatting-application.git
    ```

2. Navigate to the project directory:

    ```bash
    cd chatting-application
    ```

3. Compile the Java files:

    ```bash
    javac ChattingApplication/Server.java ChattingApplication/Client.java
    ```

4. Run the server:

    ```bash
    java ChattingApplication.Server
    ```

5. In a new terminal, run the client:

    ```bash
    java ChattingApplication.Client
    ```

 Usage

- Server: The server application should be started first. It will listen for incoming connections from the client.
- Client: After starting the server, you can launch the client. The client will automatically connect to the server at `127.0.0.1:8888`.
- Sending Messages: Type your message in the text field and press the `Enter` key or click the "Send" button to send it.
