package com.zombieinawaterbottle.footballpredictor;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zombieinawaterbottle.footballpredictor.FriendRequestListViewer.handleFriendReq;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class UserPage extends Fragment implements OnClickListener{
	private final int friend_already=1;
	private final int request_pending=2;
	private final int not_friend=3;
	TextView name,club,points,rank,last_prediction,active_since,loading_text,matches;
	ListView group_stat,users_friends,stat;
	FriendRequests this_request;
	Button addFriend;
	ImageView dp;
	Team_List team_details;
	HashMap<String,String> user_details;
	User user;
	ArrayList<Match> fixture_list;
	ArrayList<Group> group_list;
	Bundle bundle;
	UserPage page;
	ArrayList<Group> groups;
	ArrayList<String> group_String;
	private int friend_status;
	private ArrayList<Friends> friends;
	private ArrayList<FriendRequests> friend_request;
	private Dialog dialog;
	LinearLayout friend_info;
	LinearLayout friend_stat;
	private TextView last_score;
	private ProgressBar loading;
	public UserPage(User user){
		this.user=user;
		
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		bundle=getArguments();
		group_String=new ArrayList<String>();
		user_details = (HashMap<String, String>) bundle.getSerializable("user_details");
		View v= inflater.inflate(R.layout.user_profile, container, false);
		groups=new ArrayList<Group>();
		getActivity().getActionBar().setTitle(user.username+" 's Page");
		name = (TextView)v.findViewById(R.id.profile_name);
		club = (TextView)v.findViewById(R.id.fav_club);
		rank = (TextView)v.findViewById(R.id.rank);
		points = (TextView)v.findViewById(R.id.points);
		active_since=(TextView) v.findViewById(R.id.active_since);
		friend_info = (LinearLayout) v.findViewById(R.id.friends_info);
		group_stat=(ListView)v.findViewById(R.id.visitor_group_stats);
		friend_stat=(LinearLayout)v.findViewById(R.id.visitor_scores);
		matches=(TextView) v.findViewById(R.id.number_of_predictions);
		loading=(ProgressBar) v.findViewById(R.id.loading);
		
		loading_text=(TextView) v.findViewById(R.id.loading_text);
		dp=(ImageView) v.findViewById(R.id.dp);
		(new getDP(dp, "?imageID="+user.user_id)).execute();
		
		friend_status=getStatus();
		
		friend_info.setVisibility(View.INVISIBLE);
		addFriend=(Button)v.findViewById(R.id.addFriend);
        if (friend_status==friend_already){
        	addFriend.setVisibility(View.GONE);
        }
        else if (friend_status==not_friend){
        	addFriend.setText("Add Friend");

        	loading_text.setText("You have to be friends with this person to view more information.");
        }
        else{
        	addFriend.setText("Respond to Request");

        	loading_text.setText("You have to be friends with this person to view more information.\nYou have a pending Friend Request from this person.");
        }
		
		last_prediction = (TextView)v.findViewById(R.id.latest_score_predictions);
        name.setText(user.name);
        last_score = (TextView) v.findViewById(R.id.latest_score);
        addFriend.setOnClickListener(this);
        
        if (friend_status==friend_already)
        	(new getUserDetails(this,"?user_id="+user.user_id)).execute();
        else{
        	loading.setVisibility(View.GONE);
        	
        }
    	
    	page=this;
	    return v;
        
	}
	private int getStatus() {
		// TODO Auto-generated method stub
		friends=(ArrayList<Friends>) getArguments().get("friends_list");
        friend_request=(ArrayList<FriendRequests>) getArguments().get("friend_requests");
        for (int i=0;i<friends.size();i++){
        	if ((user.user_id).equals(friends.get(i).id)){
        		return friend_already;
        	}
        }
        for (int i=0;i<friend_request.size();i++){
        	if ((user.user_id).equals(friend_request.get(i).user_id)){
        		this_request=friend_request.get(i);
        		return request_pending;
        	}
        }
        return not_friend;
        
	}
	public class getUserDetails extends AsyncTask<Void, Void, String>{
		private UserPage profile;
		private String IP;
		getUserDetails(UserPage profile, String data){
			this.profile=profile;
			IP=ServerParams.url+ServerParams.getUserData+data;
			
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result==null){
				Toast.makeText(getActivity(), "Can't connect to server", Toast.LENGTH_SHORT).show();
			}

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

		@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		FragmentManager frm=getFragmentManager();
		Fragment fragment=null;
		
		if (v==addFriend){
			Log.d("MSG",friend_status+"");
			switch(friend_status){
				case not_friend:
					String user_id=user_details.get("user_id");
					String friend_id=user.user_id;
					(new SendFriendRequest(this, "?user_id="+user_id+"&friend_id="+friend_id)).execute();
					break;
				case request_pending:
					showDialog();
					
					
			}
				
		}
		//fragment.setArguments(bundle);
		//frm.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
	}
		private void showDialog() {
			dialog=new Dialog(getActivity());
			dialog.setTitle(user.username+"'s Request");
			dialog.setContentView(R.layout.friend_request);
			TextView name,username,status;
			Button accept,deny;
			
			name=(TextView) dialog.findViewById(R.id.friend_req_name);
			
			username=(TextView) dialog.findViewById(R.id.friend_req_username);
			status=(TextView) dialog.findViewById(R.id.status_writer);
			accept=(Button) dialog.findViewById(R.id.accept_req);
			deny=(Button) dialog.findViewById(R.id.deny_req);
			
			name.setText(user.name);
			username.setVisibility(View.GONE);
			
			accept.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					(new handleReq(page,"?user1="+this_request.user_id+"&user2="+user_details.get("user_id")+"&accept=1","2")).execute();
					}
			});
			deny.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					(new handleReq(page,"?user1="+this_request.user_id+"&user2="+user_details.get("user_id")+"&accept=2","2")).execute();
					}
			});
			dialog.show();
		}
		public class SendFriendRequest extends AsyncTask<Void, Void, String>{
			private UserPage profile;
			private String IP;
			SendFriendRequest(UserPage profile, String data){
				this.profile=profile;
				IP=ServerParams.url+ServerParams.sendFR+data;
			}
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if (result==null){
					Toast.makeText(getActivity(), "Error Occured, Please Try Later.", Toast.LENGTH_SHORT).show();
					
				}
				else{
					Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
					profile.addFriend.setText("Friend Request Sent");
					profile.addFriend.setClickable(false);
				}
				//Log.d("MSG",result);
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
		public void handleResponse(String result) {
			// TODO Auto-generated method stub
			JSONObject Json=null,Json1=null,Json2=null;
			JSONArray temp=null;
			JSONObject user_json,group;
			JSONArray group_json=null;
			
			try {
				Json=new JSONObject(result);
			temp=Json.getJSONArray("data");
			Json1 = (JSONObject) temp.get(0);
			Json2 = (JSONObject) temp.get(1);
			user_json=(JSONObject) Json1.getJSONArray("u_d").get(0);
			group_json=Json2.getJSONArray("g_d");
			
			
			user.points=user_json.getString("pts");
			user.rank=user_json.getString("r");
			user.fav_club_id=user_json.getString("c");
			points.setText("POINTS :  "+user.points);
			rank.setText("# "+user.rank);
			club.setText(Team_List.getName(user.fav_club_id));
	        matches.setText(user_json.getString("mat"));
			last_prediction.setText(user_json.getString("lat_p"));
			last_score.setText(user_json.getString("lat"));
			active_since.setText("Matchday "+user_json.getString("a_s"));
			friend_stat.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Fragment fragment =new MatchdayBreakdown(user.user_id);
					FragmentManager frm=getFragmentManager();
					fragment.setArguments(bundle);
					frm.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
				
				}
			});
			
			for (int i=0;i<group_json.length();i++){
				group=(JSONObject) group_json.get(i);
				Group unitGroup= new Group(group.getString("gid"),
						group.getString("gn"),
						group.getString("gm"));
				unitGroup.user_rank=group.getString("gr");
				unitGroup.user_points=group.getString("gpt");
				groups.add(unitGroup);
				group_String.add((i+1)+".\t"+unitGroup.group_name+" Rank : #"+unitGroup.user_rank);
			}
			fillUpGroups();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		private void fillUpGroups() {
			// TODO Auto-generated method stub
			Profile_Group_Adapter adapter = new Profile_Group_Adapter(getActivity(), groups);
			group_stat.setAdapter(adapter);
			group_stat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				 
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view,
	                                    int position, long id) {
	            	Bundle bundle=getArguments();
	            	Fragment fragment= new GroupMembersView(groups.get(position).group_id);
	            	FragmentManager frm=getFragmentManager();
	        		fragment.setArguments(bundle);

	            	frm.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
	            }});
			loading.setVisibility(View.GONE);
			friend_info.setVisibility(View.VISIBLE);
			loading_text.setVisibility(View.INVISIBLE);
		}
		public class handleReq extends AsyncTask<Void, Void, String>{
			private String IP;
			UserPage profile;
			String status;
			handleReq(UserPage context,String data, String status){
				
				profile=context;
				IP=ServerParams.url+ServerParams.acceptFR+data;
			}
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if (result==null){
					Toast.makeText(getActivity(), "Can't connect to server", Toast.LENGTH_SHORT).show();
					
				}
				else{
					profile.this_request.status=status;
					profile.handleAcceptedResponse(result);
					
				}
				//Log.d("MSG",result);
				
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

		public void handleAcceptedResponse(String result) {
			// TODO Auto-generated method stub
			dialog.dismiss();
			addFriend.setVisibility(View.GONE);
			Friends this_guy= new Friends(user.name, user.username, user.user_id);
			friends.add(this_guy);
		
			
		}

		
}
