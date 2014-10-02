/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.dorukruofan.delta;

import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.dorukruofan.common.HantoTestGame;
import hanto.dorukruofan.common.MyCoordinate;
import hanto.dorukruofan.common.Piece;


public class DeltaHantoTestGame extends DeltaHantoGame implements HantoTestGame{

	public DeltaHantoTestGame(HantoPlayerColor moveFirst) {
		super(moveFirst);
	}

	@Override
	public void initializeBoard(PieceLocationPair[] initialPieces) {
		for(PieceLocationPair piece: initialPieces){
			board.putPieceAt(new Piece(piece.player, piece.pieceType), new MyCoordinate(piece.location));
			if(piece.pieceType == HantoPieceType.BUTTERFLY){
				switch(piece.player){
				case BLUE:
					blueButterflyLocation = new MyCoordinate(piece.location);
					break;
				case RED:
					redButterflyLocation = new MyCoordinate(piece.location);
					break;
				}
			}
		}
	}

	@Override
	public void setTurnNumber(int turnNumber) {
		moveCounter = (turnNumber - 1)*2 + (nextMove == moveFirst? 0:1);
	}

	@Override
	public void setPlayerMoving(HantoPlayerColor player) {
		nextMove = player;
	}
	

}
