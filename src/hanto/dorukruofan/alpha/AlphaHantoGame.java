package hanto.dorukruofan.alpha;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;

import java.util.HashMap;

public class AlphaHantoGame implements HantoGame{
	HashMap<HantoCoordinate, HantoPiece> piecesOnBoard= new HashMap<HantoCoordinate, HantoPiece>();
	private boolean blueMoves = true;
	private boolean firstMove = true;
	
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		firstMoveValidation(to);
		coordinateConflictValidation(to);
		MoveResult result = getResult(to);
		saveToPiecesOnBoard(to, pieceType);
		return result;
	}
	
	private void firstMoveValidation(HantoCoordinate to) throws HantoException{
		if(firstMove){
			if(!(to.getX() == 0 && to.getY() == 0)){
				throw new HantoException("The first piece should be placed at the origin");
			}
			firstMove = false;
		}
	}
	private void coordinateConflictValidation(HantoCoordinate to) throws HantoException{
		if(piecesOnBoard.containsKey(to)){
			throw new HantoException("There is a piece on the specified destination coordinate");
		}
	}
	private MoveResult getResult(HantoCoordinate to) throws HantoException{
		if(blueMoves){
			blueMoves = false;
			return MoveResult.OK;
		}else{
			blueMoves = true;
			if(!(to.getX() == 0 && (Math.abs(to.getY())) == 1 
					|| to.getY() == 0 && (Math.abs(to.getX()) == 1)
					|| (to.getY() == -1 && to.getX() == 1)
					|| (to.getX() == -1 && to.getY() == 1))){
				throw new HantoException("Second piece should be placed adjacent to the first piece");
			}
			return MoveResult.DRAW;
		}
	}
	private void saveToPiecesOnBoard(HantoCoordinate to, HantoPieceType pieceType){
		Piece piece = new Piece(blueMoves ? HantoPlayerColor.BLUE : HantoPlayerColor.RED, pieceType);
		piecesOnBoard.put(to, piece);
	}

	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPrintableBoard() {
		// TODO Auto-generated method stub
		return null;
	}

}
