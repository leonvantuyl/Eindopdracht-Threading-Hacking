package Model.Logger;

public class LoggerQueue
  {

    private final String[] log = new String[10];
    private int freeSpot = 0;
    private static final Object logLock = new Object();

    public LoggerQueue()
      {

      }

    public void writeToLog(String message)
      {
        synchronized (logLock)
          {
            log[freeSpot] = message;
            freeSpot++;
          }
      }

    public String getFromLog()
      {
        String logMessage = "";

        synchronized (logLock)
          {
            logMessage = log[0];

            for (int i = 0; i < log.length; i++)
              {
                log[i] = log[i++];
              }
            log[log.length] = "";
          }

        freeSpot--;

        return logMessage;
      }
  }
