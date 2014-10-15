/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentdorukruofan.gamma;

import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentdorukruofan.common.BaseHantoGame;
import hanto.studentdorukruofan.common.MoveValidator;
import hanto.studentdorukruofan.common.WalkValidator;

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
	
}
