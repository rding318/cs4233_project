/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.dorukruofan.beta;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.dorukruofan.common.BaseHantoGame;
import hanto.dorukruofan.common.MoveValidator;

import java.util.Collection;

/**
 * @author doruk, ruofan
 *
 */
public class BetaHantoGame extends BaseHantoGame{

	/**
	 * Creates a beta version of Hanto.
	 * @param moveFirst color of the player who starts first
	 */
	public BetaHantoGame(HantoPlayerColor moveFirst){
		super(moveFirst);

		MAX_TYPE_NUM.put(HantoPieceType.SPARROW, 5);
		MAX_GAME_TURNS = 6;
	}

	protected void makeMoveCheck(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException{
		if(from != null){
			throw new HantoException("Moving a piece is not supported in Alpha Hanto");
		}
		
		putValidation(pieceType, to);
		saveToBoard(to, pieceType);
	}

	@Override
	protected MoveValidator getMoveValidator(HantoPieceType type) {
		return null;
	}
	
	protected void putValidation(HantoPieceType pieceType, HantoCoordinate to) throws HantoException{
		firstMoveValidation(to);
		pieceNumberCheck(pieceType);
		isConnected(to);
		coordinateConflictValidation(to);
	}
	
	protected MoveResult checkResult(){
		Collection<HantoPiece> neighbors ;

		if(redButterflyLocation != null){
			neighbors = board.getAdjacentPieces(redButterflyLocation);
			if(neighbors.size() == 6){
				return MoveResult.BLUE_WINS;
			}
		}

		if(blueButterflyLocation != null){
			neighbors = board.getAdjacentPieces(blueButterflyLocation);
			if(neighbors.size() == 6){
				return MoveResult.RED_WINS;
			}
		}
		
		if(moveCounter < MAX_GAME_TURNS * 2){
			return MoveResult.OK;
		}else{
			return MoveResult.DRAW;
		}
	}

}

