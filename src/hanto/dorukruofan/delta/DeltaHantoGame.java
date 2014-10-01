package hanto.dorukruofan.delta;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.dorukruofan.common.BaseHantoGame;
import hanto.dorukruofan.common.FlyValidator;
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
		default:
			return null;
		}
	}
	
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		if(from == null && to == null){
			return nextMove == HantoPlayerColor.RED ? MoveResult.BLUE_WINS : MoveResult.RED_WINS;
		}
		gameEndsCheck();
		placeButterflyBy4(pieceType);
		makeMoveCheck(pieceType, from, to);	
		incrementMove();	
		return checkResult();
	}
}
