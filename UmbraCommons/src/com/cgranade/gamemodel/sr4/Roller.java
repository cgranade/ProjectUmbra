/**
 * Roller.java: Implements dice pool mechanics.
 **
 * © 2011 Christopher E. Granade (cgranade@gmail.com).
 ** 
 * This file is part of UmbraCommons.
 *
 * UmbraCommons is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * UmbraCommons is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with UmbraCommons.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.cgranade.gamemodel.sr4;

import java.util.Random;

public class Roller {
	
	private static Random rand = new Random();
	
	public static int rollPool(int nDice, boolean ruleOfSixes) {
		int total = 0;
		int roll = 0;
		
		for (int i = 0; i < nDice; i++) {
			roll = rand.nextInt(6);
			if (roll == 5 && ruleOfSixes) i--;
			if (roll >= 4) total++;
		}
		
		return total;
	}
	
}
