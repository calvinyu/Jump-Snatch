package javagame;

import java.util.ArrayList;
import java.util.Random;

public class SmartPlayer {
	private Random generator = new Random();
	public SmartPlayer(){
	}
	
	public int move(Kernel k){
		System.out.println("X");
		Coordinate start = new Coordinate();
		Coordinate end = new Coordinate();
		int legal = get_move(k, start, end);
		if(legal == 1){
			k.move(1, start, end);
		}
		return legal;
	}
	
	//input: k
	//output: start, end
	public int get_move(Kernel k, Coordinate start, Coordinate end){
		Move best_move = new Move();
		double value = 0;
		if(k.count() <= 3){
			value = alphabeta(k, -1000, 1000, true, best_move, 0);
			System.out.println("this is best_move, value: " + value);
			best_move.show();
			start.set(best_move.start.x, best_move.start.y);
			end.set(best_move.end.x, best_move.end.y);
		}
		else if(k.count()<= 4 && k.getWidth() == 3 && k.getHeight() == 3){
			value = alphabeta(k, -1000, 1000, true, best_move, 0);
			System.out.println("this is best_move, value: " + value);
			best_move.show();
			start.set(best_move.start.x, best_move.start.y);
			end.set(best_move.end.x, best_move.end.y);
		}
		else{
			RandomPlayer rp = new RandomPlayer(k.getWidth(), k.getHeight());
			rp.get_move(k, start, end);
		}
		return 0;
	}
	double alphabeta(Kernel k, double alpha, double beta, boolean max, Move best_move, int depth){
		double value;
		int legal;
		String msg = "";
		if(k.count() == 1){
			if(max == true){
				return 0;
			}
			else{
				return 1;
			}
		}
		if(depth > k.count()*(k.getWidth()+k.getHeight())){
			return 0.5;
		}
		ArrayList<Move> moveLs = new ArrayList<Move>();
		get_all_moves(k, moveLs);
		for(int i = 0; i < moveLs.size(); i++){
			if(depth == 0){
				System.out.println("moveLs.size():" + moveLs.size() + " i:" + i);
			}
			legal = k.move2(1, moveLs.get(i).start, moveLs.get(i).end, msg);
			Move tmp_move = new Move();
			value = alphabeta(k, alpha, beta, !max, tmp_move, depth+1);
			k.unmove(legal, moveLs.get(i).start, moveLs.get(i).end);
			if(max == true){
				if(value > alpha){
					alpha = value;
					best_move.setMove(moveLs.get(i));
					if(beta <= alpha){
						return alpha;
					}
				}
			}
			else{
				if(value < beta){
					beta = value;
					best_move.setMove(moveLs.get(i));
					if(beta <= alpha){
						return beta;
					}
				}
			}
		}
		if(max == true){
			//System.out.println("depth:" + depth + " alpha:" + alpha + " beta:" + beta);
			return alpha;
		}
		else{
			//System.out.println("depth:" + depth + " alpha:" + alpha + " beta:" + beta);
			return beta;
		}
	}

	public int get_all_moves(Kernel k, ArrayList<Move> moveLs){
		int w = k.getWidth();
		int l = k.getHeight();
		int area = w*l;
		
		// try to find a piece to remove
		if(k.getRun() == 0){
			for(int i = 0; i < area; i++){
				Move move = new Move();
				move.start.set(i%w, i/w);
				moveLs.add(move);
			}
			return 1;
		}
		// try to find all pieces that are able to jump
		for(int i = 0; i < area; i++){
			for(int j = 0; j < 8; j++){
				Move move = new Move();
				move.start.set(i%w, i/w);
				if(k.jump_check(move.start, move.end, j) == 1){
					moveLs.add(move);
				}
			}
		}
		if(moveLs.size() > 0){
			return 1;
		}
		
		// try to find all pieces that are able to move and near center
		for(int i = 0; i < area; i++){
			for(int j = 0; j < 8; j++){
				Move move = new Move();
				move.start.set(i%w, i/w);
				if(k.move_check(move.start, move.end, j, 2) == 1){
					moveLs.add(move);
				}
			}
		}
		if(moveLs.size() > 0){
			return 1;
		}
		
		// try to find all pieces that do not move away from center
		for(int i = 0; i < area; i++){
			for(int j = 0; j < 8; j++){
				Move move = new Move();
				move.start.set(i%w, i/w);
				if(k.move_check(move.start, move.end, j, 1) == 1){
					moveLs.add(move);
				}
			}
		}
		if(moveLs.size() > 0){
			return 1;
		}
		
		// try to find all pieces that is able to move
		for(int i = 0; i < area; i++){
			for(int j = 0; j < 8; j++){
				Move move = new Move();
				move.start.set(i%w, i/w);
				if(k.move_check(move.start, move.end, j, 0) == 1){
					moveLs.add(move);
				}
			}
		}
		if(moveLs.size() > 0){
			return 1;
		}
		return 0;
	}
	public class Move{
		public Coordinate start = new Coordinate();
		public Coordinate end = new Coordinate();
		public Move(){
			start = new Coordinate();
			end = new Coordinate();
		}

		public void show(){
			System.out.println("start.x:" +start.x + " start.y:" + start.y + " end.x:" + end.x + " end.y:" + end.y);
		}
		
		public void setMove(Move m){
			start.set(m.start.x, m.start.y);
			end.set(m.end.x, m.end.y);
		}

	}
}
