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
	
	public InitiativeScore getAsInitiative() {
		return new Combatant.InitiativeScore(getScore(), getIP(), getTie());
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
