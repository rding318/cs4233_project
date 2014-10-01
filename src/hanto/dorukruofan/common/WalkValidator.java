package hanto.dorukruofan.common;

import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

public class WalkValidator extends MoveValidator {

	@Override
	public void moveCheck(Board board, MyCoordinate from, MyCoordinate to, HantoPieceType pieceType, HantoPlayerColor nextMove) throws HantoException {
		correctPiece(board, from, to, pieceType, nextMove);
		if(!isConnected(board, from, to)){
			throw new HantoException("Not Contiguous");
		}
		if(from.distance(to) != 1){
			throw new HantoException("Move distance is not 1 for walk");
		}
	}

}
