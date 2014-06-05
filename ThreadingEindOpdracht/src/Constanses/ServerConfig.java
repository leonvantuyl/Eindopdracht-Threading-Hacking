package Constanses;


public class ServerConfig
  {
    //server info
    //public static final int serverIp = localhost;
    
    //Info die de controlpanel kan aanpassen
    public static int serverPort = 8000;
    public static int controlPort = 8001;
    public static String webRoot = "c:/webserver/webroot";
    public static String defaultpage = "index.html";
    public static Boolean directoryBrowsing = true;
    
    
    //database info
    public static final String databaseHost = "jdbc:mysql://databases.aii.avans.nl/";
    public static final String databaseName = "gagpvenn_db2";
    public static final String databaseUsername = "gagpvenn";
    public static final String databasePassword = "123abc456789"; 
    
    public static String getInfo()
    {
        String info = serverPort + "$" + controlPort + "$" + webRoot + "$" + defaultpage + "$" + directoryBrowsing.toString();        
        return info;
    }

  }
