package socket;

import java.io.DataInputStream;
import java.io.EOFException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;


import DB.DBModule;

public class ServerSocketTest extends Thread {
	
	public static ServerSocketTest instance;
	private ServerSocket serverSocket = null;
	private StringBuilder sb;
	
	private static int PORT = 5006;
	public static String COORD;
	public static String coord[] = new String[999999];
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
	        try {
	        	int read = -1; 
	        	int inner = 0;
	        	int cnt=0, person_num=0;
	        	boolean isFirst = true;
	         	sb = new StringBuilder(); // ClientSocket�쑝濡쒕��꽣�쓽 Data瑜� Append �븯�뿬 ���옣.  
	        	DataInputStream dInput = new DataInputStream(clientSocket.getInputStream());
	         	
	        	// ClientSocket�쑝濡쒕��꽣�쓽 Data瑜� Integer(4 Bytes) �떒�쐞濡� �걡�뼱 �씫�쓬
	         	//while( ( read = dInput.readInt()) != -1 ) {
	        	while( true ) {
	        		
	        		read = dInput.readInt();
	        		person_num = read; 
	         		
         			sb.append(read);
         			//continue;
	        		
	        		for(int i=0; i<read*3; i++) {
	        			inner = dInput.readInt();
	        			
	        			sb.append(","+inner);
	        			
	        			//System.out.println("Inner Data : "  + sb);
	        			
	        			
	        		}
	        		
	        		/*
	         		if (isFirst) {
	         			isFirst = !isFirst;
	         		
	         			person_num = read; 
	         		
	         			sb.append(read);
	         			continue;
	         		}
	         		cnt++;
	         		  
	         		if ( cnt > person_num*3 ) 
	         			continue;
	         		
	         		// ClientSocket�쑝濡쒕��꽣�쓽 醫뚰몴媛� Data瑜� Append.
	         		sb.append(","+read);*/
	         		
	         		//System.out.println("Out Data : "  +sb);
	        		//System.out.println("Index Before--"+index);
	         		
	         		COORD = sb.toString();
		        	coord[index++]=COORD;
		        	
		        	System.out.println("Index--"+index);
		        	System.out.println("DATA : " + COORD);
		        	
		        	StaticValue.COORD_TIME = System.currentTimeMillis();
		        	StaticValue.COORD_LIST.add(COORD);
		        	
		        	new Thread(new DBModule()).start();
		        	
		        	sb.delete(0, sb.length());
	         	}
	     
	         	
	         	
	            //isRunning=false;
	            //dInput.close();
	        } 
	        // ClientSocket�쑝濡쒕��꽣�쓽 醫뚰몴 媛� �씫湲� �셿猷�
	        catch (EOFException e){
	        	// COORD�뿉 ClientSocket�쑝濡쒕��꽣�쓽 醫뚰몴 媛� ���엯
	        	
	        	/*COORD = sb.toString();
	        	coord[index++%maxData]=COORD;
	        	
	        	System.out.println("媛쒖닔--"+index);
//	        	System.out.println("DATA : " + COORD);
	        	
	        	// ClientSocket�쑝濡쒕��꽣�쓽 醫뚰몴 媛� �룄李⑹떆媛�
	        	StaticValue.COORD_TIME = System.currentTimeMillis();
	        	// COORD_LIST�뿉 ClientSocket�쑝濡쒕��꽣�쓽 醫뚰몴 媛� Add
	        	StaticValue.COORD_LIST.add(COORD);*/
	        	
	        	// ClientSocket�쑝濡쒕��꽣�쓽 醫뚰몴 媛믪쓣
	        	//  DB�뿉 ���옣�븯�뒗 Thread �룞�옉
	        	//new Thread(new DBModule()).start();
	        	
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	public static void main(String[] args) {
		System.out.println(getTime() + " : Server is ready.");
	}

}
