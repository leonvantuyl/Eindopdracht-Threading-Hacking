package Model.WebServer;

import static Constanses.ServerConfig.serverPort;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WebServer implements Runnable
  {

    private ExecutorService executor;

    public WebServer()
      {
        executor = Executors.newFixedThreadPool(20);

      }

    @Override
    public void run()
      {
        try
          {
            ServerSocket webServerSocket = new ServerSocket(serverPort);

            while (true)
              {
                Socket socket = webServerSocket.accept();
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
