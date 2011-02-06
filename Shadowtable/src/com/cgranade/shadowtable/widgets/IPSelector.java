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
