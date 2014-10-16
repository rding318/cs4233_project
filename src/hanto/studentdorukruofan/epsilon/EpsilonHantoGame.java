/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentdorukruofan.epsilon;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.HantoPrematureResignationException;
import hanto.common.MoveResult;
import hanto.studentdorukruofan.common.BaseHantoGame;
import hanto.studentdorukruofan.common.FlyValidator;
import hanto.studentdorukruofan.common.JumpValidator;
import hanto.studentdorukruofan.common.MoveValidator;
import hanto.studentdorukruofan.common.MyCoordinate;
import hanto.studentdorukruofan.common.WalkValidator;
import hanto.tournament.HantoMoveRecord;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Epsilon hanto game class
 * @author doruk, ruofan
 *
 */
public class EpsilonHantoGame extends BaseHantoGame{

	public EpsilonHantoGame(HantoPlayerColor moveFirst) {
		super(moveFirst);
		
		MAX_TYPE_NUM.put(HantoPieceType.SPARROW, 2);
		MAX_TYPE_NUM.put(HantoPieceType.CRAB, 6);
		MAX_TYPE_NUM.put(HantoPieceType.HORSE, 4);
		MAX_TYPE_NUM.put(HantoPieceType.BUTTERFLY, 1);
		MAX_GAME_TURNS = Integer.MAX_VALUE / 2;
	}

	@Override
	protected MoveValidator getMoveValidator(HantoPieceType type) {
		switch(type){
		case SPARROW:
			return new FlyValidator(4);
		case BUTTERFLY:
			return new WalkValidator();
		case CRAB:
			return new WalkValidator();
		case HORSE:
			return new JumpValidator();
		default:
			return null;
		}
	}
	
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		if(pieceType == null && from == null && to == null){
			resignCheck();
			return nextMove == HantoPlayerColor.RED ? MoveResult.BLUE_WINS : MoveResult.RED_WINS;
		}
		
		gameEndsCheck();
		placeButterflyBy4();
		makeMoveCheck(pieceType, from, to);	
		saveMove(pieceType, from, to);
		incrementMove();	
		return checkResult();
	}
	
	/**
	 * Checks if the player can resign legally
	 * @throws HantoPrematureResignationException
	 */
	public void resignCheck() throws HantoPrematureResignationException{
		if(getPossibleMoves(nextMove).size() != 0){
			throw new HantoPrematureResignationException();
		}
	}
	
	/**
	 * Gets all the possible moves that the player can do
	 * @param color
	 * @return collection of possible moves
	 */
	public Collection<HantoMoveRecord> getPossibleMoves(HantoPlayerColor color) {

		Set<MyCoordinate> availableDestination = new HashSet<MyCoordinate>();
		Collection<HantoMoveRecord> possibleMoves = new LinkedList<HantoMoveRecord>();
		
		if(moveCounter == 0) {
			possibleMoves.add(new HantoMoveRecord(HantoPieceType.BUTTERFLY, null, new MyCoordinate(0, 0)));
			possibleMoves.add(new HantoMoveRecord(HantoPieceType.CRAB, null, new MyCoordinate(0, 0)));
			possibleMoves.add(new HantoMoveRecord(HantoPieceType.SPARROW, null, new MyCoordinate(0, 0)));
			possibleMoves.add(new HantoMoveRecord(HantoPieceType.HORSE, null, new MyCoordinate(0, 0)));
			return possibleMoves;
		}
		
		
		for (MyCoordinate coord: board.getAllCoords()){
			availableDestination.addAll(board.getAdjacentLocations(coord));
		}

		for (HantoPieceType type : HantoPieceType.values()) {
			for (MyCoordinate to : availableDestination) {
				try {
					makeMoveCheck(type, null, to);
				} catch (HantoException e) {
					continue;
				}
				possibleMoves.add(new HantoMoveRecord(type, null, to));
			}
		}

		for (MyCoordinate from : board.getAllCoords()) {
			HantoPiece piece = board.getPieceAt(from);
			for (MyCoordinate to : availableDestination) {
				try {
					makeMoveCheck(piece.getType(), from, to);
				} catch (HantoException e) {
					continue;
				}
				possibleMoves.add(new HantoMoveRecord(piece.getType(), from, to));
			}
		}
		return possibleMoves;
	}
}
