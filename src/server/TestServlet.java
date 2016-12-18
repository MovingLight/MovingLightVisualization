package server;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import socket.ServerSocketTest;

/**
 * Servlet implementation class TestServlet
 */
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().printf(CoordinateServerSocket.COORD + "");
		StringBuilder sb = new StringBuilder();
		int index = ServerSocketTest.index;
		if(index>=19)
			index=19;
		for(int i=0;i<index;i++) {
			sb.append(ServerSocketTest.coord[i]+"^");
		}
		System.out.println(index+"/"+sb);
		response.getWriter().printf(index+"/"+sb.toString());
	}

}

