package DB;

import java.net.ServerSocket;

import socket.ServerSocketTest;

public class DBModule extends Thread {
	
	public void run(){
		
		// ClientSocket으로부터의 좌표 값을 DB에 저장
		//System.out.println("DBModule Thread Start");
		DBManager mgr = DBManager.getInstance();
		mgr.insertCoords(ServerSocketTest.COORD);

 	}

}
