package hanto.dorukruofan.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

public abstract class BaseHantoGame implements HantoGame {
	protected HantoPlayerColor nextMove;
	protected Board board;
	protected int moveCounter;

	protected MyCoordinate redButterflyLocation;
	protected MyCoordinate blueButterflyLocation;
	
	protected Map<HantoPieceType, Integer> MAX_TYPE_NUM;
	protected int MAX_GAME_TURNS;
	
	protected BaseHantoGame(HantoPlayerColor moveFirst){
		board = new Board();
		moveCounter = 0;
		redButterflyLocation = null;
		blueButterflyLocation = null;
		nextMove = moveFirst;
		
		MAX_TYPE_NUM = new Hashtable<HantoPieceType, Integer> ();
		MAX_TYPE_NUM.put(HantoPieceType.BUTTERFLY, 1);
		MAX_TYPE_NUM.put(HantoPieceType.CRAB, 0);
		MAX_TYPE_NUM.put(HantoPieceType.CRANE, 0);
		MAX_TYPE_NUM.put(HantoPieceType.DOVE, 0);
		MAX_TYPE_NUM.put(HantoPieceType.HORSE, 0);
		MAX_TYPE_NUM.put(HantoPieceType.SPARROW, 0);
	}
	

	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		gameEndsCheck();
		placeButterflyBy4(pieceType);
		
		if(from == null){
			putValidation(pieceType, to);
			saveToBoard(to, pieceType);
		}else{
			MoveValidator validator = getMoveValidator(pieceType);
			validator.moveCheck(board, new MyCoordinate(from), new MyCoordinate(to), pieceType, nextMove);
			board.movePiece(from, to);
		}
		incrementMove();
		
		return checkResult();
	}
	
	protected void putValidation(HantoPieceType pieceType, HantoCoordinate to) throws HantoException{
		firstMoveValidation(to);
		pieceNumberCheck(pieceType);
		isConnected(to);
		coordinateConflictValidation(to);
		onlyConnectedToTeamColor(to);
	}
	
	protected void gameEndsCheck() throws HantoException{
		if(moveCounter >= MAX_GAME_TURNS * 2){
			throw new HantoException("The game ends after " + MAX_GAME_TURNS + " turns");
		}
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
	
	protected void pieceNumberCheck(HantoPieceType type) throws HantoException{
		if(board.getPieceNumber(nextMove, type) >= MAX_TYPE_NUM.get(type)){
			throw new HantoException("There can be no more than " + MAX_TYPE_NUM.get(type) + " " + type.getPrintableName() + " for each color");
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
	
	protected void isConnected(HantoCoordinate to) throws HantoException{
		if(moveCounter == 0) {
			return;
		}
		
		Collection<HantoPiece> neighborsPiece = board.getAdjacentPieces(to);
		
		if(neighborsPiece.size() == 0){
			throw new HantoException("The piece is not connected with any other pieces on the board");
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
	
	protected MoveResult checkResult(){
		if(redButterflyLocation != null){
			Collection<HantoPiece> neighbors = board.getAdjacentPieces(redButterflyLocation);
			if(neighbors.size() == 6){
				return MoveResult.BLUE_WINS;
			}
			System.out.println(neighbors.size());
		}

		if(blueButterflyLocation != null){
			Collection<HantoPiece> neighbors = board.getAdjacentPieces(blueButterflyLocation);
			if(neighbors.size() == 6){
				return MoveResult.RED_WINS;
			}
			System.out.println(neighbors.size());
		}
		
		if(moveCounter < MAX_GAME_TURNS * 2){
			return MoveResult.OK;
		}else{
			return MoveResult.DRAW;
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
		
	private void onlyWalk(HantoCoordinate from, HantoCoordinate to) throws HantoException{
		if(!board.getAdjacentLocations(from).contains(new MyCoordinate(to))){
			throw new HantoException("Unreachable distance for walk");
		}
	}
	
	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		return board.getPieceAt(where);
	}

	@Override
	public String getPrintableBoard() {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected abstract MoveValidator getMoveValidator(HantoPieceType type);

}
