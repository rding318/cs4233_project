package hanto.dorukruofan.gamma;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.dorukruofan.common.BaseHantoGame;
import hanto.dorukruofan.common.MyCoordinate;

public class GammaHantoGame extends BaseHantoGame{
	public GammaHantoGame(HantoPlayerColor moveFirst){
		super(moveFirst);
		
		MAX_TYPE_NUM.put(HantoPieceType.SPARROW, 5);
		MAX_GAME_TURNS = 20;
	}

	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		gameEndsCheck();
		coordinateConflictValidation(to);
		isConnected(to);
		placeButterflyBy4(pieceType);
		
		if(from == null){
			firstMoveValidation(to);
			pieceNumberCheck(pieceType);
			onlyConnectedToTeamColor(to);
			saveToBoard(to, pieceType);
		}else{
			moveValidator(pieceType, from, to);
			board.movePiece(from, to);
		}
		incrementMove();
		
		
		return checkResult();
	}
	
	
	private void moveValidator(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException{
		if(getPieceAt(from) == null){
			throw new HantoException("There is no piece at the specified from location");
		}else{
			if(getPieceAt(from).getColor() != nextMove){
				throw new HantoException("You are not allowed to move opponent's pieces");
			}
			if(getPieceAt(from).getType() != pieceType){
				throw new HantoException(pieceType.getPrintableName() + " is not at specified location");
			}
			onlyWalk(from, to);
		}
	}
	
	private void onlyWalk(HantoCoordinate from, HantoCoordinate to) throws HantoException{
		if(!board.getAdjacentLocations(from).contains(new MyCoordinate(to))){
			throw new HantoException("Unreachable distance for walk");
		}
	}
	
	public void onlyConnectedToTeamColor(HantoCoordinate to) throws HantoException{
		if(moveCounter < 2)
			return;
		for(HantoPiece piece: board.getAdjacentPieces(to)){
			if(piece.getColor() != nextMove){
				throw new HantoException("Piece can not be put adjacent to opponent's pieces");
			}
		}
	}

}
