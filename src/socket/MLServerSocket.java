package socket;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import DB.DBModule;

public class MLServerSocket extends Thread {
	
	public static ServerSocketTest instance;
	private ServerSocket serverSocket = null;
	private StringBuilder sb;
	
	private static int PORT = 5006;
	public static String COORD;
	public static String coord[] = new String[20000];
	public static int maxData = 20;
	public static int index=0;
	static {
		instance = new ServerSocketTest();
		//instance.start();
	}

	boolean isRunning = false;
	public void run(){
		try {
			
			System.out.println(getTime() + " : Socket Server is ready.");
			
			serverSocket = new ServerSocket(PORT);
	        while(true) {
	        	if(!isRunning) {
	        		new Thread(new WorkerRunnable(newSocket())).start();
	        		System.out.println("--------------------------------------------------\n" + getTime() + " : New Data from Client.");
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
	    	PrintWriter pw = null;
	        try {
	        	String sys = System.getProperty("user.home");
	        	int read = -1; 
	        	int inner = 0;
	        	int cnt=0, person_num=0;
	        	boolean isFirst = true;
	         	sb = new StringBuilder(); // ClientSocket�쑝濡쒕��꽣�쓽 Data瑜� Append �븯�뿬 ���옣.  
	        	DataInputStream dInput = new DataInputStream(clientSocket.getInputStream());
	         	
	        	while( true ) {
	        		
	        		read = dInput.readInt();
	        		person_num = read; 
	         		
         			sb.append(read);
         			//continue;
         			pw = new PrintWriter(sys + "\\Desktop\\MovingLight\\logs\\VA_log_TEST_" + getTime() + ".txt");
	        		for(int i=0; i<read*3; i++) {
	        			inner = dInput.readInt();
	        			sb.append(","+inner);
	        		}
	         		
	         		COORD = sb.toString();
	         		pw.append(COORD);
	         		pw.close();
		        	coord[index++]=COORD;
		        	
		        	System.out.println("Index--"+index);
		        	System.out.println("DATA : " + COORD);
		        	
		        	StaticValue.COORD_TIME = System.currentTimeMillis();
		        	StaticValue.COORD_LIST.add(COORD);
		        	
		        	new Thread(new DBModule()).start();
		        	
		        	sb.delete(0, sb.length());
	         	}
	        } 
	        catch (EOFException e){
	    
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	public static void main(String[] args) {
		System.out.println(getTime() + " : Server is ready.");
	}

}
