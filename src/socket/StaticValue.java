package socket;
import java.util.ArrayList;
import java.util.Date;

public class StaticValue {

	// ClientSocket���κ����� ��� ��ǥ �� ����Ʈ   //�α� ���� ��¿����� ���
	public static ArrayList<String> COORD_LIST = new ArrayList<String>();
	
	public static long COORD_TIME;	// ClientSocket���κ����� ��ǥ �� �����ð�
	public static Date START_TIME; 	// �׽�Ʈ ���۽ð�
	public static Date END_TIME; 	// �׽�Ʈ ����ð�
	
	public static int replayIndex = 0;
	
	public StaticValue() {}
	
}
