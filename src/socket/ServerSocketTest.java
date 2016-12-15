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
	         	sb = new StringBuilder(); // ClientSocket���κ����� Data�� Append �Ͽ� ����. 
	        	DataInputStream dInput = new DataInputStream(clientSocket.getInputStream());
	         	
	        	// ClientSocket���κ����� Data�� Integer(4 Bytes) ������ ���� ����
	         	while( ( read = dInput.readInt()) != -1 ) {
	         		
	         		// ù��° Data = ������ ��
	         		if (isFirst) {
	         			isFirst = !isFirst;
	         			// ������ �� ����
	         			person_num = read; 
	         			// ClientSocket���κ����� ������ �� Data�� Append.
	         			sb.append(read);
	         			continue;
	         		}
	         		cnt++;
	         		// ������ �� ��ŭ Data�� �о���� ���, ���̻� ���� �ʵ����Ѵ�. 
	         		if ( cnt > person_num*3 ) 
	         			continue;
	         		
	         		// ClientSocket���κ����� ��ǥ�� Data�� Append.
	         		sb.append(","+read);
	         		
	         	}
	     
	            isRunning=false;
	            dInput.close();
	        } 
	        // ClientSocket���κ����� ��ǥ �� �б� �Ϸ�
	        catch (EOFException e){
	        	// COORD�� ClientSocket���κ����� ��ǥ �� ����
	        	COORD = sb.toString();
	        	System.out.println("DATA : " + COORD);
	        	
	        	// ClientSocket���κ����� ��ǥ �� �����ð�
	        	StaticValue.COORD_TIME = System.currentTimeMillis();
	        	// COORD_LIST�� ClientSocket���κ����� ��ǥ �� Add
	        	StaticValue.COORD_LIST.add(COORD);
	        	
	        	// ClientSocket���κ����� ��ǥ ����
	        	//  DB�� �����ϴ� Thread ����
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
