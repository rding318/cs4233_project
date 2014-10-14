/**
 * 
 */
package hanto.dorukruofan.tournament;

import hanto.common.HantoPlayerColor;
import hanto.dorukruofan.common.MyCoordinate;
import hanto.dorukruofan.epsilon.EpsilonHantoGame;
import hanto.tournament.HantoMoveRecord;

/**
 * @author ruofan
 *
 */
public class EpsilonGameForPlayer extends EpsilonHantoGame {

	public EpsilonGameForPlayer(HantoPlayerColor moveFirst) {
		super(moveFirst);

	}

	public double getMoveWeight(HantoMoveRecord move, HantoPlayerColor myColor) {
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
		
		/*
		if(board.getAdjacentOccupiedLocation(myButterFly).size() > 2){
			//
		}*/
		
		
		MyCoordinate to = new MyCoordinate(move.getTo());
		double weight = 1.0 / to.distance(opponentButterfly);
		if(move.getFrom() != null){
			MyCoordinate from = new MyCoordinate(move.getFrom());
			weight = weight - 1.0/from.distance(opponentButterfly);
		}
		return weight;
	}
}
