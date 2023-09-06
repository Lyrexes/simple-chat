public class Main {
  private final static int PORT = 8080;

  public static void main(String[] args) {
    if(args.length == 0) {
      System.out.println("Error: No arguments provided!\n" + getUsageString());
    } else if(args[0].equals("--server") || args[0].equals("-s")) {
      executeServerChatInstance();
    } else if(args[0].equals("--client") || args[0].equals("-c")) {
      executeClientChatInstance(parseIP(args));
    } else {
      System.out.println("Error: Invalid argument!\n" + getUsageString());
    }
  }

  private static void executeServerChatInstance() {
      Server server = new Server();
      try {
          server.listen(PORT);
      } catch(Exception e) {
        System.out.println("Error: " + e);
        server.close();
      }
  }

  private static String parseIP(String[] args) {
    if(args.length == 2) {
      return args[1];
    } 
    return "127.0.0.1";
  }

  private static void executeClientChatInstance(String ip) {
      Client client = new Client();
      try {
        client.connectTo(ip, PORT);
      } catch(Exception e) {
        System.out.println("Error: " + e);
        client.disconnect();
      }
  }

  private static String getUsageString() {
      return "Usage:\n"
      + "\tMain [Option]\n"
      + "\t--server, -s\n"
      + "\t--client, -c [IP-Adress]\n";
  }
}
