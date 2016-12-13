package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

public class CoordsDAO {

	private static CoordsDAO instance;
	private DBConnection conn;
	
	static {
		instance = new CoordsDAO();
	}
	private CoordsDAO() {
		// TODO Auto-generated constructor stub
		conn=DBConnection.getInstance();
	}
	public static CoordsDAO getInstance() {
		return instance;
	}
	
	public int insertCoord(String testId, CoordsDTO coord, Timestamp c_time) {
		return insertCoord(testId, coord.getpId(), coord.getX(), coord.getY(), c_time);
	}
	
	public int insertCoord(String testId, int pId, int x, int y, Timestamp c_time) {
		
		System.out.println("INSERT COORD to DB : " + testId + " / " + pId + " / " + x + " / " + y + " / " + c_time);
		
		PreparedStatement pstmt=null;
		int row = 0; 
		try
		{
			String sql = "INSERT INTO coords VALUES(?,?,?,?,?);";
			Connection con = conn.getConn();
			pstmt=con.prepareStatement(sql);
			
			pstmt.setString(1,testId);
			pstmt.setInt(2,pId);
			pstmt.setInt(3,x);
			pstmt.setInt(4,y);
			pstmt.setTimestamp(5,c_time);
			
			row=pstmt.executeUpdate();
			con.commit(); 
			/*if(row!=0)
			{
				conn.commit(); 
			}
*/
		}
		catch(Exception se){
			System.out.println(se.getMessage());
		}finally{
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (Exception se) {
				System.out.println(se.getMessage());
			}
		} 		
		return row; 	
	}
	
}
