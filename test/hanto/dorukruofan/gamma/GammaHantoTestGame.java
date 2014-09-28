package hanto.dorukruofan.gamma;

import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.dorukruofan.common.HantoTestGame;
import hanto.dorukruofan.common.MyCoordinate;
import hanto.dorukruofan.common.Piece;


public class GammaHantoTestGame extends GammaHantoGame implements HantoTestGame{

	public GammaHantoTestGame(HantoPlayerColor moveFirst) {
		super(moveFirst);
	}

	@Override
	public void initializeBoard(PieceLocationPair[] initialPieces) {
		for(PieceLocationPair piece: initialPieces){
			board.putPieceAt(new Piece(piece.player, piece.pieceType), new MyCoordinate(piece.location));
			if(piece.pieceType == HantoPieceType.BUTTERFLY){
				switch(piece.player){
				case BLUE:
					blueButterflyLocation = new MyCoordinate(piece.location);
					break;
				case RED:
					redButterflyLocation = new MyCoordinate(piece.location);
					break;
				}
			}
		}
	}

	@Override
	public void setTurnNumber(int turnNumber) {
		moveCounter = turnNumber * 2;
	}

	@Override
	public void setPlayerMoving(HantoPlayerColor player) {
		if(nextMove != player){
			nextMove = player;
			moveCounter ++;
		}
	}
	

}
