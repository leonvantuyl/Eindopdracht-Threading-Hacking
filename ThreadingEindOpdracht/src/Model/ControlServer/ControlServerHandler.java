package Model.ControlServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
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
        try
          {
            readHeader();

            switch (userRequestUrl)
              {
                case "/":
                  {
                    File file = new File(new java.io.File("").getAbsolutePath() + "\\src\\View\\login.html");
                    Loadpage(file);
                  }
                case "/login":
                  {
                    //check login
                    File file = new File(new java.io.File("").getAbsolutePath() + "\\src\\View\\ControlPanel.html");
                    Loadpage(file);
                  }
                default:
                  {
                    File file = new File(new java.io.File("").getAbsolutePath() + "\\src\\View\\error\\404.html");
                    Loadpage(file);
                  }
              }

            socket.close();
          }
        catch (IOException ex)
          {
            Logger.getLogger(ControlServerHandler.class.getName()).log(Level.SEVERE, null, ex);
          }

      }

    private void readHeader() throws IOException
      {
        BufferedReader bufReader = null;
        bufReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String inputLine;
        while (!(inputLine = bufReader.readLine()).equals(""))
          {
            if (inputLine.startsWith("GET"))
              {
                String[] parts = inputLine.split(" ");
                
                for(String part : parts)
                  {
                      System.out.println(part);
                  }
                
                userRequestUrl = parts[1];
              }
          }
        socket.isInputShutdown();
      }

    private boolean Loadpage(File file)
      {
        try
          {
            //TODO check wat de delimiter doet
            String text = new Scanner(file).useDelimiter("\\A").next();
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.write(text);
            out.close();

          }
        catch (IOException ex)
          {
            Logger.getLogger(ControlServerHandler.class.getName()).log(Level.SEVERE, null, ex);
          }
        return false;
      }
  }
