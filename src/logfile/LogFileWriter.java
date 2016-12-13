package logfile;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogFileWriter {

	private PrintWriter pw;
	
	public LogFileWriter() {}
	
	public void fileOpen() {
		try {
			pw = new PrintWriter("C:\\Users\\HP15FHD\\Desktop\\MovingLight\\logs\\VA_log_" + getTime() + ".txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeLog(String data) {
		pw.println(data);
	}
	
	public void fileClose() {
		pw.close();
	}

	public static String getTime(){
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss"); 
        return f.format(new Date());
    }	
	
	public static void main(String[] args) throws IOException {
		LogFileWriter lf = new LogFileWriter();
        //PrintWriter pw = new PrintWriter("c:/out.txt");
        for(int i=1; i<11; i++) {
            String data = i+" ��° ���Դϴ�.";
            lf.pw.println(data);
        }
        lf.pw.close();
    }
}
