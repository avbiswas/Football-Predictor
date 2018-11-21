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
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.zombieinawaterbottle.footballpredictor.GroupMembersView.MemberDataHolder;
import com.zombieinawaterbottle.footballpredictor.GroupMembersView.getGroupStat;

public class MatchdayBreakdown  extends Fragment implements OnClickListener{
	TableLayout table;
	String user_id;
	ArrayList<Matchday> members;
	ProgressBar loading;
	ArrayList<MemberDataHolder> memberViews;
	MatchdayBreakdown(String user){
		user_id=user;
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.points_breakdown, container, false);
		
        getActivity().getActionBar().setTitle("MATCHDAY BREAKDOWN");
        table=(TableLayout) v.findViewById(R.id.display_matchday_stat);
        loading=(ProgressBar) v.findViewById(R.id.loading);
        members=new ArrayList<Matchday>();
        (new getMatchdayStat(this,"?user_id="+user_id)).execute();
        return v;
	}
	public class getMatchdayStat extends AsyncTask<Void, Void, String>{
		private MatchdayBreakdown profile;
		private String IP;
		getMatchdayStat(MatchdayBreakdown profile, String data){
			this.profile=profile;
			IP=ServerParams.url+ServerParams.matchdayStat+data;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result==null){
				Toast.makeText(getActivity(), "Can't connect to server", Toast.LENGTH_SHORT).show();
				
			}
			//Log.d("MSG",result);
			profile.handleResponse(result);
			
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
		
	public void handleResponse(String groupJSON) {
		// TODO Auto-generated method stub
		JSONArray md_list=null;
		JSONObject temp=null;
		try {
			temp=new JSONObject(groupJSON);
			md_list=temp.getJSONArray("data");
			
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i=0;i<md_list.length();i++){
			JSONObject unit_md = null;
			
			try {
				unit_md = (JSONObject) md_list.get(i);
				Matchday newMd= new Matchday(unit_md.getString("no"),
						unit_md.getString("t"),
						unit_md.getString("p"),
						unit_md.getString("pt"));
								
				members.add(newMd);
				
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		int netPoints=0;
		for (int i=members.size()-1;i>=0;i--){
			netPoints+=Integer.parseInt(members.get(i).points);
			members.get(i).setNetScore(Integer.toString(netPoints));
		}
		fillUpTable();
	}

	private void fillUpTable() {
		// TODO Auto-generated method stub
		loading.setVisibility(View.GONE);
		
		LayoutInflater inf= getActivity().getLayoutInflater();
		TableRow row = null;
		for (int i = 0; i < members.size(); i++) {
			Matchday temp=members.get(i);
			row= (TableRow) inf.inflate(R.layout.matchday_data, null);
	        
			MemberDataHolder temp1=new MemberDataHolder();
	        TextView no = (TextView) row.findViewById(R.id.md_no);
	        no.setText(temp.number);
	        TextView tot = (TextView) row.findViewById(R.id.total_matches);
	        tot.setText(temp.total_matches);
	        TextView points = (TextView) row.findViewById(R.id.md_points);
	        points.setText(temp.points);
	        TextView predictions = (TextView) row.findViewById(R.id.no_of_predictions);
	        predictions.setText(temp.predictions);
	        TextView net_points = (TextView) row.findViewById(R.id.md_net_points);
	        net_points.setText(temp.net_score);
	        row.setTag(temp);
	        
	        row.setOnClickListener(this);
	        //name.setOnClickListener(this);
	        table.addView(row,(i));
	    }
	}
	public class MemberDataHolder{
		TextView rank,name,pts,pdctns,latest;
	}
	public Matchday getMatchday(int tag){
		for (int i=0;i<members.size();i++){
			if (members.get(i).number.equals(Integer.toString(tag))){
				return members.get(i);
				
			}
		}
        return null;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Matchday tag = (Matchday)v.getTag();
		Fragment fragment=new MatchBreakdown(user_id,tag);
        FragmentManager frm=getFragmentManager();
        fragment.setArguments(getArguments());
		frm.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
	
        
        
	}

}