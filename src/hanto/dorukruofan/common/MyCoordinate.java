/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.dorukruofan.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;

import java.util.Collection;
import java.util.LinkedList;

/**
 * 
 * @author doruk, ruofan
 *
 */
public class MyCoordinate implements HantoCoordinate {
	private int x;
	private int y;
	
	/**
	 * Constructor for MyCoordinate class that takes uses
	 * HantoCoordinate type to get X and Y coordinates
	 * @param coordinate 
	 */
	public MyCoordinate(final HantoCoordinate coordinate){
		x = coordinate.getX();
		y = coordinate.getY();
	}
	
	/**
	 * Constructor for MyCoordinate class that takes integer
	 * types
	 * @param x
	 * @param y
	 */
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
	
	/**
	 * Finds all adjacent coordinates of a given coordinate.
	 * @return collection of HantoCoordinate which contains all 6 adjacent coordinates
	 * 
	 */
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
	
	/**
	 * 
	 * @param game the current Hanto game.
	 * @return a collection HantoPiece which contains all pieces on board
	 */
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
