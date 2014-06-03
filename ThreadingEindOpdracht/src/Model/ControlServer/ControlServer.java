package Model.ControlServer;

import Model.WebServer.WebRequestHandler;
import Model.WebServer.WebServer;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import static Constanses.ServerConfig.*;

public class ControlServer
  {

    private ExecutorService executor;

    public ControlServer()
      {
        executor = Executors.newFixedThreadPool(20);

        try
          {
            ServerSocket serverSocket = new ServerSocket(controlPort);

            while (true)
              {
                Socket socket = serverSocket.accept();
                ControlServerHandler handler = new ControlServerHandler(socket);
                executor.submit(handler);
              }
          }
        catch (IOException ex)
          {
            Logger.getLogger(WebServer.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
  }
