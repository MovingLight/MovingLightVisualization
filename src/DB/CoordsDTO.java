package DB;

public class CoordsDTO {
	
	private int pId;
	private int x;
	private int y;
	
	public CoordsDTO(){}
	public CoordsDTO(int pId, int x, int y) {
		super();
		this.pId = pId;
		this.x = x;
		this.y = y;
	}
	
	public int getpId() {
		return pId;
	}
	
	public void setpId(int pId) {
		this.pId = pId;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "CoordsDTO [pId=" + pId + ", x=" + x + ", y=" + y + "]";
	}
	
}
