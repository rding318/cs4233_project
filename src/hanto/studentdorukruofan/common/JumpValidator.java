/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentdorukruofan.common;

import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

/**
 *  Jump Validator class
 * @author doruk, ruofan
 *
 */
public class JumpValidator extends MoveValidator{

	@Override
	public void moveCheck(Board board, MyCoordinate from, MyCoordinate to,
			HantoPieceType pieceType, HantoPlayerColor nextMove)
			throws HantoException {
		correctPiece(board, from, to, pieceType, nextMove);
		if(!isConnected(board, from, to)){
			throw new HantoException("Not Contiguous");
		}
		
		int dx, dy, distance;
		dx = to.getX() - from.getX();
		dy = to.getY() - from.getY();
		
		if(from.equals(to)){
			throw new HantoException("Can not jump to current coordinate");
		}
		
		if(!(dx == -dy || dx == 0  || dy == 0)){
			throw new HantoException("Jumping has to be on a straight line");
		}
		
		distance = Math.max(Math.abs(dx), Math.abs(dy));
		dx = dx / distance;
		dy = dy / distance;
		
		if(distance <= 1){
			throw new HantoException(" You must jump over at least one occupied hex");
		}
		
		for(int i=1; i < distance; i++){
			MyCoordinate coord = new MyCoordinate(i*dx+from.getX(), i*dy + from.getY());
			if(!board.hasPieceAt(coord)){
				throw new HantoException("There	may	be no unoccupied hexes in line of hexes");
			}
		}
		
		if(board.hasPieceAt(to)){
			throw new HantoException("There is a piece on the destination");
		}
		
		
	}

}
