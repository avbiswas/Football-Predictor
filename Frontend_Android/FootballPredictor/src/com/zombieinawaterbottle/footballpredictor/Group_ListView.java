package com.zombieinawaterbottle.footballpredictor;

import java.util.ArrayList;

import com.zombieinawaterbottle.footballpredictor.Fixture_ListView.MatchHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Group_ListView  extends BaseAdapter{
	public ArrayList<Group> groups;
	public Context context;
	public LayoutInflater inflater;
	public Group_ListView(Context context, ArrayList<Group> grps) {
		// TODO Auto-generated constructor stub
		this.groups=grps;
		this.context=context;
		 inflater = ( LayoutInflater )context.
                 getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(groups.size()<=0)
            return 1;
        return groups.size();
	}

	@Override
	public Group getItem(int pos) {
		// TODO Auto-generated method stub
		return groups.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public View getView(int pos, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row = inflater.inflate(R.layout.group_list,null);
		GroupHolder inst= new GroupHolder();
		inst.name=(TextView) row.findViewById(R.id.group_name);
		inst.strength=(TextView) row.findViewById(R.id.group_strength);
		inst.max=(TextView) row.findViewById(R.id.group_max_pts);
		inst.avg=(TextView) row.findViewById(R.id.group_avg_point);
		inst.rank=(TextView) row.findViewById(R.id.group_rank);
		inst.points=(TextView) row.findViewById(R.id.group_points);
		inst.matchday=(TextView)row.findViewById(R.id.group_matchday);

		//row.setTag(inst);
		inst.name.setText(groups.get(pos).group_name);
		inst.strength.setText("MEMBERS :\t"+groups.get(pos).strength);
		inst.max.setText("LEADER :\t"+groups.get(pos).leader+"  ( "+groups.get(pos).max_points+" pts )");
		
		inst.avg.setText("AVERAGE :\t"+groups.get(pos).avg_points);
		inst.rank.setText("# "+groups.get(pos).user_rank);
		inst.matchday.setText("MATCHDAY :\t"+groups.get(pos).matchday);
				
		inst.points.setText(groups.get(pos).user_points+" pts");
		
		return row;
	}
	class GroupHolder{
		TextView name;
		TextView matchday;
		
		TextView strength;
		TextView avg;
		TextView max;
		TextView rank,points;
	}
	
}