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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class FriendsListView extends BaseAdapter{
public ArrayList<Friends> friends;
public Context context;
public String user_id;
public LayoutInflater inflater;
public FriendsListView(Context context, ArrayList<Friends> frns) {
	// TODO Auto-generated constructor stub
	this.friends=frns;
	this.user_id=user_id;
	this.context=context;
	 inflater = ( LayoutInflater )context.
             getSystemService(Context.LAYOUT_INFLATER_SERVICE);
}
@Override
public int getCount() {
	// TODO Auto-generated method stub
	if(friends.size()<=0)
        return 1;
    return friends.size();
}

@Override
public Friends getItem(int pos) {
	// TODO Auto-generated method stub
	return friends.get(pos);
}

@Override
public long getItemId(int pos) {
	// TODO Auto-generated method stub
	return 0;
}
@Override
public View getView(int pos, View v, ViewGroup parent) {
	// TODO Auto-generated method stub
	View row = inflater.inflate(R.layout.friend_list,null);
	TextView name,username;
	
	name=(TextView) row.findViewById(R.id.friend_req_name);
	username=(TextView) row.findViewById(R.id.friend_req_username);
	final Friends temp = friends.get(pos);
	name.setText(temp.name);
	username.setText(temp.username);
	
		return row;
}

}
