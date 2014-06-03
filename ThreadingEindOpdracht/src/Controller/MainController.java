package Controller;

import static Constanses.ServerConfig.*;
import Model.WebServer;

public class MainController
  {
    private WebServer webserver;
    
    public MainController()
      {
        webserver = new WebServer(serverPort);
      }
  }
