package hanto.dorukruofan.epsilon;

import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.dorukruofan.common.BaseHantoGame;
import hanto.dorukruofan.common.FlyValidator;
import hanto.dorukruofan.common.MoveValidator;
import hanto.dorukruofan.common.WalkValidator;

public class EpsilonHantoGame extends BaseHantoGame{

	protected EpsilonHantoGame(HantoPlayerColor moveFirst) {
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

}
