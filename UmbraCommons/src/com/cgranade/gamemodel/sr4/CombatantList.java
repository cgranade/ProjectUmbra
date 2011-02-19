/**
 * CombatantList.java: Data model for a list of combatants.
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

import java.util.Collections;
import java.util.Comparator;

import com.cgranade.gamemodel.sr4.Combatant.InitiativeScore;

public class CombatantList extends java.util.ArrayList<Combatant> {

	private static final long serialVersionUID = 1L;
	
	static final Comparator<Combatant> DEFAULT_COMPARATOR = new Comparator<Combatant>() {
		public int compare(Combatant comb1, Combatant comb2) {
			InitiativeScore is1 = comb1.getCurrentInitScore(), is2 = comb2.getCurrentInitScore();
			if (is1.getScore() < is2.getScore()) {
				return -1;
			} else if (is1.getScore() > is2.getScore()) {
				return 1;
			} else {
				return ((Integer) is1.getTieBreaker()).compareTo(is2.getTieBreaker());
			}
		}
	};
	
	private static final int MAX_IP = 4;
	
	private int nowTurn = 0;
	private int nowIP = 0;
	private int nowScore = Integer.MAX_VALUE;
	private int nowTie = Integer.MAX_VALUE;
	private int nowTie2 = 0; // Used if there's a tie in the tiebreakers.
							 // TODO: figure out how to implement nowTie2.
	
	private int currentComb = -1;
	
	/**
	 * 
	 * @warning Assumes that the CombatantList has been sorted already.
	 * @return The smallest index {@code idx} such that {@code get(idx).getCurrentInitScore() <= time}.
	 */
	public int timeToIndex(InitiativeScore time) {
		// TODO!
		throw new UnsupportedOperationException("not yet implemented");
	}
	
	@Override
	public void add(int index, Combatant element) {
		// TODO: handle possible invalidation of currentComb.
		throw new UnsupportedOperationException("not yet implemented");
		//super.add(index, element);
	}
	
	public void addInNaturalOrder(Combatant comb) {
		add(timeToIndex(comb.getCurrentInitScore()), comb);
	}
	
	public int getCurrentTurn() {
		return nowTurn;
	}
	
	public void nextCombatant() {
		
		boolean canGo = false;
		Combatant comb = getCurrentCombatant();
		
		while (!canGo) {
			currentComb++;
			
			if (currentComb >= size()) {
				currentComb = 0;
				nowIP++;
				if (nowIP >= MAX_IP) {
					nowIP = 0;
					nowTurn++;
				}
			}
			
			comb = getCurrentCombatant();
			canGo = comb == null ? true : nowIP < comb.getCurrentInitScore().getnIP();
		}
		
		nowScore = comb.getCurrentInitScore().getScore();
		nowTie = comb.getCurrentInitScore().getTieBreaker();
		
	}
	
	public int getCurrentCombatantIndex() {
		return currentComb;
	}
	
	public Combatant getCurrentCombatant() {
		if (currentComb >= 0)
			return get(currentComb);
		else
			return null;
	}
	
	public void resort() {
		Collections.sort(this, DEFAULT_COMPARATOR);
	}

	public int getCurrentIP() {
		return nowIP;
	}

}
