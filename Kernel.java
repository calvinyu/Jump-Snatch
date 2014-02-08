package javagame;
import static java.lang.Math.*;

import java.util.Random;


public class Kernel {
	private int width, height;
	private int[][] s;  
	private int run = 0;
	private int player = -1;
	private int score;
	private String msg = "";
	public Kernel(int m, int n){
		width = m;
		height = n;
		s = new int[m][n];
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				s[i][j] = 1;
			}
		}
	}
	public void set(int[][] bb){
		for(int i=0; i<width; ++i){
			for(int j=0; j<height; ++j){
				s[i][j] = bb[i][j];
			}
		}
	}
	public String getMessage(){
		return msg;
	}
	//return 0: illegal move
	//return 1: jump
	//return 2: move
	//return 3: first move
	//return 4: game over
	public  int check(int player, Coordinate start, Coordinate end, String mmsg){
		this.player = player;
		int legal = 0;
		msg = "";
		if(count() == width*height){
			legal = 3;
		}
		else if(count() <= 1){
			msg = "Game over!";
			legal = 4;
		}
		else{
			//if(player != run%2){
				//System.out.println("wrong player");
				//return false;
			//}
			if(start.x < 0 || start.x >= width || start.y < 0 || start.y >= height){
				//msg = "Wrong start coordinate!" + " start.x:" + start.x + " start.y:" + start.y;
				msg = "Wrong start coordinate!";
				return 0;
			}
			if(end.x < 0 || end.x >= width || end.y < 0 || end.y >= height){
				msg = "Wrong end coordinate!";
				return 0;
			}
			if(s[start.x][start.y] != 1){
				msg = "Start is empty!";
				return 0;
			}
			if(s[end.x][end.y] != 0){
				msg = "End is not empty!";
				return 0;
			}
			
			// check if it is a jump
			int xd = abs(start.x - end.x);
			int yd = abs(start.y - end.y);
			int dist = max(xd,yd);
			int dir  = min(xd,yd);
			
			// the middle of jump
			int xm = (start.x + end.x)/2;
			int ym = (start.y + end.y)/2;
			
			if(dist == 2){
				// this is a jump
				if(dir == 0 || dir == 2){
					if(s[xm][ym] == 1){
						legal = 1;
					}
					else{
						msg = "There is no piece in the middle!";
						return 0;
					}
				}
				else{
					//illegal move
					msg = "Illegal move!";
					return 0;
				}
				
			}
			else if(dist == 1){
				// this is a move
				// check if there is no any jump option
				for(int i = 0; i < width; i++){
					for(int j = 0; j < height; j++){
						Coordinate p = new Coordinate(i,j);
						Coordinate pj = new Coordinate();
						if(jumpable(p, pj) == 1){
							msg = "It is still able to jump.";
							return 0;
						}
					}
				}
				
				// check if the piece is move to the center
				if(dist_to_ctr(start) > dist_to_ctr(end)){
					legal = 2;
				}
				else{
					// check if it is possible to move to the center
					for(int i = 0; i < width; i++){
						for(int j = 0; j < height; j++){
							Coordinate p = new Coordinate(i,j);
							Coordinate pj = new Coordinate();
							if(movable(p, pj, 2) == 1){
								msg = "Please move to the center.";
								return 0;
							}
						}
					}
					
					if(dist_to_ctr(start) < dist_to_ctr(end)){
						// check if it is possible to move that does not change the distance to center
						for(int i = 0; i < width; i++){
							for(int j = 0; j < height; j++){
								Coordinate p = new Coordinate(i,j);
								Coordinate pj = new Coordinate();
								if(movable(p, pj, 1) == 1){
									msg = "Don't move away from center.";
									return 0;
								}
							}
						}
					}
					legal = 2;
				}
			}
			else{
				// illegal move
				msg = "Illegal move!";
				return 0;
			}
		}
		return legal;
	}
	
	public int getScore() {
		score = (width*height*height*width -run*(width+height)) + 50;
		return score;
	}
	
	public  int move2(int player, Coordinate start, Coordinate end, String mmsg){
		int legal;
		legal = check(player, start, end, mmsg);
		
		if(legal == 0 || legal == 4){
			return legal;
		}
		else if(legal == 1){
			//jump
			int xm = (start.x + end.x)/2;
			int ym = (start.y + end.y)/2;
			s[xm][ym] = 0;
			s[start.x][start.y]=0;
			s[end.x][end.y] = 1;
		}
		else if(legal == 2){
			//move
			s[start.x][start.y] = 0;
			s[end.x][end.y] = 1;
		}
		else if(legal == 3){
			//first move
			s[start.x][start.y] = 0;
		}
		//show();
		run ++;
		return legal;
	}
	public  boolean move(int player, Coordinate start, Coordinate end){
		String mmsg = "";
		int legal;
		legal = move2(player, start, end, mmsg);
		if(legal == 0 || legal == 4){
			return false;
		}
		return true;
	}
	public boolean unmove(int legal, Coordinate start, Coordinate end){

		if(legal == 0 || legal == 4){
		}
		else if(legal == 1){
			//jump
			int xm = (start.x + end.x)/2;
			int ym = (start.y + end.y)/2;
			s[xm][ym] = 1;
			s[start.x][start.y]=1;
			s[end.x][end.y] = 0;
			run --;
		}
		else if(legal == 2){
			//move
			s[start.x][start.y] = 1;
			s[end.x][end.y] = 0;
			run --;
		}
		else if(legal == 3){
			//first move
			s[start.x][start.y] = 1;
			run --;
		}
		return true;
	}
	public double dist_to_ctr(Coordinate pos){
		double ctr_x = ((double)width - 1)/2;
		double ctr_y = ((double)height - 1)/2;
		double dist =  pow(((double)pos.x - ctr_x),2) + pow(((double)pos.y - ctr_y),2);
		return dist;
	}
	public int jumpable(Coordinate p, Coordinate pj){
		if(s[p.x][p.y] == 0){
			return 0;
		}
		Coordinate d = new Coordinate();
		int xm;
		int ym;
		int xj;
		int yj;
		Random generator = new Random();
		int rand = generator.nextInt(8);
		for(int i= 0; i < 8; i++){
			dir((i+rand)%8, d);
			xm = p.x+d.x;
			ym = p.y+d.y;
			xj = p.x+2*d.x;
			yj = p.y+2*d.y;
			if(xj >= 0 && xj < width && yj >=0 && yj < height){
				if(s[xm][ym] == 1 && s[xj][yj] == 0){
					pj.set(xj, yj);
					return 1;
				}
			}
		}
		return 0;
	}
	public int jump_check(Coordinate p, Coordinate pj, int idx){
		if(s[p.x][p.y] == 0){
			return 0;
		}
		Coordinate d = new Coordinate();
		int xm;
		int ym;
		int xj;
		int yj;

		dir(idx%8, d);
		xm = p.x+d.x;
		ym = p.y+d.y;
		xj = p.x+2*d.x;
		yj = p.y+2*d.y;
		if(xj >= 0 && xj < width && yj >=0 && yj < height){
			if(s[xm][ym] == 1 && s[xj][yj] == 0){
				pj.set(xj, yj);
				return 1;
			}
		}
		return 0;
	}
	public int tocenter(Coordinate p, Coordinate pj){
		if(dist_to_ctr(p) > dist_to_ctr(pj)){
			return 2;
		}
		else if(dist_to_ctr(p) == dist_to_ctr(pj)){
			return 1;
		}
		else{
			return 0;
		}
	}
	// opt == 2 find a pj such that dist(pj, center) < dist(p, center)
	// opt == 1 find a pj such that dist(pj, center) <= dist(p, center)
	// opt == 0 find a pj without any constraint
	public int movable(Coordinate p, Coordinate pj, int opt){
		if(s[p.x][p.y] == 0){
			return 0;
		}
		Coordinate d = new Coordinate();
		int xj;
		int yj;
		Random generator = new Random();
		int rand = generator.nextInt(8);
		for(int i= 0; i < 8; i++){
			dir((i+rand)%8, d);
			xj = p.x+d.x;
			yj = p.y+d.y;
			if(xj >= 0 && xj < width && yj >=0 && yj < height){
				if(s[xj][yj] == 0){
					pj.set(xj, yj);
					if(opt == 0){
						return 1;
					}
					else if(opt == 1){
						if(dist_to_ctr(pj) <= dist_to_ctr(p)){
							return 1;
						}
					}
					else if(opt == 2){
						if(dist_to_ctr(pj) < dist_to_ctr(p)){
							return 1;
						}
					}
				}
			}
		}
		return 0;
	}
	// opt == 2 find a pj such that dist(pj, center) < dist(p, center)
	// opt == 1 find a pj such that dist(pj, center) <= dist(p, center)
	// opt == 0 find a pj without any constraint
	public int move_check(Coordinate p, Coordinate pj, int idx, int opt){
		if(s[p.x][p.y] == 0){
			return 0;
		}
		Coordinate d = new Coordinate();
		int xj;
		int yj;
		dir(idx%8, d);
		xj = p.x+d.x;
		yj = p.y+d.y;
		if(xj >= 0 && xj < width && yj >=0 && yj < height){
			if(s[xj][yj] == 0){
				pj.set(xj, yj);
				if(opt == 0){
					return 1;
				}
				else if(opt == 1){
					if(dist_to_ctr(pj) <= dist_to_ctr(p)){
						return 1;
					}
				}
				else if(opt == 2){
					if(dist_to_ctr(pj) < dist_to_ctr(p)){
						return 1;
					}
				}
			}
		}
		return 0;
	}
	public static void dir(int idx, Coordinate d){
		switch (idx) {
		case 0:
			d.set(1,1);
			break;
		case 1:
			d.set(1,0);
			break;
		case 2:
			d.set(1, -1);
			break;
		case 3:
			d.set(0, -1);
			break;
		case 4:
			d.set(-1, -1);
			break;
		case 5:
			d.set(-1, 0);
			break;
		case 6:
			d.set(-1, 1);
			break;
		case 7:
			d.set(0, 1);
			break;
        default: 
        	d.set(1,1);
        	break;
		}
	}
	public int count(){
		int num = 0;
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				num += s[i][j];
			}
		}
		return num;
	}
	public void show(){
		System.out.println("=== " + "run "+run + " count "+ count() + " ===");
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				System.out.print(" "+s[i][j]);
			}
			System.out.println("");
		}
	}
	public int[][] getS() {
		return s;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public int getRun() {
		return run;
	}
	public int getPlayer() {
		return player;
	}
}
