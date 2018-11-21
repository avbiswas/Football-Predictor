package com.zombieinawaterbottle.footballpredictor;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;















import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnDismissListener;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends Fragment implements OnClickListener{
	TextView name,points,rank,last_md_score,fixture,my_Group_stat,predict,matches_predicted,no_groups;
	Button search, friends, stats, settings,pl;
	LinearLayout md_stat;
	ImageView dp,club;
	Team_List team_details;
	HashMap<String,String> user_details;
	ArrayList<Match> fixture_list;
	ArrayList<Group> group_list;
	Bundle bundle;
	Dialog dialog;
	ArrayList<String> my_stat_menu;
	ArrayList<String> PL_stat_menu;
	
	private ArrayList<Friends> friends_list;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		bundle=getArguments();
		user_details = (HashMap<String, String>) bundle.getSerializable("user_details");
		fixture_list = (ArrayList<Match>) bundle.getSerializable("fixture");
		group_list = (ArrayList<Group>) bundle.getSerializable("group_details");
		friends_list=(ArrayList<Friends> )getArguments().get("friends_list");
		my_stat_menu=new ArrayList<String>();
		my_stat_menu.add("Performance Overview");

		my_stat_menu.add("Points Breakdown History");
		PL_stat_menu=new ArrayList<String>();
		PL_stat_menu.add("Standings");
		PL_stat_menu.add("Results");
		PL_stat_menu.add("Team Stats");

		View v= inflater.inflate(R.layout.profile, container, false);
		getActivity().getActionBar().setTitle("HOME");
		name = (TextView)v.findViewById(R.id.profile_name);
		club = (ImageView)v.findViewById(R.id.fav_club);
		rank = (TextView)v.findViewById(R.id.rank);
		points = (TextView)v.findViewById(R.id.points);
		matches_predicted=(TextView) v.findViewById(R.id.number_of_predictions);
		my_Group_stat=(TextView)v.findViewById(R.id.group_stats);
		dp=(ImageView) v.findViewById(R.id.dp);
		pl= (Button) v.findViewById(R.id.buttonPL);
		(new getDP(dp, "?imageID="+user_details.get("user_id"))).execute();
		
		fixture = (TextView)v.findViewById(R.id.fixtures);
		predict=(TextView)v.findViewById(R.id.buttonPredict);
		search=(Button)v.findViewById(R.id.buttonFind);
		settings=(Button)v.findViewById(R.id.buttonSettings);
		
		//group=(Button)v.findViewById(R.id.buttonGroup);
		friends=(Button)v.findViewById(R.id.buttonFriends);
		stats=(Button)v.findViewById(R.id.buttonStats);
		no_groups = (TextView)v.findViewById(R.id.profile_no_groups);
        
		last_md_score = (TextView)v.findViewById(R.id.last_matchday_points);
        name.setText(user_details.get("name"));
        club.setImageResource(Team_List.getLogo(user_details.get("club")));
        points.setText("POINTS: "+user_details.get("points"));
        rank.setText("#"+user_details.get("rank"));
        last_md_score.setText(user_details.get("latest_score"));
        matches_predicted.setText(user_details.get("matches"));
        md_stat=(LinearLayout) v.findViewById(R.id.md_stat);
        md_stat.setOnClickListener(this);
        my_Group_stat.setOnClickListener(this);
        predict.setOnClickListener(this);
        search.setOnClickListener(this);
        stats.setOnClickListener(this);
        friends.setOnClickListener(this);
        fixture.setOnClickListener(this);
        pl.setOnClickListener(this);
        //group.setOnClickListener(this);
        fillUpGroup();  
        fillUpFixture();
        return v;
        
	}
		@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		FragmentManager frm=getFragmentManager();
		Fragment fragment=null;
		
		if (v==predict){
			fragment=new UpcomingFixtures();
		}
		else if (v==search){
			fragment=new Search();
		}
		else if (v==settings){
			fragment=new Edit();
		}
		else if (v==stats){
			 dialog = new Dialog(getActivity());
			dialog.setTitle("MY STATS");
			dialog.setContentView(R.layout.menu_view_layout);
			ListView stat_menu=(ListView) dialog.findViewById(R.id.menu_items);
			
			ArrayAdapter<String> adp= new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, my_stat_menu);
			stat_menu.setAdapter(adp);
			
			stat_menu.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View v,
						int pos, long id) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					switch (pos){
					case 0:Toast.makeText(getActivity(), "Coming Soon", Toast.LENGTH_SHORT).show();
							break;
					case 1:
						FragmentManager frm= getFragmentManager();
						Fragment fragment=new MatchdayBreakdown(user_details.get("user_id"));
						fragment.setArguments(bundle);
						frm.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
					
					
					}
				}
				
			});
			
			dialog.show();
		
			
		}
		else if (v==friends){
			dialog = new Dialog(getActivity());
			if (friends_list.size()==0){
				dialog.setTitle("NO FRIENDS");
			}
			else{
			dialog.setTitle("MY FRIENDS");
			dialog.setContentView(R.layout.friend_request_dialog);
			ListView friend_listview=(ListView) dialog.findViewById(R.id.request_list);
			
			FriendsListView adp=new FriendsListView(getActivity(), friends_list);
			friend_listview.setAdapter(adp);
			
			friend_listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View v,
						int pos, long id) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					User temp=friends_list.get(pos).getUser();
					Fragment fragment=new UserPage(temp);
					FragmentManager frm=getFragmentManager();
					fragment.setArguments(bundle);
					frm.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
				}
				
			});
			}
			dialog.show();
		
		}
		else if (v==my_Group_stat){
			fragment=new Groups();
		}
		else if (v==fixture){
			fragment=new UpcomingFixtures();
		}
		else if (v==md_stat){
			Matchday md= new Matchday(user_details.get("latest_matchday"),"0","0",user_details.get("latest_score"));
			fragment=new MatchBreakdown(user_details.get("user_id"), md);
		}
		else if(v==pl){
			 dialog = new Dialog(getActivity());
				dialog.setTitle("PREMIER LEAGUE STATS");
				dialog.setContentView(R.layout.menu_view_layout);
				ListView stat_menu=(ListView) dialog.findViewById(R.id.menu_items);
				
				ArrayAdapter<String> adp= new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, PL_stat_menu);
				stat_menu.setAdapter(adp);
				
				stat_menu.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View v,
							int pos, long id) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						FragmentManager frm= getFragmentManager();
						Fragment fragment=null;
						switch (pos){
						case 0:fragment=new Stat(ServerParams.viewLeagueStandings, "");
								break;
						case 1:
							String matchday=user_details.get("latest_matchday");
							int md=Integer.parseInt(matchday);
							md=md-1;
							fragment=new Stat(ServerParams.viewScoreHistory, "?matchday="+md);
							break;
						case 2:
							showTeamMenu();
							return;
						}
						fragment.setArguments(bundle);
						
						frm.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
						
					}
					
				});
				
				dialog.show();
		}
		if (v!=friends && v!=stats && v!=pl){
		fragment.setArguments(bundle);
		frm.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
		}
	}
	
		protected void showTeamMenu() {
			// TODO Auto-generated method stub
			dialog = new Dialog(getActivity());
			dialog.setTitle("CHOOSE TEAM");
			dialog.setContentView(R.layout.menu_view_layout);
			ListView stat_menu=(ListView) dialog.findViewById(R.id.menu_items);
			Team_List_Viewer adp= new Team_List_Viewer(getActivity());
			
			stat_menu.setAdapter(adp);
			stat_menu.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View v,
						int pos, long id) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					FragmentManager frm= getFragmentManager();
					Fragment fragment=null;
					fragment=new Stat(ServerParams.viewTeamInfo, "?id="+pos);
					fragment.setArguments(bundle);
					
					frm.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();

				}
			});
			dialog.show();
		
		}
		public void fillUpFixture() {
		// TODO Auto-generated method stub
			
			for (int i=0;i<fixture_list.size();i++){
				Match temp=fixture_list.get(i);
				fixture.setText(fixture.getText().toString()+"\n"+Team_List.getName(temp.home_team_id)+ temp.score + Team_List.getName(temp.away_team_id));
				//Log.d("MSG","PRFOLE ==="+temp.home_team + temp.score+temp.away_team);;
			}
			fixture.setMovementMethod(new ScrollingMovementMethod());
			
	}
		private void fillUpGroup() {
			// TODO Auto-generated method stub
			for (int i=0;i<group_list.size();i++){
				Group temp=group_list.get(i);
				my_Group_stat.setText(my_Group_stat.getText().toString()+(i+1)+".\t"+temp.group_name+" : "+"\n\tPoints: "+ temp.user_points + "\tRANK  :  #"+temp.user_rank+"\n\n");
			}
			my_Group_stat.setMovementMethod(new ScrollingMovementMethod());
			
			no_groups.setText(Integer.toString(group_list.size()));
		}
		public class getDP extends AsyncTask<Void, Void, Bitmap>{
			ImageView view;
			String IP;
			getDP(ImageView view, String imageID){
				this.view=view;
					IP=ServerParams.url+ServerParams.getImage+imageID;
				
			}
			@Override
			protected void onPostExecute(Bitmap result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				view.setImageBitmap(result);
				
			}
			@Override
			protected Bitmap doInBackground(Void... params) {
				// TODO Auto-generated method stub
				Bitmap file=null;
				try {
					HttpURLConnection connection=(HttpURLConnection) (new URL(IP)).openConnection();
					connection.setDoOutput(true);
					
			        connection.connect();

					
					
					file=BitmapFactory.decodeStream(connection.getInputStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return file;
			}
			
		}
			
		
}
