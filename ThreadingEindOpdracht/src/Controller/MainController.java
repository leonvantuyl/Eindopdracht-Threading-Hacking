package Controller;

import Model.ControlServer.ControlServer;
import Model.Logger.Logger;
import Model.WebServer.WebServer;

public class MainController
  {

    public MainController()
      {
        startServers();
      }

    private void startServers()
      {
        (new Thread(new WebServer())).start();
        (new Thread(new ControlServer())).start();
      }
  }
