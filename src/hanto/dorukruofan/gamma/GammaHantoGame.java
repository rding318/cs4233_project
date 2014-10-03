/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.dorukruofan.gamma;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.dorukruofan.common.BaseHantoGame;
import hanto.dorukruofan.common.MoveValidator;
import hanto.dorukruofan.common.WalkValidator;

/**
 * A Gamma Hanto Game
 * @author doruk, ruofan
 *
 */
public class GammaHantoGame extends BaseHantoGame{
	public GammaHantoGame(HantoPlayerColor moveFirst){
		super(moveFirst);
		
		MAX_TYPE_NUM.put(HantoPieceType.SPARROW, 5);
		MAX_GAME_TURNS = 20;
	}
	
	protected MoveValidator getMoveValidator(HantoPieceType type){
		switch(type){
		case BUTTERFLY:
			return new WalkValidator();
		case SPARROW:
			return new WalkValidator();
		default:
			return null;
		}
	}
	
	protected void putValidation(HantoPieceType pieceType, HantoCoordinate to) throws HantoException{
		firstMoveValidation(to);
		pieceNumberCheck(pieceType);
		isConnected(to);
		coordinateConflictValidation(to);
		onlyConnectedToTeamColor(to);
	}
	
	private void onlyConnectedToTeamColor(HantoCoordinate to) throws HantoException{
		if(moveCounter < 2) {
			return;
		}
		for(HantoPiece piece: board.getAdjacentPieces(to)){
			if(piece.getColor() != nextMove){
				throw new HantoException("Piece can not be put adjacent to opponent's pieces");
			}
		}
	}
}
