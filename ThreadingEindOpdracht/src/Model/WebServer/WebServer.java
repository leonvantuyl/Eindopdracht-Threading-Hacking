package Model.WebServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;


public class WebServer
  {
    private ExecutorService executor;
    
    public WebServer(int serverPort)
      {
        executor = Executors.newFixedThreadPool(20);
        
        try
          {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            
            while(true)
              {
                Socket socket = serverSocket.accept();
                WebRequestHandler handler = new WebRequestHandler(socket);
                executor.submit(handler);
              }
          }
        catch (IOException ex)
          {
            Logger.getLogger(WebServer.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
  }
