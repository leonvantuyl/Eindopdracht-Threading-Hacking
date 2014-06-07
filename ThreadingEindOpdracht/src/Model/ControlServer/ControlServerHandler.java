package Model.ControlServer;

import Constanses.ServerConfig;
import Controller.AuthController;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
    private String body;

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
                break;
                case "/login":
                  {
                    AuthController authController = new AuthController();
                    
                    String[] loginInfo = body.split(",");
                    String token = authController.login(loginInfo[0], loginInfo[1]);
                    
                    if (token != null)
                      {
                        //send token back to user
                        File file = new File(new java.io.File("").getAbsolutePath() + "\\src\\View\\ControlPanel.html");
                        Loadpage(file);
                      }
                    else
                      {
                        //faild send error page
                      }
                  }
                break;
                case "/controlPanel/config":
                  {
                    sendConfigInfo();
                  }
                break;
                case "/controlPanel/configPost":
                  {
                    postConfigInfo();
                  }
                break;
                default:
                  {
                    File file = new File(new java.io.File("").getAbsolutePath() + "\\src\\View\\error\\404.html");
                    Loadpage(file);
                  }
                break;
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
        InputStream is = socket.getInputStream();
        bufReader = new BufferedReader(new InputStreamReader(is));
        String inputLine;
        int contentLength = 0;

        while (!(inputLine = bufReader.readLine()).equals(""))
          {
            System.out.println(inputLine);
            if (inputLine.startsWith("GET") || inputLine.startsWith("POST"))
              {
                String[] parts = inputLine.split(" ");
                userRequestUrl = parts[1];
              }

            else if (inputLine.startsWith("Content-Length: "))
              { // get the content-length
                int index = inputLine.indexOf(':') + 1;
                String len = inputLine.substring(index).trim();
                contentLength = Integer.parseInt(len);
              }
          }
        if (contentLength > 0)
          {
            byte[] bytes = new byte[contentLength];
            for (int i = 0; i < contentLength; i++)
              {
                bytes[i] = (byte) bufReader.read();
              }
            body = new String(bytes);
          }
      }

    private void Loadpage(File file)
      {
        try
          {
            String text = new Scanner(file).useDelimiter("\\A").next();
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.write(text);
            out.close();

          }
        catch (IOException ex)
          {
            Logger.getLogger(ControlServerHandler.class.getName()).log(Level.SEVERE, null, ex);
          }
      }

    private void sendConfigInfo()
      {
        try
          {
            //TODO check wat de delimiter doet
            String text = ServerConfig.getInfo();
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.write(text);
            out.close();

          }
        catch (IOException ex)
          {
            Logger.getLogger(ControlServerHandler.class.getName()).log(Level.SEVERE, null, ex);
          }
      }

    private void postConfigInfo() throws IOException
      {
        System.out.println("yo");
        String[] newServerInfo = body.split(",");
        ServerConfig.setInfo(newServerInfo);
      }

  }
