/**
 * 
 */
package hanto.dorukruofan.beta;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.dorukruofan.alpha.HantoCoordinateGrid;
import hanto.dorukruofan.alpha.Piece;

import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * @author doruk
 *
 */
public class BetaHantoGame implements HantoGame{
	HashMap<String, HantoPiece> piecesOnBoard= new HashMap<String, HantoPiece>();
	Hashtable<HantoPieceType, Integer> redPieceCounter = new Hashtable<HantoPieceType, Integer>();
	Hashtable<HantoPieceType, Integer> bluePieceCounter = new Hashtable<HantoPieceType, Integer>();
	private HantoPlayerColor nextMove;
	private int moveCounter;

	private HantoCoordinateGrid redButterflyLocation;
	private HantoCoordinateGrid blueButterflyLocation;

	public BetaHantoGame(HantoPlayerColor moveFirst){
		this.nextMove = moveFirst;
		this.moveCounter = 0;
		redButterflyLocation = null;
		blueButterflyLocation = null;
		redPieceCounter.put(HantoPieceType.BUTTERFLY, 0);
		redPieceCounter.put(HantoPieceType.SPARROW, 0);
		bluePieceCounter.put(HantoPieceType.BUTTERFLY, 0);
		bluePieceCounter.put(HantoPieceType.SPARROW, 0);

	}

	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		gameEndsCheck();
		onlyPlacingAllowed(from);
		checkSupportedPieces(pieceType);
		firstMoveValidation(to);
		isConnected(to);
		coordinateConflictValidation(to);
		placeButterflyBy4(pieceType);
		pieceNumberCounter(pieceType);


		saveToBoard(to, pieceType);
		moveCounter ++;
		
		if(nextMove == HantoPlayerColor.BLUE)
			nextMove = HantoPlayerColor.RED;
		else 
			nextMove = HantoPlayerColor.BLUE;


		return checkResult();
	}

	private void onlyPlacingAllowed(HantoCoordinate from) throws HantoException{
		if(from != null)
			throw new HantoException("Moving a piece is not supported in Alpha Hanto");
	}

	private void checkSupportedPieces(HantoPieceType pieceType) throws HantoException{
		if(pieceType != HantoPieceType.BUTTERFLY && pieceType != HantoPieceType.SPARROW)
			throw new HantoException(pieceType.getPrintableName() + " is not supported in Beta Hanto");
	}

	private void firstMoveValidation(HantoCoordinate to) throws HantoException{
		if(moveCounter == 0){
			if(!(to.getX() == 0 && to.getY() == 0)){
				throw new HantoException("The first piece should be placed at the origin");
			}
		}
	}
	private void coordinateConflictValidation(HantoCoordinate to) throws HantoException{
		if(piecesOnBoard.containsKey(to.getX()+","+to.getY())){
			throw new HantoException("There is a piece on the specified destination coordinate");
		}
	}
	private void isConnected(HantoCoordinate to) throws HantoException{
		if(moveCounter == 0) {
			return;
		}
		
		HantoCoordinateGrid myCoordinate = new HantoCoordinateGrid(to);
		Collection<HantoCoordinate> neighbors = myCoordinate.getNeighbors();

		for(HantoCoordinate coord: neighbors){
			if(piecesOnBoard.containsKey(HantoCoordinateGrid.getHashCode(coord))){
				return;
			}
		}
		throw new HantoException("The piece is not connected with any other pieces on the board");
	}

	private void gameEndsCheck() throws HantoException{
		if(moveCounter >= 12){
			throw new HantoException("The game ends after 6 turns");
		}
	}
	
	private void placeButterflyBy4(HantoPieceType pieceType) throws HantoException{
		if(moveCounter == 6 || moveCounter == 7){
			switch(nextMove){
			case RED:
				if(redButterflyLocation == null && pieceType != HantoPieceType.BUTTERFLY)
					throw new HantoException("The butterfly should be placed by the 4th turn");
				break;
			case BLUE:
				if(blueButterflyLocation == null && pieceType != HantoPieceType.BUTTERFLY)
					throw new HantoException("The butterfly should be placed by the 4th turn");
				break;
			}
		}
	}

	private void pieceNumberCounter(HantoPieceType type) throws HantoException{
		//TODO separate
		
		if(type == HantoPieceType.BUTTERFLY && 
				nextMove == HantoPlayerColor.BLUE && bluePieceCounter.get(HantoPieceType.BUTTERFLY) >= 1){
				throw new HantoException("There can not be more than one butterfly for each color");			
		}
		if(type == HantoPieceType.BUTTERFLY &&
				nextMove == HantoPlayerColor.RED && redPieceCounter.get(HantoPieceType.BUTTERFLY) >= 1){
			throw new HantoException("There can not be more than one butterfly for each color");			
	}
		switch(nextMove){
		case BLUE:
			bluePieceCounter.put(type, bluePieceCounter.get(type) + 1);
			break;
		case RED:
			redPieceCounter.put(type, redPieceCounter.get(type) + 1);
			break;
		}
	}

	private void saveToBoard(HantoCoordinate to, HantoPieceType pieceType){
		Piece piece = new Piece(nextMove, pieceType);
		piecesOnBoard.put(HantoCoordinateGrid.getHashCode(to), piece);
		if(pieceType == HantoPieceType.BUTTERFLY){
			switch(nextMove){
			case BLUE:
				blueButterflyLocation = new HantoCoordinateGrid(to);
				break;
			case RED:
				redButterflyLocation = new HantoCoordinateGrid(to);
				break;
			}
		}
	}


	private MoveResult checkResult(){
		if(redButterflyLocation != null){
			Collection<HantoCoordinate> neighbors = redButterflyLocation.getNeighbors();
			int enemyNum = 0;
			for(HantoCoordinate coord: neighbors){
				String coorHash = HantoCoordinateGrid.getHashCode(coord);
				if(piecesOnBoard.containsKey(coorHash) && piecesOnBoard.get(coorHash).getColor() == HantoPlayerColor.BLUE){
					enemyNum++;
					break;
				}
			}
			if(enemyNum == 6){
				return MoveResult.BLUE_WINS;
			}
		}

		if(blueButterflyLocation != null){
			Collection<HantoCoordinate> neighbors = blueButterflyLocation.getNeighbors();
			int enemyNum = 0;
			for(HantoCoordinate coord: neighbors){
				String coorHash = HantoCoordinateGrid.getHashCode(coord);
				if(piecesOnBoard.containsKey(coorHash) && piecesOnBoard.get(coorHash).getColor() == HantoPlayerColor.RED){
					enemyNum++;
					break;
				}
			}
			if(enemyNum == 6){
				return MoveResult.RED_WINS;
			}
		}
		
		if(moveCounter < 12){
			return MoveResult.OK;
		}else{
			return MoveResult.DRAW;
		}
	}

	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		return piecesOnBoard.get(HantoCoordinateGrid.getHashCode(where));
	}

	@Override
	public String getPrintableBoard() {
		// TODO Auto-generated method stub
		return null;
	}

}

