import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.nio.charset.StandardCharsets;

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
  }

  public String getConnectedIP() throws Exception {
    if(connection != null) {
      return connection.getInetAddress().toString();
    }
    throw new Exception("Server has not connected to a Client yet!");
  }

  public void sendMessage(String message) throws IOException {
      out.println(message);
  }

  public String receiveMessage() throws IOException {
      return in.readLine();
  }
  
  public void close() throws IOException{
    in.close();
    out.close();
    connection.close();
    serverSocket.close();
  }

}
 
