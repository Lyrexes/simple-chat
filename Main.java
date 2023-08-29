import java.io.IOException;

public class Main {
  public static void main(String[] args) {
    if(args.length == 0) {
      System.out.println("Error: No arguments provided!\n" + getUsageString());
    } else if(args[0].equals("--server") || args[0].equals("-s")) {
      executeServerChatInstance();
    } else if(args[0].equals("--client") || args[0].equals("-c")) {
      executeClientChatInstance();
    } else {
      System.out.println("Error: Invalid argument!\n" + getUsageString());
    }
  }

  private static void executeServerChatInstance() {
      Server server = new Server();
      try {
          int port = 8080;
          System.out.println("Listening on port: " + String.valueOf(port));
          server.listen(port);
          System.out.println("Connected to: " + server.getConnectedIP());
          String serverMessage = "";
          String receivedMessage;
          while((receivedMessage = server.receiveMessage()) != null
              && !receivedMessage.isEmpty()) {
            System.out.println("client: " + receivedMessage);
            serverMessage = System.console().readLine("server> ");
            server.sendMessage(serverMessage);
          }
          System.out.println("Closing connection...");
          server.close();
      } catch(Exception e) {
        System.out.println("Error: " + e);
      }
  }

  private static void executeClientChatInstance() {
      Client client = new Client();
      try {
        String ip = "127.0.0.1";
        int port =  8080;
        System.out.println(
            "connecting to: " + ip
            + " with port: " + String.valueOf(port)
            + "..."
        );
        client.connect(ip, port);
        System.out.println("Connected!");
        String clientMessage = System.console().readLine("client> ");
        client.sendMessage(clientMessage);
        String receivedMessage;
        while((receivedMessage = client.receiveMessage()) != null 
            && !receivedMessage.isEmpty()) {
          System.out.println("Server: " + receivedMessage);
          clientMessage = System.console().readLine("client> ");
          client.sendMessage(clientMessage);
        }
        System.out.println("disconnecting...");
        client.disconnect();
      } catch(Exception e) {
        System.out.println("Error: " + e);
      }
  }

  private static String getUsageString() {
      return "Usage:\n"
      + "\tMain [Option]\n"
      + "\t--server, -s\n"
      + "\t--client, -c\n";
  }
}
