/**
 * 
 */
package hanto.studentdorukruofan.tournament;

import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentdorukruofan.common.MyCoordinate;
import hanto.studentdorukruofan.epsilon.EpsilonHantoGame;
import hanto.tournament.HantoMoveRecord;

import java.util.Collection;

/**
 * @author ruofan
 *
 */
public class EpsilonGameForPlayer extends EpsilonHantoGame {

	public EpsilonGameForPlayer(HantoPlayerColor moveFirst) {
		super(moveFirst);

	}

	public double getMoveWeight(HantoMoveRecord move, HantoPlayerColor myColor) {
		double weight = -100000;
		
		if(redButterflyLocation == null || blueButterflyLocation == null){
			return Math.random();
		}
		MyCoordinate myButterFly, opponentButterfly;
		if(myColor == HantoPlayerColor.BLUE){
			myButterFly = blueButterflyLocation;
			opponentButterfly = redButterflyLocation;
		}else{
			myButterFly = redButterflyLocation;
			opponentButterfly = blueButterflyLocation;
		}
		
		if(move.getPiece() == HantoPieceType.BUTTERFLY){
			Collection<MyCoordinate> willSurrounded = board.getAdjacentOccupiedLocation(new MyCoordinate(move.getTo()));
			Collection<MyCoordinate> currentSurrounded = board.getAdjacentOccupiedLocation(new MyCoordinate(move.getTo()));
			if(willSurrounded.size() - 1 > currentSurrounded.size()){
				weight = HantoPlayer.LOWEST_WEIGHT;
				return weight;
			}else if(willSurrounded.size() - 1 == currentSurrounded.size()){
				weight = 0;
				return weight;
			}else{
				weight = 6 - (willSurrounded.size() - 1);
				return weight;
			}
		}
		
		if (board.getAdjacentOccupiedLocation(myButterFly).size() > 3) {

			Collection<MyCoordinate> surrounded = board
					.getAdjacentOccupiedLocation(myButterFly);
			Collection<MyCoordinate> neighbors = board
					.getAdjacentLocations(myButterFly);
			if (move.getFrom() != null) {
				boolean isSurrounding, willSurround;
				isSurrounding = surrounded.contains(new MyCoordinate(move
						.getFrom()));
				willSurround = neighbors
						.contains(new MyCoordinate(move.getTo()));
				if (isSurrounding && !willSurround) {
					weight = 6 - (surrounded.size() - 1);
					return weight;
				}
				if (isSurrounding && willSurround) {

				}
				if (!isSurrounding && willSurround) {
					weight = HantoPlayer.LOWEST_WEIGHT;
				}
				if (!isSurrounding && !willSurround) {

				}
			} else {
				if (neighbors.contains(new MyCoordinate(move.getTo()))) {
					weight = HantoPlayer.LOWEST_WEIGHT;
				}
			}

		}
		
		
		MyCoordinate to = new MyCoordinate(move.getTo());
		weight = 1.0 / to.distance(opponentButterfly);
		
		if(move.getFrom() != null){
			MyCoordinate from = new MyCoordinate(move.getFrom());
			double currentWeight = 1.0/from.distance(opponentButterfly);
			if(currentWeight == weight){
				weight = 0;
			}
			if(currentWeight > weight){
				weight = weight - currentWeight;
			}
		}
		return weight;
	}
}
