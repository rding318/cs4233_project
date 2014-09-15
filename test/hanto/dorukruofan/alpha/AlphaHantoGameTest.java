package hanto.dorukruofan.alpha;

import static org.junit.Assert.assertEquals;
import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPieceType;
import hanto.common.MoveResult;
import hanto.dorukruofan.common.MyCoordinate;

import org.junit.Before;
import org.junit.Test;

/**
 * The class <code>AlphaHantoGameTest</code> contains tests for the class
 * {@link <code>AlphaHantoGame</code>}
 *
 * @pattern JUnit Test Case
 *
 * @generatedBy CodePro at 9/7/14 5:31 PM
 *
 * @author ruofan
 *
 * @version $Revision$
 */
public class AlphaHantoGameTest{
	
	HantoGame alphaGame;
	HantoCoordinate origin;
	HantoCoordinate point1, point2;
	boolean redNotFirst;
	
	@Before
	public void setUp() {
		alphaGame = new AlphaHantoGame();
		point1 = new MyCoordinate(0, 1);
		point2 = new MyCoordinate(1, 1);
		origin = new MyCoordinate(0, 0);
		redNotFirst = true;

	}
	
	@Test(expected = HantoException.class)
	public void coordinateCheck() throws HantoException{
		alphaGame.makeMove(HantoPieceType.BUTTERFLY, null, point1);
	}
	
	@Test
	public void blueFirst() throws HantoException {
		assertEquals(alphaGame.makeMove(HantoPieceType.BUTTERFLY, null, origin), MoveResult.OK);
		assertEquals(alphaGame.makeMove(HantoPieceType.BUTTERFLY, null, point1), MoveResult.DRAW);
	}
	
	@Test(expected = HantoException.class)
	public void twoPieceOnSameCoordinate() throws HantoException{
		alphaGame.makeMove(HantoPieceType.BUTTERFLY, null, origin);
		alphaGame.makeMove(HantoPieceType.BUTTERFLY, null, origin);
	}
	
	@Test(expected = HantoException.class)
	public void notAdjacentToOrigin() throws HantoException{
		alphaGame.makeMove(HantoPieceType.BUTTERFLY, null, origin);
		alphaGame.makeMove(HantoPieceType.BUTTERFLY, null, point2);
	}
}