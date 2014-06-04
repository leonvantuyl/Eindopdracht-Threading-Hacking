package Model.ControlServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControlServerHandler implements Runnable
  {

    private final Socket socket;
    private String userRequestUrl;

    public ControlServerHandler(Socket socket)
      {
        this.socket = socket;
      }

    @Override
    public void run()
      {
        System.out.println("new control server");

        readHeader();
      }

    private void readHeader()
      {
        BufferedReader bufReader = null;
        try
          {
            bufReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String inputLine;
            while (!(inputLine = bufReader.readLine()).equals(""))
              {
                if (inputLine.startsWith("Host: "))
                  {
                    userRequestUrl = inputLine.replaceAll("Host: ", "");
                  }
              }
            bufReader.close();
          }
        catch (IOException ex)
          {
            Logger.getLogger(ControlServerHandler.class.getName()).log(Level.SEVERE, null, ex);
          }
        finally
          {
            try
              {
                bufReader.close();
              }
            catch (IOException ex)
              {
                Logger.getLogger(ControlServerHandler.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
      }
  }
