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
    	
    	// Set server END_TIME
    	StaticValue.END_TIME = new Date(System.currentTimeMillis());
    	System.out.println(new SimpleDateFormat("[yyyy-MM-dd hh:mm:ss]").format(StaticValue.END_TIME) + " : Web Server destroyed.");
    	
    	// Log File write
    	logfileWrite();
    	
    	// Update server END_TIME to DataBase.
    	DBManager.getInstance().endTest();
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
    	System.out.println("Web Server initiated.");
    	
    	// Initiate and Launch the ServerSocket
    	ServerSocketTest.instance.start();
    	// Set server START_TIME
    	StaticValue.START_TIME = new Date(System.currentTimeMillis());
    	
    	// Insert the server START_TIME and TEST_ID to DataBase.
    	DBManager.getInstance().insertTest();
    }
    
    public void logfileWrite() {
    	
    	try {
    		// Get home Dir path on your device.
    		if(false ) {
	    		String sys = System.getProperty("user.home");
	        	PrintWriter pw = new PrintWriter(sys + "\\Desktop\\MovingLight\\logs\\VA_log_TEST_" + getTime() + ".txt");
	        	// LogFile Write from StaticValue.COORD_LIST
	        	for(String log : StaticValue.COORD_LIST) {
	        		pw.println(log);
	        	}
	        	pw.close();
    		}
        	
    	} catch (Exception e) {}
    }
    
    public String getTime(){
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd_HHmmss"); 
        return f.format(StaticValue.START_TIME);
    }	
	
}
