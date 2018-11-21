package com.zombieinawaterbottle.footballpredictor;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class UpcomingFixtures extends Fragment implements OnClickListener {
	Button submit;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		getActivity().getActionBar().setTitle("UPCOMING FIXTURES");
		final ArrayList<Match> fixture_arrayList=(ArrayList<Match>) getArguments().getSerializable("fixture");
		Fixture_ListView adapter = new Fixture_ListView(getActivity(), fixture_arrayList);
		View v= inflater.inflate(R.layout.upcoming_fixtures, container, false);
		ListView list = (ListView) v.findViewById(R.id.fixture);
		submit=(Button) v.findViewById(R.id.submitPredictions);
		submit.setOnClickListener(this);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
            	fixture_arrayList.get(position).predict=true;
            	Fragment fragment=new MatchPredict(fixture_arrayList.get(position).match_id);
                FragmentManager frm= getFragmentManager();
                fragment.setArguments(getArguments());
                frm.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
            }
        });
        return v;
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		ArrayList<PredictionObject> predictions=(ArrayList<PredictionObject>) getArguments().get("predictions");
		PredictionObject temp=predictions.get(0);
		HashMap<String,String> user_details=(HashMap<String, String>) getArguments().getSerializable("user_details");
		String user_id=user_details.get("user_id");
		String data=temp.getIPParameter()+"&user_id="+user_id;
		(new sendPredictions(this, data)).execute();
	}
	public class sendPredictions extends AsyncTask<Void, Void, String>{
		private UpcomingFixtures profile;
		private String IP;
		sendPredictions(UpcomingFixtures profile, String data){
			this.profile=profile;
			IP=ServerParams.url+ServerParams.prediction+data;
			Log.d("MSG",IP);
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
	public void handleResponse(String result) {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
	}
	
}