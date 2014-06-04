package Model.ControlServer;

import static Constanses.ServerConfig.controlPort;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControlServer implements Runnable
  {

    private ExecutorService executor;

    public ControlServer()
      {
        executor = Executors.newFixedThreadPool(20);

        
      }

    @Override
    public void run()
      {
        try
          {
            ServerSocket controlServerSocket = new ServerSocket(controlPort);

            while (true)
              {
                Socket socket = controlServerSocket.accept();
                ControlServerHandler handler = new ControlServerHandler(socket);
                executor.submit(handler);
              }
          }
        catch (IOException ex)
          {
            Logger.getLogger(ControlServer.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
  }
