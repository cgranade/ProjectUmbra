/**
 * IPSelector.java: Compound control for selecting a number of initiative
 *     passes.
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

import com.cgranade.shadowtable.R;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

public class IPSelector extends LinearLayout {

	private static final int MAX_IP = 4;
	
	private final RadioButton[] buttons = new RadioButton[MAX_IP];
	
	private int ip = 1;
	
	private final View.OnClickListener buttonListener = new View.OnClickListener() {		
		public void onClick(View v) {
			setValue((Integer) v.getTag());
		}
	};
	
	private void populateSelf(Context context) {
		
		
		for (int i = 0; i < MAX_IP; i++) {
			RadioButton rb = new RadioButton(context);
			buttons[i] = rb;
			
			rb.setTag(i + 1);
			rb.setOnClickListener(buttonListener);
			
			this.addView(rb);
		}
		
		setValue(1);
		
	}
	
	public int getValue() {
		return ip;
	}
	
	public void setValue(int new_ip) {
		// TODO Auto-generated method stub
		if (new_ip <= 0 || new_ip > MAX_IP) {
			throw new IllegalArgumentException("New value must be in range 1 to " + Integer.toString(MAX_IP) + ".");
		}
		
		ip = new_ip;
		
		for (int i = 0; i < MAX_IP; i++) {
			buttons[i].setChecked(i < ip);
		}
	}

	public IPSelector(Context context) {
		super(context);
		populateSelf(context);
	}
	
	public IPSelector(Context context, AttributeSet attrs) {
		super(context, attrs);
		populateSelf(context);
		
		TypedArray sa = context.obtainStyledAttributes(attrs, R.styleable.IPSelector);
		setValue(sa.getInt(R.styleable.IPSelector_value, 1));
	}

}
