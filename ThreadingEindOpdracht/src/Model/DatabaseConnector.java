package Model;

import static Constanses.ServerConfig.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnector
  {
    public static Connection connection;

    public DatabaseConnector() throws ClassNotFoundException
      {
        try
          {
            Class.forName("com.mysql.jdbc.Driver");
            openConnection();
          }
        catch (ClassNotFoundException ex)
          {
            System.err.println("MySQL Driver not found.");
          }
      }

    //open database connection
    public void openConnection()
      {
        try
          {
            connection = DriverManager.getConnection((databaseHost + databaseName), databaseUsername, databasePassword);
          }
        catch (SQLException ex)
          {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
          }
      
      }
    
    //close db connection
    public void closeConnection()
      {
        try
          {
            connection.close();
          }
        catch (SQLException ex)
          {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
  }
