package hanto.dorukruofan.alpha;

import hanto.common.HantoCoordinate;


public class HantoCoordinateGrid implements HantoCoordinate {
	private int x;
	private int y;
	
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

}
