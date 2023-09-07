import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection implements Runnable {
  private Socket clientConnection;
  private BufferedReader in;
  private PrintWriter out;
  private String username;
  private Server server;

  public Connection(Server server, Socket client) {
    this.server = server;
    this.clientConnection = client;
    System.out.println("Client connected! " + client.getInetAddress());
  }

	@Override
	public void run() {
    try {
      initIO();
      getClientsUsername();
      handleClientMessages();
    } catch (Exception e) {
      out.println("Error occured: " + e + "\n Connection closed!");
      this.close();
    }

	}

  private void initIO() throws IOException {
      out = new PrintWriter(clientConnection.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
  }

  private void getClientsUsername() throws IOException {
      out.println("Please enter a Username: ");
      username = in.readLine();
      System.out.println(username + " connected!");
      server.broadcast(username + " joined the chat!");
  }

  private void handleClientMessages() throws IOException{
    String message;
    while((message = in.readLine()) != null) {
      if(message.startsWith("/username ")) {
        changeClientsUsername(message);
      } else if (message.equals("/quit")) {
        this.close();  
        break;
      } else {
        server.broadcast(username + ": " + message);
      }
    }

  }

  private void changeClientsUsername(String command) {
      String [] commandWords = command.split(" ", 2);
      String oldUsername = username;
      if(commandWords.length == 2) {
        username = commandWords[1];
        out.println("Succesfully changed username to " + username + "!");
        server.broadcast(
            "user \"" + oldUsername  + "\" changed username to \""
            + username + "\"!"
        );
      } else {
        out.println("Couldnt change username, no name provided!");
      }
  }
  
  public void close() {
    server.broadcast(username + " left the chat!");
    System.out.println(
        "client: " + username + " (" 
        + clientConnection.getInetAddress()
        + ")" + " disconnected!"
      );
    try {
      if(!clientConnection.isClosed()) {
        clientConnection.close();
      }
      in.close();
      out.close();
    } catch (Exception e){
      System.out.println("Error occured closing client connection: " + e);
    }
  }

  public void sendMessage(String message) {
    out.println(message);
  }
}
