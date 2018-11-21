package com.zombieinawaterbottle.footballpredictor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Team_List_Viewer extends BaseAdapter{
	Context context;
	LayoutInflater inflater;
	 public Team_List_Viewer(Context context) {
	        this.context = context;
	        this.inflater = LayoutInflater.from(context);
	    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 20;
	}

	@Override
	public Integer getItem(int pos) {
		// TODO Auto-generated method stub
		return pos;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int pos, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row = inflater.inflate(R.layout.team_list_viewer,null);
		TextView name;ImageView logo;
		name=(TextView) row.findViewById(R.id.name);
		logo=(ImageView) row.findViewById(R.id.logo);
		String id=Integer.toString(pos);
		name.setText(Team_List.getName(id));
		logo.setImageResource(Team_List.getLogo(id));
		
		return row;
	}
	
}
