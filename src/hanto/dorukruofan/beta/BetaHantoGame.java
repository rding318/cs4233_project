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
import hanto.dorukruofan.common.Board;
import hanto.dorukruofan.common.MoveValidator;
import hanto.dorukruofan.common.MyCoordinate;
import hanto.dorukruofan.common.Piece;

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

	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		gameEndsCheck();
		onlyPlacingAllowed(from);
		firstMoveValidation(to);
		isConnected(to);
		coordinateConflictValidation(to);
		placeButterflyBy4(pieceType);
		pieceNumberCheck(pieceType);
		
		saveToBoard(to, pieceType);
		incrementMove();

		return checkResult();
	}

	private void onlyPlacingAllowed(HantoCoordinate from) throws HantoException{
		if(from != null){
			throw new HantoException("Moving a piece is not supported in Alpha Hanto");
		}
	}

	@Override
	protected MoveValidator getMoveValidator(HantoPieceType type) {
		// TODO Auto-generated method stub
		return null;
	}

}

