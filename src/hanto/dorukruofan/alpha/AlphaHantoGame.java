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
	HashMap<String, HantoPiece> piecesOnBoard= new HashMap<String, HantoPiece>();
	private boolean blueMoves = true;
	private boolean firstMove = true;
	
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		onlyPlacingAllowed(from);
		checkPieceIsButterFly(pieceType);
		firstMoveValidation(to);
		coordinateConflictValidation(to);
		MoveResult result = getResult(to);
		saveToPiecesOnBoard(to, pieceType);
		return result;
	}
	
	private void onlyPlacingAllowed(HantoCoordinate from) throws HantoException{
		if(from != null)
			throw new HantoException("Moving a piece is not supported in Alpha Hanto");
	}
	
	private void checkPieceIsButterFly(HantoPieceType pieceType) throws HantoException{
		if(pieceType != HantoPieceType.BUTTERFLY)
			throw new HantoException("The only piece supported in Alpha is Butterfly");
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
		if(piecesOnBoard.containsKey(to.getX()+","+to.getY())){
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
		Piece piece = new Piece(blueMoves ? HantoPlayerColor.RED : HantoPlayerColor.BLUE, pieceType);
		piecesOnBoard.put(to.getX()+","+to.getY(), piece);
		
	}

	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		return piecesOnBoard.get(where.getX()+","+where.getY());
	}

	@Override
	public String getPrintableBoard() {
		// TODO Auto-generated method stub
		return null;
	}

}
