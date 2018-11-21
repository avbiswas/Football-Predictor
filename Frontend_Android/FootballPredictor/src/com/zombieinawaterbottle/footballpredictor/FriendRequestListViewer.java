package com.zombieinawaterbottle.footballpredictor;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class FriendRequestListViewer extends BaseAdapter implements OnClickListener{
	public ArrayList<FriendRequests> requests;
	public Context context;
	public ArrayList<Button> request_accept;
	public ArrayList<Button> request_deny;
	public FriendRequestListViewer list;
	public String user_id;
	public LayoutInflater inflater;
	public FriendRequestListViewer(Context context, String user_id, ArrayList<FriendRequests> reqs) {
		// TODO Auto-generated constructor stub
		this.requests=reqs;
		Log.d("MSG","size of request:"+requests.size());
		this.user_id=user_id;
		this.context=context;
		request_accept=new ArrayList<Button>();
		request_deny=new ArrayList<Button>();
		list=this;
		inflater = ( LayoutInflater )context.
                 getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(requests.size()<=0)
            return 1;
        return requests.size();
	}

	@Override
	public FriendRequests getItem(int pos) {
		// TODO Auto-generated method stub
		return requests.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public View getView(int pos, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row = inflater.inflate(R.layout.friend_request,null);
		TextView name,username,status;
		Button accept,deny;
		
		name=(TextView) row.findViewById(R.id.friend_req_name);
		
		username=(TextView) row.findViewById(R.id.friend_req_username);
		status=(TextView) row.findViewById(R.id.status_writer);
		accept=(Button) row.findViewById(R.id.accept_req);
		deny=(Button) row.findViewById(R.id.deny_req);
		
		final FriendRequests tempRequest= requests.get(pos);
		name.setText(tempRequest.name);
		username.setText(tempRequest.username);
		
		User user=new User(tempRequest);
		
		name.setTag(user);
		username.setTag(user);
		name.setOnClickListener(this);
		username.setOnClickListener(this);
		accept.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				(new handleFriendReq(context,"?user1="+tempRequest.user_id+"&user2="+user_id+"&accept=1")).execute();
				tempRequest.status="1";
				list.notifyDataSetChanged();
				}
		});
		deny.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				(new handleFriendReq(context,"?user1="+tempRequest.user_id+"&user2="+user_id+"&accept=2")).execute();
				tempRequest.status="2";
				list.notifyDataSetChanged();
				
			}
		});
		if (tempRequest.status=="0"){
			status.setVisibility(View.INVISIBLE);
		}
		else if(tempRequest.status=="1"){
			status.setText("Accepted");
			accept.setVisibility(View.GONE);

			deny.setVisibility(View.GONE);
			status.setVisibility(View.VISIBLE);
		}
		else if (tempRequest.status=="2"){
			status.setText("Not Accepted");

			status.setVisibility(View.VISIBLE);
			accept.setVisibility(View.GONE);

			deny.setVisibility(View.GONE);
			
		}
		return row;
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
			User query=(User) v.getTag();
			Fragment fragment=new UserPage(query);
			HomeScreen activity=(HomeScreen) context;
			FragmentManager frm=activity.getFragmentManager();
			fragment.setArguments(activity.bundle);
			frm.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
		
	}
	public class handleFriendReq extends AsyncTask<Void, Void, String>{
		private String IP;
		Activity activity;
		handleFriendReq(Context context,String data){
			
			activity=(Activity)context;
			IP=ServerParams.url+ServerParams.acceptFR+data;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result=="Done"){
				Toast.makeText(activity, "Can't connect to server", Toast.LENGTH_SHORT).show();
				
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
	


}
