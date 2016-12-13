package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MovingLightServerSocket extends Thread {
	
	public static MovingLightServerSocket instance;
	private ServerSocket serverSocket = null;
	
	private static int PORT = 5006;
	public static String COORD;
	
	static {
		instance = new MovingLightServerSocket();
		instance.start();
	}

	boolean isRunning = false;
	public void run(){
		try {
			
			serverSocket = new ServerSocket(PORT);
	        //InetAddress clientAddress = socket.getInetAddress(); 
	        //System.out.println(getTime() + " : " + clientAddress + " Client connected.");
	        //COORD = "Hello, " + clientAddress + ".";
	        while(true) {
	        	if(!isRunning) {
	        		new Thread(new WorkerRunnable(newSocket())).start();
	        		System.out.println(getTime() + " : New Client Binded.");
	        	}
	        }
	       
		} catch(Exception e){
            e.printStackTrace();
        }
 	}
	private Socket newSocket() throws Exception{
	
        return serverSocket.accept();
	}
	
	public static String getTime(){
        SimpleDateFormat f = new SimpleDateFormat("[yyyy-MM-dd hh:mm:ss]"); 
        return f.format(new Date());
    }
	
	private class WorkerRunnable implements Runnable {

	    protected Socket clientSocket = null;
	    protected String mMsgFromClient = null;

	    public WorkerRunnable(Socket clientSocket) {
	        this.clientSocket = clientSocket;
	    }

	    public void run() {
	        try {
	        	
	            BufferedReader in = new BufferedReader(new InputStreamReader(
	                    clientSocket.getInputStream()));

	            while ((mMsgFromClient = in.readLine()) != null) {
	            	System.out.println("MSG From Client : " + mMsgFromClient);
	            	COORD = /*getTime() + " : " + */mMsgFromClient;
	            	System.out.println(COORD);
	            }
	            isRunning=false;
	            in.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	    }
	}
	
	public static void main(String[] args) {
		System.out.println(getTime() + " : Server is ready.");
	}

}
