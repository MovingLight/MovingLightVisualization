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
			//System.out.println(index);
		} else if (index>=20) { // else濡쒕쭔 �빐以섎룄 �릺�뒗�뜲 紐낆떆�쟻�쑝濡� 議곌굔�쓣 �쟻�뼱以ъ뼱�슂.
			for(int i=index-19;i<=index+1;i++) {
				sb.append(ServerSocketTest.coord[i]+"^");
			}
			index=20;
			//System.out.println(index);
		}
		
		//System.out.println(index+"/"+sb);
		response.getWriter().printf(index+"/"+sb.toString().substring(0, sb.toString().length()-1));
	}

}

