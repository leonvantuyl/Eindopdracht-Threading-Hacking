package Model.Logger;

import java.util.concurrent.Semaphore;
import static Constanses.QueueConstanses.*;

public class LoggerQueue
  {

    private final String[] log = new String[queueLength];
    private int freeSpot = 0;
    private final Semaphore goods_available = new Semaphore(0);
    private final Semaphore slots_available = new Semaphore(queueLength);
    private final Semaphore queueLock = new Semaphore(1);

    public LoggerQueue()
      {
        (new Thread(new Logger())).start();
      }

    public void writeToLog(String message) throws InterruptedException
      {
        queueLock.acquire();
        log[freeSpot] = message;
        freeSpot++;
        queueLock.release();
      }

    public String getFromLog() throws InterruptedException
      {
        String logMessage = "";
        
        queueLock.acquire();
        logMessage = log[0];

        for (int i = 0; i < log.length; i++)
          {
            log[i] = log[i++];
          }
        log[log.length] = "";
        queueLock.release();

        freeSpot--;

        return logMessage;
      }
  }
