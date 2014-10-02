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

import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

/**
 * A class to check whether a walk is validate for a piece
 * @author doruk, ruofan
 *
 */
public class WalkValidator extends MoveValidator {

	@Override
	public void moveCheck(Board board, MyCoordinate from, MyCoordinate to, HantoPieceType pieceType, HantoPlayerColor nextMove) throws HantoException {
		correctPiece(board, from, to, pieceType, nextMove);
		if(!isConnected(board, from, to)){
			throw new HantoException("Not Contiguous");
		}
		if(from.distance(to) != 1){
			throw new HantoException("Move distance is not 1 for walk");
		}
	}

}
