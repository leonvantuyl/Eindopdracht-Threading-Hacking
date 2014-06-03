package Model.ControlServer;

import java.net.Socket;

public class ControlServerHandler implements Runnable
  {
    private Socket socket;
    
    public ControlServerHandler(Socket socket)
      {
        this.socket = socket;
      }
    
    @Override
    public void run()
      {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
      }
    
  }
