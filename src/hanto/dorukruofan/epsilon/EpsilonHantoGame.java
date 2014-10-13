package hanto.dorukruofan.epsilon;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.HantoPrematureResignationException;
import hanto.common.MoveResult;
import hanto.dorukruofan.common.BaseHantoGame;
import hanto.dorukruofan.common.FlyValidator;
import hanto.dorukruofan.common.JumpValidator;
import hanto.dorukruofan.common.MoveValidator;
import hanto.dorukruofan.common.MyCoordinate;
import hanto.dorukruofan.common.WalkValidator;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

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
		placeButterflyBy4(pieceType);
		makeMoveCheck(pieceType, from, to);	
		incrementMove();	
		return checkResult();
	}
	
	public void resignCheck() throws HantoPrematureResignationException{
		if(moveCounter < 1){
			throw new HantoPrematureResignationException();
		}
		
		Set<MyCoordinate> availableDestination = new HashSet<MyCoordinate>();
		for(MyCoordinate coord: board.getAllCoords()){
			availableDestination.addAll(board.getAdjacentLocations(coord));
		}
		
	
		for (HantoPieceType type : HantoPieceType.values()) {
			for (MyCoordinate to : availableDestination) {
				try {
					makeMoveCheck(type, null, to);
				} catch (HantoException e) {
					continue;
				}
				throw new HantoPrematureResignationException();
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
				throw new HantoPrematureResignationException();
			}
		}
	}

}
