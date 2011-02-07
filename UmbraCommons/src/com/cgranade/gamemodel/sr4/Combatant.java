/**
 * Combatant.java: Data model for combatants.
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

import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class Combatant {
	
	public static class Condition {
		@SerializedName("damage") private int currentDamage;
		@SerializedName("max") private int maxBoxes;

		public Condition() {}
		
		public Condition(int currentDamage, int maxBoxes) {
			this.currentDamage = currentDamage;
			this.maxBoxes = maxBoxes;
		}
		
		public boolean isOverflowed() {
			return currentDamage > maxBoxes;
		}
		
		public void damageBy(int amt) {
			setCurrentDamage(amt + getCurrentDamage());
		}
		
		public void setCurrentDamage(int currentDamage) {
			this.currentDamage = currentDamage;
		}

		public int getCurrentDamage() {
			return currentDamage;
		}

		public void setMaxBoxes(int maxBoxes) {
			this.maxBoxes = maxBoxes;
		}

		public int getMaxBoxes() {
			return maxBoxes;
		}		
		
	}
	
	public static class InitiativeScore {
		private int rawScore;
		@SerializedName("ip") private int nIP;
		@SerializedName("tie") private int tieBreaker;
		private int score;
		
		public InitiativeScore() {}
		
		public InitiativeScore(int rawScore, int nIP, int tieBreaker) {
			this.rawScore = rawScore;
			this.nIP = nIP;
			this.tieBreaker = tieBreaker;
			
			updateScore();
		}
		
		public int getRawScore() {
			return rawScore;
		}
		
		public void setRawScore(int rawScore) {
			this.rawScore = rawScore;
		}
		
		public int getnIP() {
			return nIP;
		}
		
		public void setnIP(int nIP) {
			this.nIP = nIP;
		}
		
		public int getTieBreaker() {
			return tieBreaker;
		}
		
		public void setTieBreaker(int tieBreaker) {
			this.tieBreaker = tieBreaker;
		}
		
		public void updateScore() {
			score = (int) (rawScore + Roller.rollPool(rawScore, false));
		}
		
		public int getScore() {
			return score;
		}
	}	

	private String name;
	private Map<InitiativeType, InitiativeScore>
		initScores;
	private InitiativeType initType = InitiativeType.PHYSICAL;
	private Map<DamageType, Condition> conditions;
	
	public Combatant() {}
	
	public Combatant(String name, Map<InitiativeType, InitiativeScore> initScores) {
		this.setName(name);
		this.conditions = new HashMap<DamageType, Condition>();
		this.initScores = initScores;
		for (DamageType dt : DamageType.values()) {
			// TODO: don't hardcode in 10 CM boxes.
			this.conditions.put(dt, new Condition(0, 10));
		}
	}

	public InitiativeType getCurrentInitMode() {
		return initType;
	}
	
	public InitiativeScore getCurrentInitScore() {
		return getInitScore(getCurrentInitMode());
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public InitiativeScore getInitScore(InitiativeType type) {
		return initScores.get(type);
	}
	
	@Deprecated
	public void setInitMeat(InitiativeScore initMeat) {
		this.initScores.put(InitiativeType.PHYSICAL, initMeat);
	}

	@Deprecated
	public InitiativeScore getInitMeat() {
		return this.initScores.get(InitiativeType.PHYSICAL);
	}

	public Condition getCondition(DamageType dmgType) {
		return this.conditions.get(dmgType);
	}
	
	public void applyDamage(DamageType dmgType, int amt) {
		this.conditions.get(dmgType).damageBy(amt);
	}
	
	private static int woundMod(int dmg) {
		if (dmg % 3 == 0) {
			return dmg / 3;
		} else {
			return 1 + (dmg / 3);
		}
	}
	
	public int getWoundModifier() {
		return -1 * (
			woundMod(getCondition(DamageType.P).getCurrentDamage()) +
			woundMod(getCondition(DamageType.S).getCurrentDamage())
		);
	}
	
}
