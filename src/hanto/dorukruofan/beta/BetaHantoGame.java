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
		super();
		nextMove = moveFirst;
	}

	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		gameEndsCheck();
		onlyPlacingAllowed(from);
		checkSupportedPieces(pieceType);
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

	private void checkSupportedPieces(HantoPieceType pieceType) throws HantoException{
		if(pieceType != HantoPieceType.BUTTERFLY && pieceType != HantoPieceType.SPARROW){
			throw new HantoException(pieceType.getPrintableName() + " is not supported in Beta Hanto");
		}
	}

	private void isConnected(HantoCoordinate to) throws HantoException{
		if(moveCounter == 0) {
			return;
		}
		
		Collection<HantoPiece> neighborsPiece = board.getAdjacentPieces(to);
		
		if(neighborsPiece.size() == 0){
			throw new HantoException("The piece is not connected with any other pieces on the board");
		}
	}

	private void gameEndsCheck() throws HantoException{
		if(moveCounter >= 12){
			throw new HantoException("The game ends after 6 turns");
		}
	}
		
	
	protected MoveResult checkResult(){
		if(redButterflyLocation != null){
			Collection<HantoPiece> neighbors = board.getAdjacentPieces(redButterflyLocation);
			int enemyNum = 0;
			for(HantoPiece piece: neighbors){
				if(piece.getColor() == HantoPlayerColor.BLUE){
					enemyNum++;
				}
			}
			if(enemyNum == 6){
				return MoveResult.BLUE_WINS;
			}
		}

		if(blueButterflyLocation != null){
			Collection<HantoPiece> neighbors = board.getAdjacentPieces(blueButterflyLocation);
			int enemyNum = 0;
			for(HantoPiece piece: neighbors){
				if(piece.getColor() == HantoPlayerColor.RED){
					enemyNum++;
				}
			}
			if(enemyNum == 6){
				return MoveResult.RED_WINS;
			}
		}
		
		if(moveCounter < 12){
			return MoveResult.OK;
		}else{
			return MoveResult.DRAW;
		}
	}


}

