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
import hanto.common.HantoGameID;
import hanto.common.HantoPlayerColor;
import hanto.tournament.HantoGamePlayer;
import hanto.tournament.HantoMoveRecord;

import java.util.Collection;

/**
 * Hanto Player class
 * @author doruk, ruofan
 *
 */
public class HantoPlayer implements HantoGamePlayer {
	public static final double LOWEST_WEIGHT = -1000000;
	EpsilonGameForPlayer game;
	HantoPlayerColor playerColor;

	@Override
	public void startGame(HantoGameID version, HantoPlayerColor myColor,
			boolean doIMoveFirst) {

		playerColor = myColor;
		if (playerColor == HantoPlayerColor.RED) {
			if (doIMoveFirst) {
				game = new EpsilonGameForPlayer(HantoPlayerColor.RED);
			} else {
				game = new EpsilonGameForPlayer(HantoPlayerColor.BLUE);
			}
		} else {
			if (doIMoveFirst) {
				game = new EpsilonGameForPlayer(HantoPlayerColor.BLUE);
			} else {
				game = new EpsilonGameForPlayer(HantoPlayerColor.RED);
			}
		}
	}
	
	private void doNothing(){}

	@Override
	public HantoMoveRecord makeMove(HantoMoveRecord opponentsMove) {

		if (opponentsMove != null) {
			try {
				game.makeMove(opponentsMove.getPiece(),
						opponentsMove.getFrom(), opponentsMove.getTo());
			} catch (HantoException e) {
				doNothing();
			}
		}

		Collection<HantoMoveRecord> possibleMoves = game.getPossibleMoves(playerColor);

		HantoMoveRecord bestMove = new HantoMoveRecord(null, null, null);
		double bestMoveWeight = HantoPlayer.LOWEST_WEIGHT - 1000;
		for (HantoMoveRecord move : possibleMoves) {
			double weight = game.getMoveWeight(move, playerColor);
			if (bestMoveWeight < weight) {
				bestMoveWeight = weight;
				bestMove = move;
			}
		}
		try {
			game.makeMove(bestMove.getPiece(), bestMove.getFrom(),
					bestMove.getTo());
		} catch (HantoException e) {
			doNothing();
		}
		return bestMove;
	}

}
