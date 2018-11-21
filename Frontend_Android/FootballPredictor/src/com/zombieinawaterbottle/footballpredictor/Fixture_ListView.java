package com.zombieinawaterbottle.footballpredictor;

import java.util.ArrayList;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class Fixture_ListView  extends BaseAdapter{
	public ArrayList<Match> fixture;
	public Context context;
	public LayoutInflater inflater;
	public Fixture_ListView(Context context, ArrayList<Match> fixture) {
		// TODO Auto-generated constructor stub
		this.fixture=fixture;
		this.context=context;
		 inflater = ( LayoutInflater )context.
                 getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(fixture.size()<=0)
            return 1;
        return fixture.size();
	}

	@Override
	public Match getItem(int pos) {
		// TODO Auto-generated method stub
		return fixture.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		// TODO Auto-generated method stub
		return 0;
	}
	class MatchHolder{
		TextView home;
		TextView away;
		TextView stadium;
		TextView date;
		ImageView home_logo;
		ImageView away_logo;
		CheckBox predict;
	}
	@Override
	public View getView(int pos, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row = inflater.inflate(R.layout.fixture_list,null);
		MatchHolder inst= new MatchHolder();
		inst.home=(TextView) row.findViewById(R.id.textHome);
		inst.away=(TextView) row.findViewById(R.id.textAway);
		inst.stadium=(TextView) row.findViewById(R.id.textStadium);
		inst.date=(TextView) row.findViewById(R.id.textDate);
		inst.home_logo=(ImageView) row.findViewById(R.id.home_logo);
		inst.away_logo=(ImageView) row.findViewById(R.id.away_logo);
		
		//row.setTag(inst);
		inst.home.setText(fixture.get(pos).getHomeTeamName());
		inst.away.setText(fixture.get(pos).getAwayTeamName());
		inst.stadium.setText(fixture.get(pos).stadium);
		inst.date.setText(fixture.get(pos).date);
		inst.home_logo.setImageResource(fixture.get(pos).getHomeTeamIcon());

		inst.away_logo.setImageResource(fixture.get(pos).getAwayTeamIcon());
		inst.predict=(CheckBox) row.findViewById(R.id.predict);
		inst.predict.setChecked(fixture.get(pos).getPredictionStatus());
		
		return row;
	}

}
