package server;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.TestDAO;
import socket.ServerSocketTest;
import socket.StaticValue;

/**
 * Servlet implementation class ReplayServlet
 */
public class ReplayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	String replayFileName = "";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReplayServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    public static String[] coord = new String[99999];
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    private void replayFileOpen() {
    	
    	String sys = System.getProperty("user.home");
    	
    	FileInputStream fis = null;
    	BufferedReader reader = null;
    	int index=0;
    	
    	try {
    		
    		fis = new FileInputStream(sys + "\\Desktop\\MovingLight\\logs\\" + replayFileName);
            reader = new BufferedReader(new InputStreamReader(fis));
            
            String line = reader.readLine();
            while(line != null){
                System.out.println(line);
                line = reader.readLine();
                coord[index++]=line;
            }   
            
    		
    	} catch (Exception e) {
    		
    		e.printStackTrace();
    	}
    	
    	 finally {
             try {
                 reader.close();
                 fis.close();
             } catch (Exception ex) {
            	 ex.printStackTrace();
             }
         }
    	
    }
    public static int replayIndex=0;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// Replay From DB
		//String test_id = TestDAO.getInstance().getLastLog();
		//replayFileName = "VA_log_" + test_id + ".txt";
		
		
		// Replay From File
		replayFileName = "VA_log_TEST_20161221_124110.txt";
		//replayFileOpen();
		
		
		StringBuilder sb = new StringBuilder();
		
		int index = StaticValue.replayIndex;
		if(index<20) {
			for(int i=0;i<=index;i++) {
				sb.append(coord[i]+"^");
			}
			//System.out.println(index);
		} else if (index>=20) { // else濡쒕쭔 �빐以섎룄 �릺�뒗�뜲 紐낆떆�쟻�쑝濡� 議곌굔�쓣 �쟻�뼱以ъ뼱�슂.
			for(int i=index-19;i<=index+1;i++) {
				sb.append(coord[i]+"^");
			}
			index=20;
			//System.out.println(index);
		}
		
		
		
		System.out.println(" Index : " + StaticValue.replayIndex +  "  / replay SB : " + sb ) ;
		response.getWriter().printf(index+"/"+sb.toString().substring(0, sb.toString().length()-6));
		if(ServerSocketTest.index==StaticValue.replayIndex) {
			StaticValue.replayIndex=0;
			try {
				Thread.sleep(5000);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		StaticValue.replayIndex = StaticValue.replayIndex+2;
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}