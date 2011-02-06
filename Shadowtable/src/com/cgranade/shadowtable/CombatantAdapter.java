/**
 * CombatantAdapter.java: Implements ListView adapter for
 *     Combatant instances.
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.cgranade.gamemodel.sr4.Combatant;
import com.cgranade.gamemodel.sr4.CombatantList;
import com.cgranade.gamemodel.sr4.DamageType;
import com.cgranade.gamemodel.sr4.Utils;
import com.cgranade.shadowtable.R;
import com.google.gson.Gson;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * @author cgranade
 *
 */
public class CombatantAdapter extends ArrayAdapter<Combatant> {

	private CombatantList characters;
	private Map<DamageType, Integer> conditionMonitorIDs;
	private Context context;
	
	public CombatantAdapter(Context context, int textViewResourceId, CombatantList characters) {
		super(context, textViewResourceId, characters);
		this.characters = characters;
		this.context = context;
		
		conditionMonitorIDs = new HashMap<DamageType, Integer>();
		conditionMonitorIDs.put(DamageType.S, R.id.dmg_stun);
		conditionMonitorIDs.put(DamageType.P, R.id.dmg_phys);
	}
	
	private void updateConditionMonitor(TextView mon, Combatant comb, DamageType dt) {
		mon.setText(comb.getCondition(dt).getCurrentDamage() + dt.getAbbrev());
		if (comb.getCondition(dt).isOverflowed()) {
			mon.setTextColor(Color.RED);
		} else {
			mon.setTextColor(context.getResources().getColor(android.R.color.primary_text_dark));
		}
	}
	
	public int getCurrentTurn() { return characters.getCurrentTurn(); }
	
	public void add(Combatant comb) {
		characters.add(comb);
		notifyDataSetChanged();
	}
	
	public void nextCombatant() {
		characters.nextCombatant();
		notifyDataSetChanged();
	}
	
	public void resort() {
		characters.resort();
		notifyDataSetChanged();
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
			View v = null;
		
            try{
            	v = convertView;
            
	            if (v == null) {
	                LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	                v = vi.inflate(R.layout.character_row, null);
	            }
	            
	            Combatant comb = characters.get(position);
	            boolean isCurrent = characters.getCurrentCombatantIndex() == position;
	            
	            v.findViewById(R.id.turn_arrow).setVisibility(isCurrent ? View.VISIBLE : View.INVISIBLE);
	            
	            if (comb != null) {
                    TextView nameView = (TextView) v.findViewById(R.id.char_name);
                    nameView.setText(comb.getName());
                    
                    TextView initView = (TextView) v.findViewById(R.id.cur_init);
                    initView.setText(Integer.toString(comb.getCurrentInitScore().getScore())
                    		+ " " + Utils.ipString(comb.getCurrentInitScore().getnIP())
                    		+ " " + comb.getCurrentInitMode().getLongName());
                    
                    for (Map.Entry<DamageType, Integer> mon: conditionMonitorIDs.entrySet()) {
                    	updateConditionMonitor((TextView) v.findViewById(mon.getValue()), comb, mon.getKey());
                    }
                    
                    TextView wound = (TextView) v.findViewById(R.id.wound_mod);
                    wound.setText(Integer.toString(comb.getWoundModifier()));
	            }
            
            } catch (Exception e) {
            	Log.e("tag", "err", e);
            } finally {
            	return v;
            }
	}

	public int getCurrentIP() { return characters.getCurrentIP(); }
	
	public String serializeToJson() {
		Gson gson = new Gson();
		return gson.toJson(characters);
	}
	
}
