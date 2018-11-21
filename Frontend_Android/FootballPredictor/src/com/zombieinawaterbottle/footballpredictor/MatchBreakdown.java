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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.zombieinawaterbottle.footballpredictor.GroupMembersView.MemberDataHolder;
import com.zombieinawaterbottle.footballpredictor.GroupMembersView.getGroupStat;

public class MatchBreakdown  extends Fragment implements OnClickListener{
	TableLayout table;
	Matchday md;
	String user_id;
	ArrayList<Match> members;
	ArrayList<MemberDataHolder> memberViews;
	ProgressBar loading;
	MatchBreakdown(String user,Matchday m){
		this.md=m;

		user_id=user;
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.points_breakdown_matchwise, container, false);
		
        getActivity().getActionBar().setTitle("MATCHDAY "+ md.number +" BREAKDOWN");
        table=(TableLayout) v.findViewById(R.id.display_matchday_stat);
        loading=(ProgressBar) v.findViewById(R.id.loading);
        
        members=new ArrayList<Match>();
        (new getMatchStat(this,"?user_id="+user_id+"&matchday="+md.number)).execute();
        return v;
	}
	public class getMatchStat extends AsyncTask<Void, Void, String>{
		private MatchBreakdown profile;
		private String IP;
		getMatchStat(MatchBreakdown profile, String data){
			this.profile=profile;
			IP=ServerParams.url+ServerParams.matchStat+data;
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
		
	public void handleResponse(String JSON) {
		// TODO Auto-generated method stub
		JSONArray list=null;
		JSONObject temp=null;
		try {
			temp=new JSONObject(JSON);
			list=temp.getJSONArray("data");
			
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i=0;i<list.length();i++){
			JSONObject unit_md = null;
			
			try {
				unit_md = (JSONObject) list.get(i);
				Match newMd= new Match(md.number,
						unit_md.getString("id"),
						
						unit_md.getString("pt"));
				newMd.away_team_id=unit_md.getString("h");
				newMd.home_team_id=unit_md.getString("a");
				newMd.score=unit_md.getString("s");
				
				members.add(newMd);
				
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
			Match temp=members.get(i);
			row= (TableRow) inf.inflate(R.layout.match_data, null);
	        
			ImageView home = (ImageView) row.findViewById(R.id.home_logo);
	        home.setImageResource(temp.getHomeTeamIcon());
	        TextView score = (TextView) row.findViewById(R.id.score);
	        score.setText(temp.score);
	        ImageView away = (ImageView) row.findViewById(R.id.away_logo);
	        away.setImageResource(temp.getAwayTeamIcon());
	        TextView net_points = (TextView) row.findViewById(R.id.points);
	        net_points.setText(temp.points);
	        
	        row.setTag(members.get(i).match_id);
	        row.setOnClickListener(this);
	        table.addView(row,(i));
	    }
	}
	public class MemberDataHolder{
		TextView rank,name,pts,pdctns,latest;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String tag=(String) v.getTag();
		Fragment fragment=new PointBreakdown(tag,user_id);
        FragmentManager frm=getFragmentManager();
        fragment.setArguments(getArguments());
		frm.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
	
	}

}