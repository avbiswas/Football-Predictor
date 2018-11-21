package com.zombieinawaterbottle.footballpredictor;




import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class HomeScreen extends Activity implements OnItemClickListener
		 {
	private ActionBarDrawerToggle mDrawerToggle;
    public Bundle bundle;
    HashMap<String, String> user_details;
    ArrayList<Match> fixture;
    ArrayList<Group> myGroups;
    ArrayList<Friends> friends;
    ArrayList<FriendRequests> requests;
    ArrayList<PredictionObject> predictions;
    String id;
    String name, fav_club,user_name, points,rank;
    String indian;
	private String[] mTitle;
	private CharSequence str;
    private CharSequence mDrawerTitle;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);
		user_details=new HashMap<String, String>();
		requests=new ArrayList<FriendRequests>();
		friends=new ArrayList<Friends>();
		fixture=new ArrayList<Match>();
		myGroups=new ArrayList<Group>();
		predictions=new ArrayList<PredictionObject>();
		Team_List.fill_Team_List();
		Bundle extras = getIntent().getExtras();
		String JSONresponse = null;
		String fixtureJSON=null;
		JSONObject Json=null;
		JSONArray JsonReq=null;
		JSONArray JsonFr=null;
		JSONObject temp=null;
		   
		JSONArray fixture_list = null;
		if (extras != null) {
		   JSONresponse = extras.getString("user");
		   fixtureJSON=extras.getString("fixture");
		  
			
		}
		try {
			Json=new JSONObject(JSONresponse);
			JSONArray tempArr=Json.getJSONArray("data");
			Json=tempArr.getJSONObject(0).getJSONArray("u_d").getJSONObject(0);
			JsonFr=tempArr.getJSONObject(1).getJSONArray("fr");
			JsonReq=tempArr.getJSONObject(2).getJSONArray("req");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			name=Json.getString("name");
			id=Json.getString("user_id");
			(new getGroupStat(this, "?user_id="+id)).execute();
			user_name=Json.getString("user_name");
			fav_club=Json.getString("fav_club_id");
			
			indian=(Json.getString("indian").equals("1"))?"Indian":"Not Indian";
			points=Json.getString("points");
			
			user_details.put("user_id",id);
			user_details.put("name",name);
			user_details.put("club",fav_club);
			user_details.put("country",indian);
			user_details.put("user_name",user_name);
			user_details.put("points",points);
			user_details.put("latest_score",Json.getString("lat"));
			user_details.put("latest_matchday",Json.getString("lat_md"));
			
			user_details.put("user_id",id);
			
			user_details.put("matches", Json.getString("matches"));

			user_details.put("rank", Json.getString("rank"));
			
			for (int i=0;i<JsonReq.length();i++){
				JSONObject requestJson=(JSONObject) JsonReq.get(i);
				FriendRequests req=new FriendRequests(requestJson.getString("un"),requestJson.getString("n"),requestJson.getString("id"));
				requests.add(req);
				
			}
			for (int i=0;i<JsonFr.length();i++){
				JSONObject friendsJson=(JSONObject) JsonFr.get(i);
				Friends frn=new Friends(friendsJson.getString("n"),friendsJson.getString("un"),friendsJson.getString("id"));
				friends.add(frn);
				
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Json=new JSONObject(fixtureJSON);
			fixture_list=Json.getJSONArray("data");
			
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i=0;i<fixture_list.length();i++){
			JSONObject unit_match = null;
			
			try {
				unit_match = (JSONObject) fixture_list.get(i);
				
				Match newMatch= new Match(unit_match.getString("id"),
						unit_match.getString("home"),
						unit_match.getString("away"),
						unit_match.getString("matchday"),
						Team_List.getStadium(unit_match.getString("home")),
						unit_match.getString("date"));
				fixture.add(newMatch);
				
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        
		str = mDrawerTitle = getTitle();
        
		
		mTitle = getResources().getStringArray(R.array.Menu);
        mDrawerList = (ListView) findViewById(R.id.navigation_drawer);

        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.fragment_navigation_drawer, mTitle));
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(255, 72, 31)));;
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,  R.string.navigation_drawer_open,R.string.navigation_drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerList.setOnItemClickListener(this);
	}
	public void gotoWelcomeScreen(){
    	bundle = new Bundle();
    	bundle.putSerializable("user_details", user_details);
        bundle.putSerializable("fixture", fixture);
        bundle.putSerializable("group_details", myGroups);
        bundle.putSerializable("friends_list", friends);
        bundle.putSerializable("friend_requests", requests);
        bundle.putSerializable("predictions", requests);
        
        FragmentManager frm=getFragmentManager();
		Fragment fragment=null;
		fragment=new Profile();
		fragment.setArguments(bundle);
	       
		frm.beginTransaction().replace(R.id.container, fragment).commit();
		
	
    }
    @Override
    public void onPause(){
    	super.onPause();
    }
    @Override
    public void onResume(){
    	super.onResume();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState = bundle;
        super.onSaveInstanceState(savedInstanceState);
    }
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_screen, menu);
       	
        return super.onCreateOptionsMenu(menu);
    }
 @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch(item.getItemId()){
        case R.id.menu_logout: 
        	Intent intent=new Intent(this,Login.class);
        	startActivity(intent);
        	Log.d("MSG","logout");
        	break;
    	case R.id.menu_requests: 
    		final Dialog dialog = new Dialog(this);
    		
    		if (requests.size()!=0){
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

			dialog.setContentView(R.layout.friend_request_dialog);
			ListView request_listview=(ListView) dialog.findViewById(R.id.request_list);
			
			FriendRequestListViewer adp=new FriendRequestListViewer(this, id, requests);
					request_listview.setAdapter(adp);
    		}
    		else{
    			dialog.setTitle("No Friend Requests");
    		}
    		
			dialog.show();
    		break;
    	case R.id.menu_leaderboard:
    		  
    		Fragment fragment=new Stat(ServerParams.viewLeaderboard, "");
    		fragment.setArguments(bundle);
 	       	FragmentManager frm=getFragmentManager();
    		frm.beginTransaction().replace(R.id.container, fragment).commit();

        	Log.d("MSG","leader");
        	break;
    	}
    
        return false;
       }


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
		// TODO Auto-generated method stub
		FragmentManager frm=getFragmentManager();
		Fragment fragment=null;
		switch(pos){
		case 1: fragment=new Groups();
				break;
		case 2:	//bundle.putString("id", id);
        		fragment=new UpcomingFixtures();
				//fragment.setArguments(bundle);
				break;
		case 3:	//bundle.putString("id", id);
				fragment=new Edit();
				//fragment.setArguments(bundle);
				break;
		case 0: gotoWelcomeScreen();
				return;
		}
		fragment.setArguments(bundle);
		frm.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
	
	}
	public class getPredictions extends AsyncTask<Void, Void, String>{
		private HomeScreen profile;
		private String IP;
		getPredictions(HomeScreen profile, String data){
			this.profile=profile;
			IP=ServerParams.url+ServerParams.getPredictions+data;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result==null){
				Toast.makeText(getApplicationContext(), "Can't connect to server", Toast.LENGTH_SHORT).show();
				
			}
			//Log.d("MSG",result);
			profile.handlePredictionsResponse(result);
			
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
		
	
	public class getGroupStat extends AsyncTask<Void, Void, String>{
		private HomeScreen profile;
		private String IP;
		getGroupStat(HomeScreen profile, String data){
			this.profile=profile;
			IP=ServerParams.url+ServerParams.userGroups+data;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result==null){
				Toast.makeText(getApplicationContext(), "Can't connect to server", Toast.LENGTH_SHORT).show();
				
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
		JSONArray group_list=null;
		JSONObject temp=null;
		try {
			temp=new JSONObject(groupJSON);
			group_list=temp.getJSONArray("data");
			
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i=0;i<group_list.length();i++){
			JSONObject unit_group = null;
			
			try {
				unit_group = (JSONObject) group_list.get(i);
				Group newGrp= new Group(unit_group.getString("group_id"),
						unit_group.getString("group_name"),
						unit_group.getString("matchday"));
				newGrp.setUser_points(unit_group.getString("point"));

				newGrp.setUser_Rank(unit_group.getString("rank"));
				newGrp.setUser_Pdctns(unit_group.getString("prediction"));
				newGrp.setAvg(unit_group.getString("avg"));
				newGrp.setLeader(unit_group.getString("leader"));
				newGrp.setMax(unit_group.getString("max"));
				newGrp.setStrength(unit_group.getString("strength"));
				
				
				myGroups.add(newGrp);
				
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		String current_matchday=Integer.toString(Integer.parseInt(user_details.get("latest_matchday"))+1);
        new getPredictions(this,"?id="+id+"&matchday="+current_matchday).execute();

	}
	public void handlePredictionsResponse(String result) {
		// TODO Auto-generated method stub
		JSONArray pd=null;
		JSONObject temp=null;
		try {
			temp=new JSONObject(result);
			pd=temp.getJSONArray("data");
			
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i=0;i<pd.length();i++){
			JSONObject unit_pd = null;
			
			try {
				unit_pd = (JSONObject) pd.get(i);
				PredictionObject newPd= new PredictionObject(unit_pd.getString("m"),
						unit_pd.getString("s1"), unit_pd.getString("s2"), 
						unit_pd.getString("ss"));			
				newPd.prediction_id=unit_pd.getString("pid");
				predictions.add(newPd);
				
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		updateFixtureList();
		gotoWelcomeScreen();
	}
	private void updateFixtureList() {
		// TODO Auto-generated method stub
		for (int i=0;i<predictions.size();i++){
			PredictionObject pd=predictions.get(i);
			for (int j=0;j<fixture.size();j++){
				Match m= fixture.get(j);
				if (pd.match_id.equals(m.match_id)){
					m.predict=true;
					m.prediction=pd;
				}
			}
		}
	}
	@Override
	public void onBackPressed() {
		FragmentManager frm=getFragmentManager();
	    if (frm.getBackStackEntryCount() > 0 ){
	        frm.popBackStackImmediate();
		    
	        frm.beginTransaction().commit();
	    } else {
	        super.onBackPressed();
	    }
	}
}