package hanto.dorukruofan.alpha;

import hanto.common.HantoCoordinate;

import java.util.Collection;
import java.util.LinkedList;


public class HantoCoordinateGrid implements HantoCoordinate {
	private int x;
	private int y;
	
	public static String getHashCode(HantoCoordinate coordinate){
		return coordinate.getX() + "," + coordinate.getY();
	}
	
	public HantoCoordinateGrid(HantoCoordinate coordinate){
		this.x = coordinate.getX();
		this.y = coordinate.getY();
	}
	
	public HantoCoordinateGrid(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}
	
	public Collection<HantoCoordinate> getNeighbors(){
		LinkedList<HantoCoordinate> neighbors = new LinkedList<HantoCoordinate>();
		neighbors.add(new HantoCoordinateGrid(x, y+1));
		neighbors.add(new HantoCoordinateGrid(x+1, y));
		neighbors.add(new HantoCoordinateGrid(x+1, y-1));
		neighbors.add(new HantoCoordinateGrid(x, y-1));
		neighbors.add(new HantoCoordinateGrid(x-1, y));
		neighbors.add(new HantoCoordinateGrid(x-1, y+1));
		return neighbors;
	}
}
