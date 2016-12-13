package socket;

import java.io.DataInputStream;
import java.io.EOFException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import logfile.LogFileWriter;

public class ServerSocketTest extends Thread {
	
	public static ServerSocketTest instance;
	private ServerSocket serverSocket = null;
	private StringBuilder sb;
	
	private static int PORT = 5006;
	public static String COORD;
	
	static {
		instance = new ServerSocketTest();
		//instance.start();
	}

	boolean isRunning = false;
	public void run(){
		try {
			
			System.out.println(getTime() + " : Server is ready.");
			
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
	         	
	         	while( ( read = dInput.readInt()) != -1 ) {
	         		if (isFirst) {
	         			isFirst = !isFirst;
	         			sb.append(read);
	         			continue;
	         		}
	         		sb.append(","+read);
	         		
	         	}
	     
	            isRunning=false;
	            dInput.close();
	        } catch (EOFException e){
	        	COORD = sb.toString();
	        	
	        	System.out.println("Input : " + COORD);
	        	
	        	StaticValue.COORD_LIST.add(COORD);
	        	
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	public static void main(String[] args) {
		System.out.println(getTime() + " : Server is ready.");
	}

}
