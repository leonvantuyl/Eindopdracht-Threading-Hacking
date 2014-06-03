package Model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class WebServer
  {
    public WebServer(int serverPort)
      {
        try
          {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            
            while(true)
              {
                Socket socket = serverSocket.accept();
                
                WebRequestHandler handler = new WebRequestHandler(socket);
                
                handler.run();
              }
          }
        catch (IOException ex)
          {
            Logger.getLogger(WebServer.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
  }
