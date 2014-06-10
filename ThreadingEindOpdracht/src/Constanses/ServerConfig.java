package Constanses;

import java.util.concurrent.Semaphore;
import threadingeindopdracht.ReadWriteLock;

public class ServerConfig {
    //server info
    //public static final int serverIp = localhost;

    //Info die de controlpanel kan aanpassen
    //TODO Hier getter en setters voor maken met semaphore. anders gaat dat fout met threads.
    private static int serverPort = 8000;
    private static int controlPort = 8001;
    private static String webRoot = "c:/webserver/webroot";
    private static String defaultpage = "index.html";
    private static Boolean directoryBrowsing = true;

    //database info
    public static final String databaseHost = "jdbc:mysql://databases.aii.avans.nl/";
    public static final String databaseName = "gagpvenn_db2";
    public static final String databaseUsername = "gagpvenn";
    public static final String databasePassword = "123abc456789";

    public static ReadWriteLock lock = new ReadWriteLock();

    public static String getInfo() {
        String info = serverPort + "$" + controlPort + "$" + webRoot + "$" + defaultpage + "$" + directoryBrowsing.toString();
        return info;
    }

    public static void setInfo(String[] info) throws InterruptedException {
        lock.lockWrite();
        if (serverPort != Integer.parseInt(info[0])) {
            serverPort = Integer.parseInt(info[0]);
            //TODO restart webserver
        }
        if (controlPort != Integer.parseInt(info[1])) {
            controlPort = Integer.parseInt(info[1]);
            //TODO restart controlserver
        }
        webRoot = info[2];
        defaultpage = info[3];
        directoryBrowsing = info[4].equalsIgnoreCase("true");
        lock.unlockWrite();
    }

    public static int getserverPort() throws InterruptedException {
        lock.lockRead();
        int value = serverPort;
        lock.unlockRead();
        return value;
    }

    public static int getcontrolPort() throws InterruptedException {
        lock.lockRead();
        int value = controlPort;
        lock.unlockRead();
        return value;
    }

    public static String getwebRoot() throws InterruptedException {
        lock.lockRead();
        String value = webRoot;
        lock.unlockRead();
        return value;
    }

    public static String getdefaultpage() throws InterruptedException {
        lock.lockRead();
        String value = defaultpage;
        lock.unlockRead();
        return value;
    }

    public static Boolean getdirectoryBrowsing() throws InterruptedException {
        lock.lockRead();
        Boolean value = directoryBrowsing;
        lock.unlockRead();
        return value;
    }

}
