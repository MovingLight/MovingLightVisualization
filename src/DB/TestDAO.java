package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class TestDAO {

	private static TestDAO instance;
	private DBConnection conn;
	
	static {
		instance = new TestDAO();
	}
	private TestDAO() {
		// TODO Auto-generated constructor stub
		conn=DBConnection.getInstance();
	}
	public static TestDAO getInstance() {
		return instance;
	}
	
	public int insertTest(String testId, Timestamp startTime)
	{
		//System.out.println("START_TEST : " + testId + " / " + startTime);
		
		PreparedStatement pstmt=null;
		int row = 0; 
		try
		{
			String sql = "INSERT INTO test VALUES(?,?,?);";
			Connection con = conn.getConn();
			pstmt=con.prepareStatement(sql);
			
			pstmt.setString(1,testId);
			pstmt.setTimestamp(2,startTime);
			pstmt.setTimestamp(3,startTime);
			
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
	
	public int endTest(String testId, Timestamp startTime, Timestamp endTime) {
		
		//System.out.println("ENT_TEST : " + testId + " / " + startTime + " / " + endTime);
		
		PreparedStatement pstmt=null;
		int row = 0; 
		try
		{
			String sql = "UPDATE test SET start_time = ?, end_time = ? WHERE test_id = ?;";
			
			Connection con = conn.getConn();
			pstmt=con.prepareStatement(sql);
			
			pstmt.setTimestamp(1,startTime);
			pstmt.setTimestamp(2,endTime);
			pstmt.setString(3,testId);
			
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
	
	public String getLastLog() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String lastLogName = "";
		try {
		String sql = "select * from test order by start_time desc limit 1";
		Connection con = conn.getConn();
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		while(rs.next()) {
		return rs.getString("test_id");
		}
		} catch(Exception ex) {
		}
		return null;
		}
}
