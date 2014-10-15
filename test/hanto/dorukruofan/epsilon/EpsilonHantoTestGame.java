package hanto.dorukruofan.epsilon;

import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.dorukruofan.common.HantoTestGame;
import hanto.studentdorukruofan.common.MyCoordinate;
import hanto.studentdorukruofan.common.Piece;
import hanto.studentdorukruofan.epsilon.EpsilonHantoGame;

public class EpsilonHantoTestGame extends EpsilonHantoGame implements HantoTestGame{

	public EpsilonHantoTestGame(HantoPlayerColor moveFirst) {
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
		moveCounter = (turnNumber - 1)*2 + (nextMove == moveFirst? 0:1);
	}

	@Override
	public void setPlayerMoving(HantoPlayerColor player) {
		nextMove = player;
	}
	

}