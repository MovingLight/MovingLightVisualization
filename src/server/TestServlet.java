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
		if(index<20) {
			for(int i=0;i<=index;i++) {
				sb.append(ServerSocketTest.coord[i]+"^");
			}
			System.out.println(index);
		} else if (index>=20) { // else로만 해줘도 되는데 명시적으로 조건을 적어줬어요.
			for(int i=index-19;i<=index+1;i++) {
				sb.append(ServerSocketTest.coord[i]+"^");
			}
			System.out.println(index);
		}
		//System.out.println(index+"/"+sb);
		response.getWriter().printf(index+"/"+sb.toString());
	}

}

