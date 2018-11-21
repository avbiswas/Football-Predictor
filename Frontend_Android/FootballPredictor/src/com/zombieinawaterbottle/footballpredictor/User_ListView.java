package com.zombieinawaterbottle.footballpredictor;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class User_ListView extends BaseAdapter{
	public ArrayList<User> users;
	public Context context;
	public LayoutInflater inflater;
	public User_ListView(Context context, ArrayList<User> users) {
		// TODO Auto-generated constructor stub
		this.users=users;
		this.context=context;
		 inflater = ( LayoutInflater )context.
                 getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(users.size()<=0)
            return 1;
        return users.size();
	}

	@Override
	public User getItem(int pos) {
		// TODO Auto-generated method stub
		return users.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public View getView(int pos, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row = inflater.inflate(R.layout.user_list,null);
		TextView name=(TextView) row.findViewById(R.id.search_details);
		ImageView dp=(ImageView) row.findViewById(R.id.search_dp);
		ImageView clubico=(ImageView) row.findViewById(R.id.search_clubico);
		
		name.setText("Name:\t\t"+users.get(pos).name+"\n"+"User:\t\t"+users.get(pos).username);
		dp.setImageResource(R.drawable.arsenal);
		clubico.setImageResource(users.get(pos).getClubIco());
		return row;
	}
	
}