package hanto.dorukruofan.alpha;

import hanto.common.HantoCoordinate;


public class HantoCoordinateGrid implements HantoCoordinate {
	private int x;
	private int y;
	
	public HantoCoordinateGrid(int x, int y){
		this.x = x;
		this.y = y;
	}
	public HantoCoordinateGrid(HantoCoordinate coord){
		this.x = coord.getX();
		this.y = coord.getY();
	}
	
	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}
	
	public boolean equals(Object o){
		if(o instanceof HantoCoordinateGrid){
			HantoCoordinateGrid coord = (HantoCoordinateGrid) o;
			return this.x == coord.getX() && this.y == coord.getX();
		}else{
			return false;
		}
	}

}
