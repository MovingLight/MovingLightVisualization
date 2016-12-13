package DB;

import java.net.ServerSocket;

import socket.ServerSocketTest;

public class DBModule extends Thread {
	
	public void run(){
		
		System.out.println("DBModule Thread Start");
		DBManager mgr = DBManager.getInstance();
		mgr.insertCoords(ServerSocketTest.COORD);

 	}

}
