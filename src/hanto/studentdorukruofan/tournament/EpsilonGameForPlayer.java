/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentdorukruofan.tournament;

import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentdorukruofan.common.MyCoordinate;
import hanto.studentdorukruofan.common.WalkValidator;
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
	
	/**
	 * Gets the weight of a given move for hanto player.
	 * Greater the weight, more preferrable the move
	 * @param move
	 * @param myColor
	 * @return weight of the given move
	 */
	public double getMoveWeight(HantoMoveRecord move, HantoPlayerColor myColor) {
		double weight = -100000;
		
		if(move.getPiece() == HantoPieceType.BUTTERFLY && move.getFrom() == null){
			return 100 + Math.random();
		}
		
		if(redButterflyLocation == null || blueButterflyLocation == null){
			if(move.getFrom() == null){
				return Math.random();
			}else{
				return Math.random() * -1;
			}
		}
		
		MyCoordinate myButterFly, opponentButterfly;
		if(myColor == HantoPlayerColor.BLUE){
			myButterFly = blueButterflyLocation;
			opponentButterfly = redButterflyLocation;
		}else{
			myButterFly = redButterflyLocation;
			opponentButterfly = blueButterflyLocation;
		}
		
		boolean isButterflyDanger = false;
		int opponentAroundBF = 0;
		int aroundBF = 0;
		
		int possibleMoves = 6;
		for (MyCoordinate coord: board.getAdjacentLocations(myButterFly)){
			WalkValidator validator = new WalkValidator();
			try {
				validator.moveCheck(board, myButterFly, coord, HantoPieceType.BUTTERFLY, myColor);
			} catch (HantoException e) {
				possibleMoves--;
			}
		}
		if(possibleMoves == 0){
			isButterflyDanger = true;
		}
		
		for (HantoPiece piece : board.getAdjacentPieces(myButterFly)) {
			aroundBF++;
			if (piece.getColor() != myColor) {
				opponentAroundBF++;
			}
		}
		if(aroundBF >= 4 || opponentAroundBF >= 2){
			isButterflyDanger = true;
		}

		
		if(move.getPiece() == HantoPieceType.BUTTERFLY){
			Collection<MyCoordinate> willSurrounded = board.getAdjacentOccupiedLocation(new MyCoordinate(move.getTo()));
			Collection<MyCoordinate> currentSurrounded = board.getAdjacentOccupiedLocation(new MyCoordinate(move.getFrom()));
			
			
			if(!isButterflyDanger){
				weight = 0;
				return weight;
			}
			
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
		
		if (isButterflyDanger) {

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
				if (!isSurrounding && willSurround) {
					weight = HantoPlayer.LOWEST_WEIGHT;
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
			int comparison = Double.compare(currentWeight, weight);
			if(comparison == 0){
				weight = 0;
			}
			if(currentWeight > weight){
				weight = weight - currentWeight;
			}
		}
		return weight;
	}
}
