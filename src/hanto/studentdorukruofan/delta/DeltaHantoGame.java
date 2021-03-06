/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentdorukruofan.delta;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentdorukruofan.common.BaseHantoGame;
import hanto.studentdorukruofan.common.FlyValidator;
import hanto.studentdorukruofan.common.MoveValidator;
import hanto.studentdorukruofan.common.WalkValidator;

/**
 * A Delta Hanto Game
 * @author doruk, ruofan
 *
 */
public class DeltaHantoGame extends BaseHantoGame{

	public DeltaHantoGame(HantoPlayerColor moveFirst) {
		super(moveFirst);
		
		MAX_TYPE_NUM.put(HantoPieceType.SPARROW, 4);
		MAX_TYPE_NUM.put(HantoPieceType.CRAB, 4);
		MAX_GAME_TURNS = Integer.MAX_VALUE / 2;
	}

	@Override
	protected MoveValidator getMoveValidator(HantoPieceType type) {
		MoveValidator v = null;
		if(type == HantoPieceType.SPARROW){
			v = new FlyValidator();
		}else if(type == HantoPieceType.BUTTERFLY || type == HantoPieceType.CRAB){
			v = new WalkValidator();
		}
		return v;
	}
	
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		if(pieceType == null && from == null && to == null){
			return nextMove == HantoPlayerColor.RED ? MoveResult.BLUE_WINS : MoveResult.RED_WINS;
		}
		
		gameEndsCheck();
		placeButterflyBy4();
		makeMoveCheck(pieceType, from, to);	
		saveMove(pieceType, from, to);
		incrementMove();	
		return checkResult();
	}
}
