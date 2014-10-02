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
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

/**
 * Class which contains the common variables and methods for all 
 * Hanto versions
 * @author doruk, ruofan
 *
 */
public abstract class BaseHantoGame implements HantoGame {
	protected HantoPlayerColor nextMove, moveFirst;
	protected Board board;
	protected int moveCounter;

	protected MyCoordinate redButterflyLocation;
	protected MyCoordinate blueButterflyLocation;
	
	protected Map<HantoPieceType, Integer> MAX_TYPE_NUM;
	protected int MAX_GAME_TURNS;
	
	/**
	 * Constructor for the BaseHantoGame class
	 * @param moveFirst color of player who moves first
	 */
	protected BaseHantoGame(HantoPlayerColor moveFirst){
		board = new Board();
		moveCounter = 0;
		redButterflyLocation = null;
		blueButterflyLocation = null;
		nextMove = moveFirst;
		moveFirst = this.moveFirst;
		
		MAX_TYPE_NUM = new Hashtable<HantoPieceType, Integer> ();
		MAX_TYPE_NUM.put(HantoPieceType.BUTTERFLY, 1);
		MAX_TYPE_NUM.put(HantoPieceType.CRAB, 0);
		MAX_TYPE_NUM.put(HantoPieceType.CRANE, 0);
		MAX_TYPE_NUM.put(HantoPieceType.DOVE, 0);
		MAX_TYPE_NUM.put(HantoPieceType.HORSE, 0);
		MAX_TYPE_NUM.put(HantoPieceType.SPARROW, 0);
	}
	

	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		gameEndsCheck();
		placeButterflyBy4(pieceType);
		makeMoveCheck(pieceType, from, to);	
		incrementMove();	
		return checkResult();
	}
	
	/**
	 * Checks if a given move is a legal move
	 * @param pieceType piece type to be moved
	 * @param from from coordinate
	 * @param to to coordinate
	 * @throws HantoException
	 */
	protected void makeMoveCheck(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException{
		if(from == null){
			putValidation(pieceType, to);
			saveToBoard(to, pieceType);
		}else{
			MoveValidator validator = getMoveValidator(pieceType);
			validator.moveCheck(board, new MyCoordinate(from), new MyCoordinate(to), pieceType, nextMove);
			board.movePiece(from, to);
		}
	}
	
	/**
	 * Validator method which checks if piece placement to board is valid
	 * @param pieceType piece type to be placed
	 * @param to to coordinate
	 * @throws HantoException
	 */
	protected void putValidation(HantoPieceType pieceType, HantoCoordinate to) throws HantoException{
		firstMoveValidation(to);
		pieceNumberCheck(pieceType);
		isConnected(to);
		coordinateConflictValidation(to);
		onlyConnectedToTeamColor(to);
	}
	
	/**
	 * Validator method to check if max number of allowed turns is reached.
	 * @throws HantoException
	 */
	protected void gameEndsCheck() throws HantoException{
		if(moveCounter >= MAX_GAME_TURNS * 2){
			throw new HantoException("The game ends after " + MAX_GAME_TURNS + " turns");
		}
	}
	
	/**
	 * Validator method to check if the first piece is placed on the origin
	 * @param to
	 * @throws HantoException
	 */
	protected void firstMoveValidation(HantoCoordinate to) throws HantoException{
		if(moveCounter == 0){
			if(!(to.getX() == 0 && to.getY() == 0)){
				throw new HantoException("The first piece should be placed at the origin");
			}
		}
	}
	
	/**
	 * Validator to check if there is already a piece on the "to" coordinate of a move
	 * @param to
	 * @throws HantoException
	 */
	protected void coordinateConflictValidation(HantoCoordinate to) throws HantoException{
		if(board.hasPieceAt(to)){
			throw new HantoException("There is a piece on the specified destination coordinate");
		}
	}
	
	/**
	 * Validator method which checks if the butterfly is placed by the 4th turn for both
	 * players.
	 * @param pieceType
	 * @throws HantoException
	 */
	protected void placeButterflyBy4(HantoPieceType pieceType) throws HantoException{
		if(moveCounter == 6 || moveCounter == 7){
			switch(nextMove){
			case RED:
				if(redButterflyLocation == null && pieceType != HantoPieceType.BUTTERFLY){
					throw new HantoException("The butterfly should be placed by the 4th turn");
				}
				break;
			case BLUE:
				if(blueButterflyLocation == null && pieceType != HantoPieceType.BUTTERFLY){
					throw new HantoException("The butterfly should be placed by the 4th turn");
				}
				break;
			}
		}
	}
	
	/**
	 * Validator method to prevent the placement of pieces more than allowed by the 
	 * game version
	 * @param type
	 * @throws HantoException
	 */
	protected void pieceNumberCheck(HantoPieceType type) throws HantoException{
		if(board.getPieceNumber(nextMove, type) >= MAX_TYPE_NUM.get(type)){
			throw new HantoException("There can be no more than " + MAX_TYPE_NUM.get(type) + " " + type.getPrintableName() + " for each color");
		}
	}

	/**
	 * Method used to save pieces on board. New pieces are just saved and 
	 * existing pieces which are moved are updated.
	 * @param to
	 * @param pieceType
	 */
	protected void saveToBoard(HantoCoordinate to, HantoPieceType pieceType){
		Piece piece = new Piece(nextMove, pieceType);
		board.putPieceAt(piece, to);
		if(pieceType == HantoPieceType.BUTTERFLY){
			switch(nextMove){
			case BLUE:
				blueButterflyLocation = new MyCoordinate(to);
				break;
			case RED:
				redButterflyLocation = new MyCoordinate(to);
				break;
			}
		}
	}
	
	/**
	 * Validator to check that a given coordinate is connected to other pieces
	 * @param to
	 * @throws HantoException
	 */
	protected void isConnected(HantoCoordinate to) throws HantoException{
		if(moveCounter == 0) {
			return;
		}
		
		Collection<HantoPiece> neighborsPiece = board.getAdjacentPieces(to);
		
		if(neighborsPiece.size() == 0){
			throw new HantoException("The piece is not connected with any other pieces on the board");
		}
	}
	
	/**
	 * Method used to increment move counter by one and 
	 * switch the player turn
	 */
	protected void incrementMove(){
		moveCounter ++;
		if(nextMove == HantoPlayerColor.BLUE){
			nextMove = HantoPlayerColor.RED;
		}else{
			nextMove = HantoPlayerColor.BLUE;
		}
	}
	
	/**
	 * Used to determine the result of a move. Makes checks on
	 * current conditions and returns OK, RED WINS, BLUE WINS or
	 * DRAW.
	 * @return result of the move (OK, RED WINS, BLUE WINS or DRAW.)
	 */
	protected MoveResult checkResult(){
		int enemyNum;
		Collection<HantoPiece> neighbors ;

		if(redButterflyLocation != null){
			neighbors = board.getAdjacentPieces(redButterflyLocation);
			enemyNum = 0;
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
			neighbors = board.getAdjacentPieces(blueButterflyLocation);
			enemyNum = 0;
			for(HantoPiece piece: neighbors){
				if(piece.getColor() == HantoPlayerColor.RED){
					enemyNum++;
				}
			}
			if(enemyNum == 6){
				return MoveResult.RED_WINS;
			}
		}
		
		if(moveCounter < MAX_GAME_TURNS * 2){
			return MoveResult.OK;
		}else{
			return MoveResult.DRAW;
		}
	}
	
	/**
	 * Validator method which checks if a placed piece is only connected to the player's 
	 * color
	 * @param to
	 * @throws HantoException
	 */
	public void onlyConnectedToTeamColor(HantoCoordinate to) throws HantoException{
		if(moveCounter < 2) {
			return;
		}
		for(HantoPiece piece: board.getAdjacentPieces(to)){
			if(piece.getColor() != nextMove){
				throw new HantoException("Piece can not be put adjacent to opponent's pieces");
			}
		}
	}
	
	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		return board.getPieceAt(where);
	}

	@Override
	public String getPrintableBoard() {
		return board.getPrintableBoard();
	}
	
	/**
	 * Gets the move validator of the given piece type
	 * @param type
	 * @return move validator for given piece
	 */
	protected abstract MoveValidator getMoveValidator(HantoPieceType type);

}
