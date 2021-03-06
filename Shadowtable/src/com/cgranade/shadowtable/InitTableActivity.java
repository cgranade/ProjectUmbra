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

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.cgranade.gamemodel.sr4.Combatant;
import com.cgranade.gamemodel.sr4.Combatant.InitiativeScore;
import com.cgranade.gamemodel.sr4.CombatantList;
import com.cgranade.gamemodel.sr4.DamageType;
import com.cgranade.gamemodel.sr4.InitiativeType;
import com.cgranade.gamemodel.sr4.Utils;
import com.cgranade.shadowtable.widgets.CombatantDetailsPane;
import com.cgranade.shadowtable.widgets.InitiativeEditor;

public class InitTableActivity extends Activity {

	// INNER TYPES ////////////////////////////////////////////////////////////
	
	private static enum InputState {
		IDLE,
		TYPING_DAMAGE,
		DAMAGE_TO_WHOM
	}
    
	// MEMBER VARIABLES ///////////////////////////////////////////////////////
	
    private InputState inputState = InputState.IDLE;
    private String dmgSeq = "";
    private DamageType dmgType = DamageType.P;
    
    private ListView initList;
    private CombatantAdapter charAdapter;
    private CombatantList characters;
    private CombatantDetailsPane combDetailsPane;
    
    private TextView cmdLineDisp, curTurn, curIP;
    
    //private SharedPreferences dmPolicies = PreferenceManager.getDefaultSharedPreferences(this);
    
    // CONSTRUCTORS ///////////////////////////////////////////////////////////
    
    public InitTableActivity() { }
 
    // UTILITY METHODS ////////////////////////////////////////////////////////
    
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
    
    // EVENT HANDLERS /////////////////////////////////////////////////////////
    
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
        
        combDetailsPane = (CombatantDetailsPane) findViewById(R.id.comb_details);

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
				
				Combatant ch = charAdapter.getItem(pos);
				
				switch (inputState) {
					case DAMAGE_TO_WHOM: 
						ch.applyDamage(dmgType, Integer.parseInt(dmgSeq));
						inputState = InputState.IDLE;
						charAdapter.notifyDataSetChanged();
						
						updateCmdLineDisplay();
						break;
						
					default:
						combDetailsPane.setCurrentCombatant(ch);
						break;
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
					scores.put(ent.getKey(), ent.getValue().getInitiative());
				}
				
				c = new Combatant(name, scores);
				charAdapter.add(c);
				// TODO: respect policy as to where to add combatants.
				dialog.dismiss();
				
				Log.d("SerializationTest", charAdapter.serializeToJson());
			}
		});
    	
    	dialog.show();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.inittable_options, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// TODO Auto-generated method stub
    	switch (item.getItemId()) {
    		case R.id.dm_policies:
    			Intent prefsIntent = new Intent(this, DMPolicyPreferences.class);
    			startActivity(prefsIntent);
    	    	return true;
	    	default:
	    		return super.onOptionsItemSelected(item);
    	}
    }
}
