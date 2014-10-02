/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.dorukruofan.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author Doruk, Ruofan
 * The board class
 */
public class Board {
	private Map<MyCoordinate, HantoPiece> piecesOnBoard;
	private Hashtable<HantoPieceType, Integer> redPieceCounter = new Hashtable<HantoPieceType, Integer>();
	private Hashtable<HantoPieceType, Integer> bluePieceCounter = new Hashtable<HantoPieceType, Integer>();
	private int size;
	
	/**
	 * Constructor for Board.
	 */
	public Board(){
		piecesOnBoard = new HashMap<MyCoordinate, HantoPiece>();
		for(HantoPieceType type: HantoPieceType.values()){
			redPieceCounter.put(type, 0);
			bluePieceCounter.put(type, 0);
		}
		size = 0;
	}
	
	/**
	 * Constructor for Board.
	 * @param copyBoard Board
	 */
	public Board(Board copyBoard){
		piecesOnBoard = new HashMap<MyCoordinate, HantoPiece>(copyBoard.piecesOnBoard);
		size = copyBoard.size;
		for(HantoPieceType type: HantoPieceType.values()){
			redPieceCounter.put(type, copyBoard.getPieceNumber(HantoPlayerColor.RED, type));
			bluePieceCounter.put(type, copyBoard.getPieceNumber(HantoPlayerColor.BLUE, type));
		}
	}
	
	/**
	 * Finds all adjacent coordinates of a given coordinate.
	
	 * 
	 * @param coordinate HantoCoordinate
	 * @return collection of HantoCoordinate which contains all 6 adjacent coordinates */
	public Collection<MyCoordinate> getAdjacentLocations(HantoCoordinate coordinate){
		int x = coordinate.getX();
		int y = coordinate.getY();
		Collection<MyCoordinate> neighborsLocation = new LinkedList<MyCoordinate>();
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
	
	
	 * @param coordinate HantoCoordinate
	 * @return a collection HantoPiece which contains all pieces on board */
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
	
	/**
	 * Method getAdjacentOccupiedLocation.
	 * @param coordinate MyCoordinate
	 * @return Collection<MyCoordinate>
	 */
	public Collection<MyCoordinate> getAdjacentOccupiedLocation(MyCoordinate coordinate){
		Collection<MyCoordinate> neighbors = new LinkedList<MyCoordinate>();
		for(MyCoordinate coord: getAdjacentLocations(coordinate)){
			HantoPiece piece = piecesOnBoard.get(coord);
			if(piece != null){
				neighbors.add(coord);
			}
		}
		return neighbors;
	}
	
	/**
	 * Method getPieceAt.
	 * @param coordiante HantoCoordinate
	 * @return HantoPiece
	 */
	public HantoPiece getPieceAt(HantoCoordinate coordiante){
		return piecesOnBoard.get(new MyCoordinate(coordiante));
	}
	
	/**
	 * Method putPieceAt.
	 * @param piece HantoPiece
	 * @param coordinate HantoCoordinate
	 */
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
		size++;
	}
		
	/**
	 * Method movePiece.
	 * @param from HantoCoordinate
	 * @param to HantoCoordinate
	 */
	public void movePiece(HantoCoordinate from, HantoCoordinate to){
		HantoPiece piece = getPieceAt(from);
		piecesOnBoard.put(new MyCoordinate(to), piece);
		piecesOnBoard.remove(new MyCoordinate(from));
	}
	
	/**
	 * Method hasPieceAt.
	 * @param coordinate HantoCoordinate
	 * @return boolean
	 */
	public boolean hasPieceAt(HantoCoordinate coordinate){
		return piecesOnBoard.containsKey(new MyCoordinate(coordinate)); 
	}
	
	/**
	 * Method getPieceNumber.
	 * @param color HantoPlayerColor
	 * @param type HantoPieceType
	 * @return int
	 */
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
	
	/**
	 * Method size.
	 * @return int
	 */
	public int size(){
		return size;
	}
	
	/**
	 * Get the pieces information on board in a String
	 * @return a string representing the board
	 */
	public String getPrintableBoard(){
		String result = new String();
		for(MyCoordinate coord: piecesOnBoard.keySet()){
			result = result + coord.toString() + piecesOnBoard.get(coord).getColor()+piecesOnBoard.get(coord).getType()+"\n";
		}
		result = result + "\n";
		return result;
	}
}
