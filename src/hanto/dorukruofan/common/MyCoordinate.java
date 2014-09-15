package hanto.dorukruofan.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;

import java.util.Collection;
import java.util.LinkedList;


public class MyCoordinate implements HantoCoordinate {
	private int x;
	private int y;
	
	public MyCoordinate(final HantoCoordinate coordinate){
		x = coordinate.getX();
		y = coordinate.getY();
	}
	
	public MyCoordinate(final int x, final int y){
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
	
	@Override
	public int hashCode(){
		return x*31 + y;
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof HantoCoordinate){
			return x == ((HantoCoordinate) o).getX() && y == ((HantoCoordinate) o).getY();
		}else{
			return false;
		}
	}
	
	public Collection<HantoCoordinate> getNeighborsLocation(){
		Collection<HantoCoordinate> neighborsLocation = new LinkedList<HantoCoordinate>();
		neighborsLocation.add(new MyCoordinate(x, y+1));
		neighborsLocation.add(new MyCoordinate(x+1, y));
		neighborsLocation.add(new MyCoordinate(x+1, y-1));
		neighborsLocation.add(new MyCoordinate(x, y-1));
		neighborsLocation.add(new MyCoordinate(x-1, y));
		neighborsLocation.add(new MyCoordinate(x-1, y+1));
		return neighborsLocation;
	}
	
	public Collection<HantoPiece> getNeighborsPiece(HantoGame game){
		Collection<HantoPiece> neighborsPiece = new LinkedList<HantoPiece>();
		for(HantoCoordinate coord: getNeighborsLocation()){
			HantoPiece piece = game.getPieceAt(coord);
			if(piece != null){
				neighborsPiece.add(piece);
			}
		}
		return neighborsPiece;
	}
}
