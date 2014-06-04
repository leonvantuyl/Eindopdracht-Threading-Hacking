package Model.WebServer;

import java.net.Socket;

public class WebRequestHandler implements Runnable
  {
    private final Socket socket;
    
    WebRequestHandler(Socket socket)
      {
        this.socket = socket;
      }

    @Override
    public void run()
      {
        System.out.println("new connection");
      }
    
  }
