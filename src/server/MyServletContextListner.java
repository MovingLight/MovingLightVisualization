package server;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import DB.DBManager;
import socket.ServerSocketTest;
import socket.StaticValue;

/**
 * Application Lifecycle Listener implementation class MyServletContextListner
 *
 */
public class MyServletContextListner implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public MyServletContextListner() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    	System.out.println("Web Server destroyed.");
    	StaticValue.END_TIME = new Date(System.currentTimeMillis());
    	logfileWrite();
    	
    	DBManager.getInstance().endTest();
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    	System.out.println("Web Server initiated.");
    	
    	ServerSocketTest.instance.start();
    	StaticValue.START_TIME = new Date(System.currentTimeMillis());
    	
    	DBManager.getInstance().insertTest();
    }
    
    public void logfileWrite() {
    	
    	try {
    		String sys = System.getProperty("user.home");
        	PrintWriter pw = new PrintWriter(sys + "\\Desktop\\MovingLight\\logs\\VA_log_TEST_" + getTime() + ".txt");
        	for(String log : StaticValue.COORD_LIST) {
        		pw.println(log);
        	}
        	pw.close();
        	
    	} catch (Exception e) {}
    }
    
    public String getTime(){
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd_HHmmss"); 
        return f.format(StaticValue.START_TIME);
    }	
	
}
