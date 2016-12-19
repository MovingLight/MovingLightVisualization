package DB;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import socket.StaticValue;

public class DBManager {

	private static DBManager instance;
	private TestDAO testDao;
	private CoordsDAO coordsDao;
	private String testId;
	
	static {
		instance = new DBManager();
	}
	private DBManager() {
		// TODO Auto-generated constructor stub
		testDao=TestDAO.getInstance();
		coordsDao=CoordsDAO.getInstance();
	}
	public static DBManager getInstance() {
		return instance;
	}
	
	public int insertCoords(String coords) {
		
		System.out.println("DBManager Insert : " + coords);
		
		int cnt=0;
		int val=0;
		CoordsDTO coord = null;
		for(String str: coords.split(",")) {
			val = Integer.parseInt(str);
			if(cnt==0) {
				cnt++;
				continue;
			}
			
			if(cnt%3 == 1) {
				coord = new CoordsDTO();
				coord.setpId(val);
			} else if(cnt%3 == 2) {
				coord.setX(val);
			} else if(cnt%3 == 0) {
				coord.setY(val);
				
				coordsDao.insertCoord(testId, coord, new Timestamp(StaticValue.COORD_TIME));
			}
			cnt++;
		}
		
		return 1;
	}
	
	public int insertTest() {
		testId = generateTestID();
		return testDao.insertTest(testId, new Timestamp(StaticValue.START_TIME.getTime()));
	}
	
	public int endTest() {
		return testDao.endTest(testId, new Timestamp(StaticValue.START_TIME.getTime()) ,new Timestamp(StaticValue.END_TIME.getTime()));
	}
	
	private String generateTestID() {
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd_HHmmss"); 
        return "TEST_" + f.format(StaticValue.START_TIME);
	}
	
}
