package hanto.dorukruofan.gamma;

import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.dorukruofan.common.BaseHantoGame;
import hanto.dorukruofan.common.MoveValidator;
import hanto.dorukruofan.common.WalkValidator;

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
