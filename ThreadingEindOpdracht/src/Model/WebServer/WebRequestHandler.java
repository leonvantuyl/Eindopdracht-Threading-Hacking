package Model.WebServer;

import Constanses.ServerConfig;
import Controller.AuthController;
import Model.ControlServer.ControlServerHandler;
import Model.Logger.LoggerQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WebRequestHandler implements Runnable {

    private final Socket socket;
    private String userIp;
    private String date;
    private String userRequestUrl;

    WebRequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("new connection");

        try {
            readHeader();
            switch (userRequestUrl) {
                case "/": {
                    //Hier is het een string omdat de pagina dynamisch word gemaakt.
                    String file = createIndex();
                    Loadpage(file);
                }
                break;
                    case "/favicon.ico":
                        break;
                default: {
                    File file = null;
                    File isAllowed = isAllowed(userRequestUrl);
                    if (isAllowed != null) {
                        file = isAllowed;
                    } else {
                        file = new File(new java.io.File("").getAbsolutePath() + "\\src\\View\\error\\404.html");
                    }
                    Loadpage(file);
                }
                break;
            }
        } catch (IOException ex) {
            Logger.getLogger(ControlServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        logInfo();
    }

    private void logInfo() {
        socket.getRemoteSocketAddress();
        date = new Date().toString();
    }

    private void readHeader() throws IOException {
        BufferedReader bufReader = null;
        InputStream is = socket.getInputStream();
        bufReader = new BufferedReader(new InputStreamReader(is));
        String inputLine;
        while (!(inputLine = bufReader.readLine()).equals("")) {
            System.out.println(inputLine);
            if (inputLine.startsWith("GET")) {
                String[] parts = inputLine.split(" ");
                userRequestUrl = parts[1];
            }
        }
    }

    public String createIndex() {
        String indexpage = null;
        StringBuilder sb = new StringBuilder();
        //standard pagina opzet
        sb.append("<!DOCTYPE html>\n" + "<html>\n"
                + "    <head>\n"
                + "        <title>Index</title>\n"
                + "        <meta charset=\"UTF-8\">\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    </head>\n"
                + "    <body>");
        /*
         Logica hier,
         finder classe zoekt alle bestanden met .html als extensie in de opgegeven directory.
         per gevonde bestand maakt die een link aan.
         Als directorybrowsing uitstaat dan krijg je een melding te zien.
         */
        if (ServerConfig.directoryBrowsing) {
            String rootDir = ServerConfig.webRoot;
            File[] webpages = finder(rootDir);
            sb.append("<ul>");
            for (int i = 0; i < webpages.length; i++) {
                String name = webpages[i].getName();
                // String link = rootDir + "/" + name;
                sb.append("<li> <a href=\"" + name + "\">" + name + "</a> </li>");
            }
            sb.append("</ul>");
        } else {
            sb.append("<h1> DirBrowsing turned off </h1>");
        }

        //sluiten pagina
        sb.append("   </body>\n" + "</html>");
        indexpage = sb.toString();
        return indexpage;
    }

    private void Loadpage(File file) {
        try {
            String text = new Scanner(file).useDelimiter("\\A").next();
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.print(text);
            out.flush();
            out.close();
            socket.close();

        } catch (IOException ex) {
            Logger.getLogger(ControlServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void Loadpage(String text) {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.print(text);
            out.flush();
            out.close();
            socket.close();

        } catch (IOException ex) {
            Logger.getLogger(ControlServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public File[] finder(String dirName) {
        File dir = new File(dirName);
        return dir.listFiles((File dir1, String filename) -> filename.endsWith(".html"));
    }

    private File isAllowed(String userRequestUrl) {
        File allowed = null;
        String root = ServerConfig.webRoot;
        File[] webpages = finder(root);
        for (int i = 0; i < webpages.length; i++) {
            String name = "/" + webpages[i].getName();
            //TODO wat je hier moet vergelijken
            if (userRequestUrl.equals(name)) {
                allowed = new File(root + userRequestUrl);
                i = webpages.length;
            }
        }
        return allowed;
    }

}
