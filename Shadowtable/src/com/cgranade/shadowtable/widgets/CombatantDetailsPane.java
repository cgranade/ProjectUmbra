/**
 * CombatantDetailsPane.java: Implements a pane showing details of a
 *     selected combatant.
 **
 * © 2011 Christopher E. Granade (cgranade@gmail.com).
 ** 
 * This file is part of Shadowtable.
 *
 * Shadowtable is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Shadowtable is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Shadowtable.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * TODO: There is currently no feedback to the outside world when values are changed here.
 * TODO: Add information on connected players.
 */

package com.cgranade.shadowtable.widgets;

import java.util.HashMap;
import java.util.Map;

import com.cgranade.gamemodel.sr4.Combatant;
import com.cgranade.gamemodel.sr4.Combatant.Condition;
import com.cgranade.gamemodel.sr4.DamageType;
import com.cgranade.gamemodel.sr4.InitiativeType;
import com.cgranade.shadowtable.R;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class CombatantDetailsPane extends LinearLayout {

	// MEMBER VARIABLES /////////////////////////////////////////////
	
	// DATA //
	Combatant currentCombatant = null;
	 
	// CONTROLS // 
	TextView combNameView, combKindView, woundModView;
	Map<InitiativeType, ToggleButton> initToggles = new HashMap<InitiativeType, ToggleButton>();
	Map<InitiativeType, InitiativeEditor> initEditors = new HashMap<InitiativeType, InitiativeEditor>();
	Map<DamageType, TextView> conditionMonitors = new HashMap<DamageType, TextView>();
	
	// PRIVATE METHODS //////////////////////////////////////////////
	
	private void populateSelf(Context context) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.combatant_details, this);
		
		// Find controls by ID.
		combNameView = (TextView) findViewById(R.id.comb_name);
		combKindView = (TextView) findViewById(R.id.comb_kind);
		woundModView = (TextView) findViewById(R.id.wound_mod);
		
		initToggles.put(InitiativeType.ASTRAL, (ToggleButton) findViewById(R.id.inittoggle_astr));
		initEditors.put(InitiativeType.ASTRAL, (InitiativeEditor) findViewById(R.id.init_edit_astr));
		initToggles.put(InitiativeType.MATRIX, (ToggleButton) findViewById(R.id.inittoggle_matr));
		initEditors.put(InitiativeType.MATRIX, (InitiativeEditor) findViewById(R.id.init_edit_matr));
		initToggles.put(InitiativeType.PHYSICAL, (ToggleButton) findViewById(R.id.inittoggle_phys));
		initEditors.put(InitiativeType.PHYSICAL, (InitiativeEditor) findViewById(R.id.init_edit_phys));
		
		conditionMonitors.put(DamageType.P, (TextView) findViewById(R.id.cm_phys));
		conditionMonitors.put(DamageType.S, (TextView) findViewById(R.id.cm_stun));
//		scoreEdit = (EditText) findViewById(R.id.init_picker);
//		tieEdit = (EditText) findViewById(R.id.tie_picker);
//		ipSel = (IPSelector) findViewById(R.id.ip_selector);
	}
	
	// CONSTRUCTORS /////////////////////////////////////////////////
	
	public CombatantDetailsPane(Context context) {
		super(context);
		populateSelf(context);
	}
	
	public CombatantDetailsPane(Context context, AttributeSet attrs) {
		super(context, attrs);
		populateSelf(context);
		
//		TypedArray sa = context.obtainStyledAttributes(attrs, R.styleable.InitiativeEditor);
//		setScore(sa.getInt(R.styleable.InitiativeEditor_score, 0));
//		setTie(sa.getInt(R.styleable.InitiativeEditor_tie, 0));
//		setIP(sa.getInt(R.styleable.InitiativeEditor_ip, 1));
	}

	// PUBLIC METHODS ///////////////////////////////////////////////
	
	/**
	 * Clients should call this method whenever the current combatant
	 * has been updated to ensure that the UI reflects these changes.
	 */
	public void notifyCombatantChanged() {		
		combNameView.setText(currentCombatant.getName());
		// TODO: base this on actual information!
		combKindView.setText("Player Character");
		woundModView.setText("Wound Mod: " + Integer.toString(currentCombatant.getWoundModifier()));
		
		// Update initiative editors and toggles.
		for (InitiativeType it: InitiativeType.values()) {
			initEditors.get(it).setInitiative(currentCombatant.getInitScore(it));
			initToggles.get(it).setChecked(currentCombatant.getCurrentInitMode() == it);
		}
		
		// Update condition monitors.
		for (DamageType dt: DamageType.values()) {
			Condition cond = currentCombatant.getCondition(dt);
			TextView mon = conditionMonitors.get(dt);
			mon.setText(
					Integer.toString(cond.getCurrentDamage()) +
					dt.toString() + " / " +
					Integer.toString(cond.getMaxBoxes())
			);
			
			// TODO: consolidate this with similar code from CombatantAdapter.
			if (cond.isOverflowed()) {
				mon.setTextColor(Color.RED);
			} else {
				mon.setTextColor(getContext().getResources().getColor(android.R.color.primary_text_dark));
			}
		}
	}
	
	// ACCESSORS AND MUTATORS ///////////////////////////////////////
	
	public void setCurrentCombatant(Combatant currentCombatant) {
		this.currentCombatant = currentCombatant;
		notifyCombatantChanged();
	}
	
}
