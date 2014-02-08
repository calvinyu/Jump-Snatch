package javagame;

public class Coordinate {
	public int x;
	public int y;
	public Coordinate(){
		x = 0;
		y = 0;
	}
	public Coordinate(int x, int y){
		this.x = x;
		this.y = y;
	}
	public void set(int x, int y){
		this.x = x;
		this.y = y;
	}
	public String toString(){
		return "" + x + "," + y;
	}
}
