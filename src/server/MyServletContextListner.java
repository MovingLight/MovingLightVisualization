package server;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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
    	logfileWrite();
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    	ServerSocketTest.instance.start();
    }
    
    public void logfileWrite() {
    	
    	try {
        	PrintWriter pw = new PrintWriter("C:\\Users\\HP15FHD\\Desktop\\MovingLight\\logs\\VA_log_" + getTime() + ".txt");
        	for(String log : StaticValue.COORD_LIST) {
        		pw.println(log);
        	}
        	pw.close();
        	
    	} catch (Exception e) {}
    }
    
    public static String getTime(){
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss"); 
        return f.format(new Date());
    }	
	
}
