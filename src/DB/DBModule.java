package DB;

import java.net.ServerSocket;

import socket.ServerSocketTest;

public class DBModule extends Thread {
	
	public void run(){
		
		// ClientSocket���κ����� ��ǥ ���� DB�� ����
		//System.out.println("DBModule Thread Start");
		DBManager mgr = DBManager.getInstance();
		mgr.insertCoords(ServerSocketTest.COORD);

 	}

}
