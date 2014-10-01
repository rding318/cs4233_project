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
	
	public int distance(MyCoordinate coord){
		int z1 = 0 - x - y;
		int z2 = 0 - coord.getX() - coord.getY();

		return (Math.abs(x - coord.getX()) + Math.abs(y - coord.getY()) + Math.abs(z1 - z2)) / 2;
	}
}
