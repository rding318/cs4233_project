package hanto.dorukruofan.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;

public class Board {
	private Map<MyCoordinate, HantoPiece> piecesOnBoard= new HashMap<MyCoordinate, HantoPiece>();
	private Hashtable<HantoPieceType, Integer> redPieceCounter = new Hashtable<HantoPieceType, Integer>();
	private Hashtable<HantoPieceType, Integer> bluePieceCounter = new Hashtable<HantoPieceType, Integer>();
	
	public Board(){
		for(HantoPieceType type: HantoPieceType.values()){
			redPieceCounter.put(type, 0);
			bluePieceCounter.put(type, 0);
		}
	}
	
	/**
	 * Finds all adjacent coordinates of a given coordinate.
	 * @return collection of HantoCoordinate which contains all 6 adjacent coordinates
	 * 
	 */
	public Collection<HantoCoordinate> getAdjacentLocations(HantoCoordinate coordinate){
		int x = coordinate.getX();
		int y = coordinate.getY();
		Collection<HantoCoordinate> neighborsLocation = new LinkedList<HantoCoordinate>();
		neighborsLocation.add(new MyCoordinate(x, y+1));
		neighborsLocation.add(new MyCoordinate(x+1, y));
		neighborsLocation.add(new MyCoordinate(x+1, y-1));
		neighborsLocation.add(new MyCoordinate(x, y-1));
		neighborsLocation.add(new MyCoordinate(x-1, y));
		neighborsLocation.add(new MyCoordinate(x-1, y+1));
		return neighborsLocation;
	}
	
	/**
	 * 
	 * @param game the current Hanto game.
	 * @return a collection HantoPiece which contains all pieces on board
	 */
	public Collection<HantoPiece> getAdjacentPieces(HantoCoordinate coordinate){
		Collection<HantoPiece> neighborsPiece = new LinkedList<HantoPiece>();
		for(HantoCoordinate coord: getAdjacentLocations(coordinate)){
			HantoPiece piece = piecesOnBoard.get(coord);
			if(piece != null){
				neighborsPiece.add(piece);
			}
		}
		return neighborsPiece;
	}
	
	public HantoPiece getPieceAt(HantoCoordinate coordiante){
		return piecesOnBoard.get(new MyCoordinate(coordiante));
	}
	
	public void putPieceAt(HantoPiece piece, HantoCoordinate coordinate){
		piecesOnBoard.put(new MyCoordinate(coordinate), piece);
		
		switch (piece.getColor()){
		case BLUE:
			bluePieceCounter.put(piece.getType(), bluePieceCounter.get(piece.getType()) + 1);
			break;
		case RED:
			redPieceCounter.put(piece.getType(), redPieceCounter.get(piece.getType()) + 1);
			break;
		}
	}
	
	public boolean hasPieceAt(HantoCoordinate coordinate){
		if(piecesOnBoard.containsKey(new MyCoordinate(coordinate))){
			return true;
		}else{
			return false;
		}
	}
	
	public int getPieceNumber(HantoPlayerColor color, HantoPieceType type){
		switch (color){
		case BLUE:
			return bluePieceCounter.get(type);
		case RED:
			return redPieceCounter.get(type);
		default:
			return -1;
		}
	}
}
