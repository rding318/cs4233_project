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

import static org.junit.Assert.*;

import org.junit.Test;



/**
 * Test cases for Beta Hanto.
 * @version Sep 14, 2014
 */
public class CommonTest
{
	@Test
	public void distanceTest(){
		MyCoordinate coord1 = new MyCoordinate(0,4);
		MyCoordinate coord2 = new MyCoordinate(0,0);
		MyCoordinate coord3 = new MyCoordinate(-2,2);
		MyCoordinate coord4 = new MyCoordinate(-1,0);
		
		assertEquals(coord1.distance(coord2), 4);
		assertEquals(coord1.distance(coord3), 4);
		assertEquals(coord1.distance(coord4), 5);
	}
	
}
