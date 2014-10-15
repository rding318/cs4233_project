package hanto.dorukruofan.tournament;

import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPlayerColor;
import hanto.tournament.HantoGamePlayer;
import hanto.tournament.HantoMoveRecord;

import java.util.Collection;

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

	@Override
	public HantoMoveRecord makeMove(HantoMoveRecord opponentsMove) {

		if (opponentsMove != null) {
			try {
				game.makeMove(opponentsMove.getPiece(),
						opponentsMove.getFrom(), opponentsMove.getTo());
			} catch (HantoException e) {
			}
		}

		Collection<HantoMoveRecord> possibleMoves = game.getPossibleMoves(playerColor);
		if (possibleMoves.size() == 0) {
			try {
				game.makeMove(null, null, null);
			} catch (HantoException e) {
			}
			return new HantoMoveRecord(null, null, null);
		}

		HantoMoveRecord bestMove = null;
		double bestMoveWeight = -10000000;
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
		}
		return bestMove;
	}

}
