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
	        try {
	        	int read = -1; 
	        	int cnt=0, person_num=0;
	        	boolean isFirst = true;
	         	sb = new StringBuilder(); // ClientSocket으로부터의 Data를 Append 하여 저장.  
	        	DataInputStream dInput = new DataInputStream(clientSocket.getInputStream());
	         	
	        	// ClientSocket으로부터의 Data를 Integer(4 Bytes) 단위로 끊어 읽음
	         	while( ( read = dInput.readInt()) != -1 ) {
	         		
	         		// 첫번째 Data = 공연자 수
	         		if (isFirst) {
	         			isFirst = !isFirst;
	         			// 공연자 수 세팅
	         			person_num = read; 
	         			// // ClientSocket으로부터의 공연자 수 Data를 Append.
	         			sb.append(read);
	         			continue;
	         		}
	         		cnt++;
	         		// 공연자 수 만큼 Data를 읽어왔을 경우, 더이상 읽지 않도록한다.  
	         		if ( cnt > person_num*3 ) 
	         			continue;
	         		
	         		// ClientSocket으로부터의 좌표값 Data를 Append.
	         		sb.append(","+read);
	         		
	         	}
	     
	            isRunning=false;
	            dInput.close();
	        } 
	        // ClientSocket으로부터의 좌표 값 읽기 완료
	        catch (EOFException e){
	        	// COORD에 ClientSocket으로부터의 좌표 값 대입
	        	COORD = sb.toString();
	        	coord[index++]=COORD;
	        	
	        	System.out.println("개수--"+index);
//	        	System.out.println("DATA : " + COORD);
	        	
	        	// ClientSocket으로부터의 좌표 값 도착시간
	        	StaticValue.COORD_TIME = System.currentTimeMillis();
	        	// COORD_LIST에 ClientSocket으로부터의 좌표 값 Add
	        	StaticValue.COORD_LIST.add(COORD);
	        	
	        	// ClientSocket으로부터의 좌표 값을
	        	//  DB에 저장하는 Thread 동작
	        	new Thread(new DBModule()).start();
	        	
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	public static void main(String[] args) {
		System.out.println(getTime() + " : Server is ready.");
	}

}
