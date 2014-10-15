/**
 * 
 */
package hanto.studentdorukruofan.common;

import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

/**
 * @author doruk, ruofan
 *
 */
public class FlyValidator extends MoveValidator{
	int maxDistance;
	
	public FlyValidator(){
		maxDistance = Integer.MAX_VALUE;
	}
	
	public FlyValidator(int maxDistance){
		this.maxDistance = maxDistance;
	}

	@Override
	public void moveCheck(Board board, MyCoordinate from, MyCoordinate to,
			HantoPieceType pieceType, HantoPlayerColor nextMove)
					throws HantoException {
		
		correctPiece(board, from, to, pieceType, nextMove);
		
		if(!isConnected(board, from, to)){
			throw new HantoException("Not Contiguous");
		}
		
		if(from.distance(to) > maxDistance){
			throw new HantoException("Unreachable location for Fly");
		}
		
	}

}
