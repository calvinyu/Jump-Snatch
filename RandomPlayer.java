package javagame;

import java.util.Random;

public class RandomPlayer {
	
	private Coordinate start = new Coordinate();
	private Coordinate end = new Coordinate();
	private Random generator = new Random();
	private int w, l;
	private int area;
	private int rand;
	public RandomPlayer(int width, int height){
		w = width;
		l = height;
	}
	public void reset(int width, int height){
		w = width;
		l = height;
	}
	
	public int move(Kernel k){
		System.out.println("X");
		int legal = get_move(k, start, end);
		if(legal == 1){
			k.move(1, start, end);
		}
		return legal;
	}
	
	//input: k
	//output: start, end
	public int get_move(Kernel k, Coordinate start, Coordinate end){
		int idx;
		area = w*l;
		
		// try to find a piece to remove
		rand = generator.nextInt(area);
		if(k.getRun() == 0){
			idx = rand;
			start.set(idx%w, idx/w);
			return 1;
		}
		// try to find a piece that is able to jump
		rand = generator.nextInt(area);
		for(int i = 0; i < area; i++){
			idx = (rand + i)%area;
			start.set(idx%w, idx/w);			
			if(k.jumpable(start,end) == 1){
				return 1;
			}
		}
		// try to find a piece that is able to move and near center
		rand = generator.nextInt(area);
		for(int i = 0; i < area; i++){
			idx = (rand + i)%area;
			start.set(idx%w, idx/w);
			//System.out.println("try"+ " start.x:" + start.x + " start.y:" + start.y);
			//System.out.println("movable:" + k.movable(start,end, 2) + " tocenter:" + k.tocenter(start, end));
			if(k.movable(start,end, 2) == 1){
				return 1;
			}
		}
		
		// try to find a piece that does not move away from center
		rand = generator.nextInt(area);
		for(int i = 0; i < area; i++){
			idx = (rand + i)%area;
			start.set(idx%w, idx/w);
			//System.out.println("try"+ " start.x:" + start.x + " start.y:" + start.y);
			//System.out.println("movable:" + k.movable(start,end, 1) + " tocenter:" + k.tocenter(start, end));
			if(k.movable(start,end, 1) == 1){
				return 1;
			}
		}
		
		// try to find a piece that is able to move
		rand = generator.nextInt(area);
		for(int i = 0; i < area; i++){
			idx = (rand + i)%area;
			start.set(idx%w, idx/w);
			if(k.movable(start,end, 0) == 1){
				return 1;
			}
		}
		return 0;
		
	}
	
	public int getArea() {
		return area;
	}
}
