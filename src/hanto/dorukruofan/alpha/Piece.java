package hanto.dorukruofan.alpha;

import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

public class Piece implements HantoPiece {
	private HantoPlayerColor color;
	private HantoPieceType type;
	
	public Piece(HantoPlayerColor color, HantoPieceType type){
		this.color = color;
		this.type = type;
	}

	@Override
	public HantoPlayerColor getColor() {
		return color;
	}

	@Override
	public HantoPieceType getType() {
		return type;
	}

}
