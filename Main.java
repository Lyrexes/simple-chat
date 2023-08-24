import java.io.IOException;

public class Main {
  public static void main(String[] args) {
    System.out.println(args[0]);
    if(args[0].equals("--server") || args[0].equals("-s")) {
      Server server = new Server();
      try {
        server.listen(8080);
        server.stop();
      } catch(IOException e) {
        System.out.println("Error: " + e);
      }
    } else if(args[0].equals("--client") || args[0].equals("-c")) {
      Client client = new Client();
      try {
        client.startConnection("127.0.0.1", 8080);
        client.sendMessage("ping");
      } catch(IOException e) {
        System.out.println("Error: " + e);
      }
    } else {
      System.out.println("Eror: Invalid argument");
    }
  }
  
}
