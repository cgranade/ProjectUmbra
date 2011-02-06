/**
 * InitTableActivity.java: Implements the main initiative
 *     table activity.
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

package com.cgranade.shadowtable;

import java.util.HashMap;
import java.util.Map;

import com.cgranade.gamemodel.sr4.Combatant;
import com.cgranade.gamemodel.sr4.Combatant.InitiativeScore;
import com.cgranade.gamemodel.sr4.CombatantList;
import com.cgranade.gamemodel.sr4.DamageType;
import com.cgranade.gamemodel.sr4.InitiativeType;
import com.cgranade.gamemodel.sr4.Utils;
import com.cgranade.shadowtable.widgets.InitiativeEditor;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DialerFilter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class InitTableActivity extends Activity {
    
	private static enum InputState {
		IDLE,
		TYPING_DAMAGE,
		DAMAGE_TO_WHOM
	}
    
    private InputState inputState = InputState.IDLE;
    private String dmgSeq = "";
    private DamageType dmgType = DamageType.P;
    
    private ListView initList;
    private CombatantAdapter charAdapter;
    private CombatantList characters;
    
    private TextView cmdLineDisp, curTurn, curIP;
    
    public InitTableActivity() { }
 
    /** Called with the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate our UI from its XML layout description.
        setContentView(R.layout.inittable_activity);
        
        initList = (ListView) findViewById(R.id.init_list);
        cmdLineDisp = (TextView) findViewById(R.id.cmd_line_display);
        curTurn = (TextView) findViewById(R.id.cur_turn);
        curIP = (TextView) findViewById(R.id.cur_ip);
        curIP.setText(Utils.ipString(1));

        // Set up the initiative list.
        characters = new CombatantList();
        // TODO: recover from stored.
        HashMap<InitiativeType, Combatant.InitiativeScore> initScores =
        		new HashMap<InitiativeType, Combatant.InitiativeScore>();
        initScores.put(InitiativeType.PHYSICAL, new InitiativeScore(9, 3, 3));
        initScores.put(InitiativeType.ASTRAL, new InitiativeScore(2, 1, 3));
        initScores.put(InitiativeType.MATRIX, new InitiativeScore(1, 3, 3));
        characters.add(new Combatant("Trix", (HashMap<InitiativeType, Combatant.InitiativeScore>) initScores.clone()));
        initScores.put(InitiativeType.PHYSICAL, new InitiativeScore(10, 4, 3));
        initScores.put(InitiativeType.ASTRAL, new InitiativeScore(2, 1, 3));
        initScores.put(InitiativeType.MATRIX, new InitiativeScore(1, 3, 3));
        characters.add(new Combatant("Fermi", (HashMap<InitiativeType, Combatant.InitiativeScore>) initScores.clone()));
        charAdapter = new CombatantAdapter(this, R.layout.character_row, characters);
        initList.setAdapter(charAdapter);

        initList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
				if (inputState == InputState.DAMAGE_TO_WHOM) {
					Combatant ch = charAdapter.getItem(pos); 
					ch.applyDamage(dmgType, Integer.parseInt(dmgSeq));
					inputState = InputState.IDLE;
					charAdapter.notifyDataSetChanged();
					
					updateCmdLineDisplay();
				}
			}
        	
		});
        
        ((Button) findViewById(R.id.new_turn_btn)).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				charAdapter.nextCombatant();
				curTurn.setText(Integer.toString(charAdapter.getCurrentTurn()+1));
				curIP.setText(Utils.ipString(charAdapter.getCurrentIP()+1));
			}
		});
    }

    private void updateCmdLineDisplay() {
    	String text = "";
    	
    	switch (inputState) {
    		case IDLE:
    			break;
    			
    		case TYPING_DAMAGE:
    			text = "dmg " + dmgSeq;
    			break;
    			
    		case DAMAGE_TO_WHOM:
				text = "dmg " + dmgSeq + dmgType.toString() + " to whom?";
				break;
    	}
    	
    	cmdLineDisp.setText("> " + text);
    }
    
    public void applyDamageBtnListener(View v) {
    	if (inputState == InputState.IDLE) {
    		inputState = InputState.TYPING_DAMAGE;
    		dmgSeq = "";
    	}
    	
    	String tag = (String) v.getTag();
		dmgSeq = dmgSeq + tag;
		
		updateCmdLineDisplay();
    }
    
    public void finishDamageBtnListener(View v) {
    	if (inputState == InputState.TYPING_DAMAGE) {
    		inputState = InputState.DAMAGE_TO_WHOM;
    		dmgType = Enum.valueOf(DamageType.class, (String) v.getTag());
    	}
    	
    	updateCmdLineDisplay();
    }
    
    public void newCombatantBtnListener(View v) {
    	final Dialog dialog = new Dialog(this);
    	dialog.setContentView(R.layout.new_combatant);
    	dialog.setTitle("New Combatant");
    	
    	final EditText nameEdit = (EditText) dialog.findViewById(R.id.name_edit);
    	
    	final Map<InitiativeType, InitiativeEditor> initEditors = new HashMap<InitiativeType, InitiativeEditor>();
    	initEditors.put(InitiativeType.ASTRAL, (InitiativeEditor) dialog.findViewById(R.id.init_edit_astr));
    	initEditors.put(InitiativeType.PHYSICAL, (InitiativeEditor) dialog.findViewById(R.id.init_edit_phys));
    	initEditors.put(InitiativeType.MATRIX, (InitiativeEditor) dialog.findViewById(R.id.init_edit_matr));

    	final Button addBtn = (Button) dialog.findViewById(R.id.add_btn);
    	addBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Combatant c;
				
				String name = nameEdit.getText().toString();
				Map<InitiativeType, InitiativeScore> scores = new HashMap<InitiativeType, Combatant.InitiativeScore>();
				
				for (Map.Entry<InitiativeType, InitiativeEditor> ent: initEditors.entrySet()) {
					scores.put(ent.getKey(), ent.getValue().getAsInitiative());
				}
				
				c = new Combatant(name, scores);
				charAdapter.add(c);
				dialog.dismiss();
				
				Log.d("SerializationTest", charAdapter.serializeToJson());
			}
		});
    	
    	dialog.show();
    }
}
