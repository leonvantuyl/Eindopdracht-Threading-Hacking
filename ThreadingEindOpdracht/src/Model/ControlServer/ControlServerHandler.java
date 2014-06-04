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
        System.out.println("new control server");
      }
    
  }
