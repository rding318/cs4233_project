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
import java.util.LinkedList;

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
	
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		if(pieceType == null && from == null && to == null){
			resign();
			return nextMove == HantoPlayerColor.RED ? MoveResult.BLUE_WINS : MoveResult.RED_WINS;
		}
		
		gameEndsCheck();
		placeButterflyBy4(pieceType);
		makeMoveCheck(pieceType, from, to);	
		incrementMove();	
		return checkResult();
	}
	
	public void resign() throws HantoPrematureResignationException{

		for (HantoPieceType type : HantoPieceType.values()) {
			if (MAX_TYPE_NUM.get(type) > board.getPieceNumber(nextMove, type)) {
				throw new HantoPrematureResignationException();
			}
		}

		for(MyCoordinate from: board.getCoords(nextMove)){
			HantoPiece piece = board.getPieceAt(from);
			try {
				Collection<MyCoordinate> possibleCoords = coordGenerate(piece.getType(), from);
				for(MyCoordinate to: possibleCoords){
					makeMoveCheck(piece.getType(), from, to);
				}
			} catch (HantoException e) {
				throw new HantoPrematureResignationException();
			}
		}
		
		
	}
	
	public Collection<MyCoordinate> coordGenerate(HantoPieceType type, MyCoordinate coord){
		int distance;
		switch(type){
		case BUTTERFLY:
			distance = 1;
			break;
		case CRAB:
			distance = 1;
			break;
		case HORSE:
			distance = 150;
			break;
		case SPARROW:
			distance = 4;
			break;
		default:
			distance = 0;
		}
		int dx[] = {0,1,1,0,-1,-1};
		int dy[] = {1,0,-1,-1,0,1};
		
		Collection<MyCoordinate> coords = new LinkedList<MyCoordinate>();
		for(int direction = 0; direction < 6; direction ++){
			for(int dist = 1; dist <= distance; dist++){
				coords.add(new MyCoordinate(coord.getX() + dx[direction] * distance, 
						coord.getY() + dy[direction] * distance));
			}
		}
		return coords;
	}

}
