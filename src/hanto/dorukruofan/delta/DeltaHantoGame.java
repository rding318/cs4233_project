package hanto.dorukruofan.delta;

import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.dorukruofan.common.BaseHantoGame;
import hanto.dorukruofan.common.MoveValidator;
import hanto.dorukruofan.common.WalkValidator;

public class DeltaHantoGame extends BaseHantoGame{

	protected DeltaHantoGame(HantoPlayerColor moveFirst) {
		super(moveFirst);
		
		MAX_TYPE_NUM.put(HantoPieceType.SPARROW, 4);
		MAX_TYPE_NUM.put(HantoPieceType.CRAB, 4);
		MAX_GAME_TURNS = Integer.MAX_VALUE;
	}

	@Override
	protected MoveValidator getMoveValidator(HantoPieceType type) {
		switch(type){
		case SPARROW:
			return new FlyValidator();
		case BUTTERFLY:
		case CRAB:
			return new WalkValidator();
		}
	}

}
