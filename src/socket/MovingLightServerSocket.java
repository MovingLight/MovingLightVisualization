package socket;

import java.io.DataInputStream;
import java.io.EOFException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MovingLightServerSocket extends Thread {
	
	public static String COORD;
	public static ServerSocketTest instance;
	private ServerSocket serverSocket = null;
	private StringBuilder sb;
	

	private static int PORT = 5006;
	
	static {
		instance = new ServerSocketTest();
		instance.start();
	}

	boolean isRunning = false;
	public void run(){
		try {
			
			serverSocket = new ServerSocket(PORT);
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
	        	
	        	int read = -1;  
	        	boolean isFirst = true;
	         	sb = new StringBuilder();
	        	DataInputStream dInput = new DataInputStream(clientSocket.getInputStream());
	         	int cnt = 0;
	         	while( ( read = dInput.readInt()) != -1 ) {
	         		if (isFirst) {
	         			isFirst = !isFirst;
	         			sb.append(read);
	         			continue;
	         		}
	         		sb.append(",");
	         		sb.append(read);
	         		
	         		/*
	         		if (cnt == 3) {
	         			//log.fileClose();
	         		}
	         		cnt++;*/
	         	}
	     
	            isRunning=false;
	            dInput.close();
	        } catch (EOFException e){
	        	COORD = sb.toString();
	        	//log.writeLog(COORD);
	        	System.out.println("Input : " + COORD);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	public static void main(String[] args) {
		System.out.println(getTime() + " : Server is ready.");
	}

}
