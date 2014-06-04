package threadingeindopdracht;

import Controller.MainController;
import Model.DatabaseConnector;

public class ThreadingEindOpdracht
  {

    public static void main(String[] args)
      {
        DatabaseConnector dbConnection = new DatabaseConnector();
        MainController controller = new MainController();
      }

  }
