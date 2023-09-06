import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TerminalInput implements Runnable {
  private Client client;

  public TerminalInput(Client client) {
    this.client = client;
  }

	@Override
	public void run() {
    try {
      sendTerminalInput();
    } catch(Exception e) {
      System.out.println("Error occured sending terminal input: " + e);
    }
	}

  void sendTerminalInput() throws IOException {
    String message;
    BufferedReader terminalReader = new BufferedReader(new InputStreamReader(System.in));
    while(client.isRunning()) {
      message = terminalReader.readLine();
      client.sendMessage(message);
      if(message.equals("/quit")) {
        client.stopRunning();
      }
    }
  }

}
