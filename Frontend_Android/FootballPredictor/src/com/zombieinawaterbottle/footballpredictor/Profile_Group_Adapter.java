package com.zombieinawaterbottle.footballpredictor;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Profile_Group_Adapter extends BaseAdapter {
	ArrayList<Group> groups;
	LayoutInflater inflater;
	Context context;
	public Profile_Group_Adapter(Context context,ArrayList<Group> groups) {
		// TODO Auto-generated constructor stub
		this.groups=groups;
		this.context=context;
		 inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(groups.size()<=0)
            return 1;
        return groups.size();	}

	@Override
	public Group getItem(int pos) {
		// TODO Auto-generated method stub
		return groups.get(pos);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int pos, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row = inflater.inflate(R.layout.group_list_unit,null);
		TextView name,rank,pts;
		name=(TextView) row.findViewById(R.id.group_name);

		rank=(TextView) row.findViewById(R.id.group_rank);
		pts=(TextView) row.findViewById(R.id.group_points);
		
		name.setText(groups.get(pos).group_name);
		rank.setText("# "+groups.get(pos).user_rank);
		pts.setText("PTS :\t"+groups.get(pos).user_points);
		
		return row;
	}
	

}
