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

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Search extends Fragment implements OnClickListener {
	Button submit;
	EditText searchQuery;
	ListView results;
	ProgressBar loading;
	ArrayList<User> search_result_array;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		getActivity().getActionBar().setTitle("SEARCH");
		if (getArguments().containsKey("search_result")){
			search_result_array=(ArrayList<User>) getArguments().getSerializable("search_result");
		}
		else{
		search_result_array=new ArrayList<User>();
		}
		final ArrayList<Match> fixture_arrayList=(ArrayList<Match>) getArguments().getSerializable("fixture");
		View v= inflater.inflate(R.layout.search, container, false);
		
		results = (ListView) v.findViewById(R.id.searchresult);
		loading=(ProgressBar) v.findViewById(R.id.loading);
		submit=(Button) v.findViewById(R.id.search_button);
		submit.setOnClickListener(this);
		searchQuery=(EditText) v.findViewById(R.id.search_query);
		loading.setVisibility(View.GONE);
		refreshList();
		return v;
	}
	private void refreshList() {
		// TODO Auto-generated method stub
		if (search_result_array.size()>0){
			User_ListView adapter = new User_ListView(getActivity(), search_result_array);
			
			results.setAdapter(adapter);
			results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				 
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view,
	                                    int position, long id) {
	            	Fragment fragment=new UserPage(search_result_array.get(position));
	                FragmentManager frm= getFragmentManager();
	                getArguments().putSerializable("search_result", search_result_array);
	                fragment.setArguments(getArguments());
	                frm.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
	                
	            	
	            }
	        });
		
		}
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	    imm.hideSoftInputFromWindow(searchQuery.getWindowToken(), 0);
		loading.setVisibility(View.VISIBLE);
		@SuppressWarnings("unchecked")		
		HashMap<String,String> user_details=(HashMap<String, String>) getArguments().getSerializable("user_details");
		String user_id=user_details.get("user_id");
		String data="?search_id="+searchQuery.getText().toString()+"&user_id="+user_id;
		(new getSearchResult(this, data)).execute();
	}
	public class getSearchResult extends AsyncTask<Void, Void, String>{
		private Search profile;
		private String IP;
		getSearchResult(Search profile, String data){
			this.profile=profile;
			IP=ServerParams.url+ServerParams.searchUser+data;
			
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
	public void handleResponse(String result) {
		// TODO Auto-generated method stub
		JSONObject Json;
		JSONArray search_result = null;
		search_result_array.clear();
		try {
			Json=new JSONObject(result);
			search_result=Json.getJSONArray("data");
			
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i=0;i<search_result.length();i++){
			JSONObject unit_user = null;
			
			try {
				unit_user = (JSONObject) search_result.get(i);
				
				User user= new User(unit_user.getString("id"),
						unit_user.getString("un"),
						unit_user.getString("n"),
						unit_user.getString("c"));
				search_result_array.add(user);
				
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		loading.setVisibility(View.GONE);

		refreshList();
}
	
}
