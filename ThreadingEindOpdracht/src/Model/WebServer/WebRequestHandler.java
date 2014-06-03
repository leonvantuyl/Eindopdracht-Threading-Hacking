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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
      }
    
  }
