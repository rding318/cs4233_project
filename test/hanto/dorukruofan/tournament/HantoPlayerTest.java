package hanto.dorukruofan.tournament;

import hanto.common.HantoGameID;
import hanto.common.HantoPlayerColor;
import hanto.tournament.HantoMoveRecord;

import org.junit.Before;
import org.junit.Test;

public class HantoPlayerTest {
	HantoPlayer player1;
	HantoPlayer player2;
	@Before
	public void initialPlayers(){
		player1 = new HantoPlayer();
		player2 = new HantoPlayer();
		
		player1.startGame(HantoGameID.EPSILON_HANTO, HantoPlayerColor.BLUE, true);
		player2.startGame(HantoGameID.EPSILON_HANTO, HantoPlayerColor.RED, false);
	}
	@Test
	public void testPlay(){
		HantoMoveRecord record;
		record = player1.makeMove(null);
		
		for (int i = 0; i < 100; i++) {
			switch (i % 2) {
			case 0:
				record = player2.makeMove(record);
				break;
			case 1:
				record = player1.makeMove(record);
				break;
			}
		}
	}
	@Test
	public void testPlays(){
		for(int i=0; i < 100; i++){
			testPlay();
		}
	}
}
