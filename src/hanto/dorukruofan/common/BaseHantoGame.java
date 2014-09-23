package hanto.dorukruofan.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;

public abstract class BaseHantoGame implements HantoGame {
	protected HantoPlayerColor nextMove;
	protected Board board;
	protected int moveCounter;

	protected MyCoordinate redButterflyLocation;
	protected MyCoordinate blueButterflyLocation;
	
	protected BaseHantoGame(){
		board = new Board();
		moveCounter = 0;
		redButterflyLocation = null;
		blueButterflyLocation = null;
	}
	
	protected void firstMoveValidation(HantoCoordinate to) throws HantoException{
		if(moveCounter == 0){
			if(!(to.getX() == 0 && to.getY() == 0)){
				throw new HantoException("The first piece should be placed at the origin");
			}
		}
	}
	
	protected void coordinateConflictValidation(HantoCoordinate to) throws HantoException{
		if(board.hasPieceAt(to)){
			throw new HantoException("There is a piece on the specified destination coordinate");
		}
	}
	
	protected void placeButterflyBy4(HantoPieceType pieceType) throws HantoException{
		if(moveCounter == 6 || moveCounter == 7){
			switch(nextMove){
			case RED:
				if(redButterflyLocation == null && pieceType != HantoPieceType.BUTTERFLY){
					throw new HantoException("The butterfly should be placed by the 4th turn");
				}
				break;
			case BLUE:
				if(blueButterflyLocation == null && pieceType != HantoPieceType.BUTTERFLY){
					throw new HantoException("The butterfly should be placed by the 4th turn");
				}
				break;
			}
		}
	}
	
	
	//TODO add check for sparrow
	protected void pieceNumberCheck(HantoPieceType type) throws HantoException{
		if(type == HantoPieceType.BUTTERFLY && 
				nextMove == HantoPlayerColor.BLUE && board.getPieceNumber(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY) >= 1){
				throw new HantoException("There can not be more than one butterfly for each color");			
		}
		if(type == HantoPieceType.BUTTERFLY &&
				nextMove == HantoPlayerColor.RED && board.getPieceNumber(HantoPlayerColor.RED, HantoPieceType.BUTTERFLY) >= 1){
			throw new HantoException("There can not be more than one butterfly for each color");			
		}
	}

	protected void saveToBoard(HantoCoordinate to, HantoPieceType pieceType){
		Piece piece = new Piece(nextMove, pieceType);
		board.putPieceAt(piece, to);
		if(pieceType == HantoPieceType.BUTTERFLY){
			switch(nextMove){
			case BLUE:
				blueButterflyLocation = new MyCoordinate(to);
				break;
			case RED:
				redButterflyLocation = new MyCoordinate(to);
				break;
			}
		}
	}
	
	protected void incrementMove(){
		moveCounter ++;
		if(nextMove == HantoPlayerColor.BLUE){
			nextMove = HantoPlayerColor.RED;
		}else{
			nextMove = HantoPlayerColor.BLUE;
		}
	}
	
	protected abstract MoveResult checkResult();
	
	
	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		return board.getPieceAt(where);
	}

	@Override
	public String getPrintableBoard() {
		// TODO Auto-generated method stub
		return null;
	}

}
