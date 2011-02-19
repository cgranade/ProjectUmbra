/**
 * InitiativeEditor.java: Implements a compound control for manipulating
 *     initiative entries.
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

package com.cgranade.shadowtable.widgets;

import com.cgranade.gamemodel.sr4.Combatant;
import com.cgranade.gamemodel.sr4.Combatant.InitiativeScore;
import com.cgranade.shadowtable.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TableLayout;

public class InitiativeEditor extends TableLayout {

	EditText scoreEdit, tieEdit;
	IPSelector ipSel;
	
	private void populateSelf(Context context) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.init_editor, this);
		
		// Find controls by ID.
		scoreEdit = (EditText) findViewById(R.id.init_picker);
		tieEdit = (EditText) findViewById(R.id.tie_picker);
		ipSel = (IPSelector) findViewById(R.id.ip_selector);
	}
	
	public InitiativeEditor(Context context) {
		super(context);
		populateSelf(context);
	}
	
	public InitiativeEditor(Context context, AttributeSet attrs) {
		super(context, attrs);
		populateSelf(context);
		
		TypedArray sa = context.obtainStyledAttributes(attrs, R.styleable.InitiativeEditor);
		setScore(sa.getInt(R.styleable.InitiativeEditor_score, 0));
		setTie(sa.getInt(R.styleable.InitiativeEditor_tie, 0));
		setIP(sa.getInt(R.styleable.InitiativeEditor_ip, 1));
	}
	
	public InitiativeScore getInitiative() {
		return new Combatant.InitiativeScore(getScore(), getIP(), getTie());
	}
	
	public void setInitiative(InitiativeScore newScore) {
		setScore(newScore.getScore());
		setIP(newScore.getnIP());
		setTie(newScore.getTieBreaker());
	}
	
	public int getScore() {
		return Integer.parseInt(scoreEdit.getText().toString());
	}
	
	public void setScore(int score) {
		scoreEdit.setText(Integer.toString(score));
	}
	
	public int getTie() {
		return Integer.parseInt(tieEdit.getText().toString());
	}
	
	public void setTie(int tie) {
		tieEdit.setText(Integer.toString(tie));
	}
	
	public int getIP() {
		return ipSel.getValue();
	}
	
	public void setIP(int ip) {
		ipSel.setValue(ip);
	}

}
