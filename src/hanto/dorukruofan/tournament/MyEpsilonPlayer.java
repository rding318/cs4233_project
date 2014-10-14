package hanto.dorukruofan.tournament;

import hanto.common.HantoGameID;
import hanto.common.HantoPlayerColor;
import hanto.dorukruofan.epsilon.EpsilonHantoGame;
import hanto.tournament.HantoGamePlayer;
import hanto.tournament.HantoMoveRecord;

public class MyEpsilonPlayer implements HantoGamePlayer{
	EpsilonHantoGame game;

	@Override
	public void startGame(HantoGameID version, HantoPlayerColor myColor,
			boolean doIMoveFirst) {
		
		
		
		game = new EpsilonHantoGame(doIMoveFirst?myColor);
		
	}

	@Override
	public HantoMoveRecord makeMove(HantoMoveRecord opponentsMove) {
		
		return null;
	}

}
