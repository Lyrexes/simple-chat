import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.IOException;

class Server {
  private ServerSocket serverSocket;
  private Socket connection;
  private BufferedReader in;
  private PrintWriter out;

  public void listen(int port) throws IOException {
      serverSocket = new ServerSocket(port);
      connection = serverSocket.accept();
      out = new PrintWriter(connection.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String message = in.readLine();
      System.out.println("client: " + message);
      if ("ping".equals(message)) {
          out.println("pong");
      } else {
          out.println("EROOR!");
      }
  }
  
  public void stop() throws IOException{
    in.close();
    out.close();
    connection.close();
    serverSocket.close();
  }

}
 
