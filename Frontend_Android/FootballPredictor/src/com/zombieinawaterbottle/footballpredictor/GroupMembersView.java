package com.zombieinawaterbottle.footballpredictor;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class GroupMembersView extends Fragment implements OnClickListener{
	TableLayout table;
	String group_id;
	ProgressBar loading;
	ArrayList<Group_Member> members;
	ArrayList<MemberDataHolder> memberViews;
	GroupMembersView(String id){
		group_id=id;
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.member_ranking, container, false);
		
        getActivity().getActionBar().setTitle("GROUPS");
        table=(TableLayout) v.findViewById(R.id.displayRank);
        loading=(ProgressBar) v.findViewById(R.id.loading);
        members=new ArrayList<Group_Member>();
        (new getGroupStat(this,"?group_id="+group_id)).execute();
        return v;
	}
	public class getGroupStat extends AsyncTask<Void, Void, String>{
		private GroupMembersView profile;
		private String IP;
		getGroupStat(GroupMembersView profile, String data){
			this.profile=profile;
			IP=ServerParams.url+ServerParams.groupStat+data;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result==null){
				Toast.makeText(getActivity(), "Can't connect to server", Toast.LENGTH_SHORT).show();
				
			}
			//Log.d("MSG",result);
			profile.handleGroupResponse(result);
			
		}
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String response="0";
			try {
				HttpURLConnection connection=(HttpURLConnection) (new URL(IP)).openConnection();
				connection.setDoOutput(true);
				connection.setRequestProperty("Connection", "close");
				BufferedReader br=new BufferedReader(new InputStreamReader(new BufferedInputStream(connection.getInputStream())));
				response=br.readLine();
			    
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
			return response;
		}
		
	}
		
	public void handleGroupResponse(String groupJSON) {
		// TODO Auto-generated method stub
		JSONArray member_list=null;
		JSONObject temp=null;
		try {
			temp=new JSONObject(groupJSON);
			member_list=temp.getJSONArray("data");
			
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i=0;i<member_list.length();i++){
			JSONObject unit_group = null;
			
			try {
				unit_group = (JSONObject) member_list.get(i);
				Group_Member newGrp= new Group_Member(Integer.toString(i+1),
						unit_group.getString("user_id"),
						unit_group.getString("user_name"),
						unit_group.getString("points"),
						unit_group.getString("predictions"),
						unit_group.getString("latest"));
								
				members.add(newGrp);
				
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		fillUpTable();
	}

	private void fillUpTable() {
		// TODO Auto-generated method stub
		loading.setVisibility(View.GONE);
		LayoutInflater inf= getActivity().getLayoutInflater();
		TableRow row = null;
		for (int i = 0; i < members.size(); i++) {
			row= (TableRow) inf.inflate(R.layout.rank_data, null);
	        
			MemberDataHolder temp1=new MemberDataHolder();
	        TextView rank = (TextView) row.findViewById(R.id.member_rank);
	        rank.setText(Integer.toString(i+1));
	        TextView name = (TextView) row.findViewById(R.id.member_name);
	        name.setText(members.get(i).user_name);
	        TextView points = (TextView) row.findViewById(R.id.member_points);
	        points.setText(members.get(i).points);
	        TextView predictions = (TextView) row.findViewById(R.id.member_predictions);
	        predictions.setText(members.get(i).matches_predicted);
	        TextView latest_points = (TextView) row.findViewById(R.id.member_latest);
	        latest_points.setText(members.get(i).latest_points);
	        
	        
	        rank.setOnClickListener(this);
	        name.setOnClickListener(this);
	        table.addView(row,(i));
	    }
	}
	public class MemberDataHolder{
		TextView rank,name,pts,pdctns,latest;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

}
