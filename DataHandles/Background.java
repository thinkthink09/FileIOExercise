package DataHandles;


import DataHandles.Threads;
import java.util.*;
import java.util.concurrent.*;
import javax.servlet.*;
import javax.servlet.annotation.WebListener;

@WebListener
public class Background implements ServletContextListener {

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        
        StaminaHandler staminahandle = new StaminaHandler();
        scheduler = Executors.newSingleThreadScheduledExecutor();
        Calendar now = new GregorianCalendar();
        Calendar nexthour = new GregorianCalendar();
        nexthour.add(Calendar.HOUR, 1);
        nexthour.set(Calendar.MINUTE, 0);
        nexthour.set(Calendar.SECOND, 0);
        nexthour.set(Calendar.MILLISECOND, 0);
//        long delay = nexthour.getTimeInMillis()-now.getTimeInMillis();
//        System.out.println(delay);
//        long onehour = 1000*60*60;
//        scheduler.scheduleWithFixedDelay(staminahandle, delay, onehour, TimeUnit.MILLISECONDS);
        // Test Use
        scheduler.scheduleWithFixedDelay(staminahandle, 0, 1000*60, TimeUnit.MILLISECONDS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }

    private static class StaminaHandler implements Runnable {
        @Override
        public void run() {
            Calendar now = new GregorianCalendar();
            if(now.get(Calendar.HOUR_OF_DAY)==8){
                System.out.println("Day Begins");
                Threads.DayBegin();
                Threads.NewsGenerator();
            }else if(now.get(Calendar.HOUR_OF_DAY)>8&&now.get(Calendar.HOUR_OF_DAY)<=23){
                Threads.StaminaRefill();
            }
        }
    }
}