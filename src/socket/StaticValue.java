package socket;
import java.util.ArrayList;
import java.util.Date;

public class StaticValue {

	// ClientSocket으로부터의 모든 좌표 값 리스트   //로그 파일 출력용으로 사용
	public static ArrayList<String> COORD_LIST = new ArrayList<String>();
	
	public static long COORD_TIME;	// ClientSocket으로부터의 좌표 값 도착시간
	public static Date START_TIME; 	// 테스트 시작시간
	public static Date END_TIME; 	// 테스트 종료시간
	
	public static int replayIndex = 0;
	
	public StaticValue() {}
	
}
