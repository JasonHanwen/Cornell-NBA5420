import java.util.Calendar;
import java.util.Iterator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;
/**
 * @author hanwenwang
 * @version 1
 * @since 2016/3/19
 */
public class MyDaemonServletContextListener implements ServletContextListener {
	/**
	 * This is to build a daemon thread to clean the timeout userInfo
	 */
	public void contextInitialized(ServletContextEvent sce) {
		Thread th = new Thread() {
    	 public void run() {
    		 System.out.println("Entering run method");
             try {
            	 System.out.println("In run Method: currentThread() is" + Thread.currentThread());
            	 while (true) {
            		 System.out.print("The Daemon thread start working");
                     try {
                    	 Thread.sleep(500);
                     } catch (InterruptedException x) {
                    	 System.out.println("There is some error when the thread sleeps");
                     }
                        //This is to delete all the threads that expire after a specific date  
                     	Map<Integer, UserInfor> map = loginservlet.map;
                        
                        //This is to make the map is synchronized
                     synchronized(map){ 
                    	 //Loop all user information and delete the timeout session information
                    	 Calendar date = Calendar.getInstance();
                         Iterator iterator = map.keySet().iterator();
                         while(iterator.hasNext()){
                         int id = (Integer)(iterator.next());
                         UserInfor userInfor = map.get(id);
                         //If the expiration time of the userInformation is before the expiration time.
                         System.out.print("begin scan the Session with number of" + id);
                         if(userInfor.getExpiration().getTime().before(date.getTime())){
                        	System.out.print("delete one thread" + "the Id is" + id);
                        		map.remove(id);
                        	}
                        }
                     }
                }
             } finally {
                    System.out.println("Leaving run Method");
             }
          }
        };
        th.setDaemon(true);
        th.start();
    }
    public void contextDestroyed(ServletContextEvent sce) {
        // you could notify your thread you're shutting down if 
        // you need it to clean up after itself
    }
}