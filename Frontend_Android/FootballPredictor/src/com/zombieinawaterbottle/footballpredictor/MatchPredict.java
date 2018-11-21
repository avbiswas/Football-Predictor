package com.zombieinawaterbottle.footballpredictor;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MatchPredict extends Fragment implements OnValueChangeListener, OnClickListener, OnItemSelectedListener{
	String match_id;
	ArrayList<Match> temp;
	Spinner homescorers,awayscorers;
	String home_prd;
	String away_prd;
	ArrayList<Player> HomeTeam;
	ArrayList<Player> AwayTeam;
	ArrayList<String> homeSquad;
	ArrayList<String> awaySquad;
	
	ArrayList<ScorerSpinners> homeScorers;
	ArrayList<ScorerSpinners> awayScorers;
	PredictionObject currentPrd;
	boolean alreadyPredicted;
	
	
	ArrayAdapter<String> home_adp; 
	ArrayAdapter<String> away_adp;
	ArrayList<String> scorers_list;
	Button save_prediction;
	NumberPicker homescore, awayscore;
	LinearLayout homeLayout;
	LinearLayout awayLayout;
		public MatchPredict(String id){
			match_id=id;
			
		}
		@SuppressWarnings("unchecked")
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        View v= inflater.inflate(R.layout.predict_match, container, false);
			temp=(ArrayList<Match>) getArguments().getSerializable("fixture");
	        getActivity().getActionBar().setTitle("MATCH PREDICTION");
	        Match match=getMatch();
	        HomeTeam=new ArrayList<Player>();
	        AwayTeam=new ArrayList<Player>();
	        homeSquad=new ArrayList<String>();
	        awaySquad=new ArrayList<String>();
	        scorers_list=new ArrayList<String>();
	        homeScorers=new ArrayList<MatchPredict.ScorerSpinners>();
	        awayScorers= new ArrayList<MatchPredict.ScorerSpinners>();
	        TextView home=(TextView) v.findViewById(R.id.textHome);
			TextView away=(TextView) v.findViewById(R.id.textAway);
			TextView stadium=(TextView) v.findViewById(R.id.textStadium);
			TextView date=(TextView) v.findViewById(R.id.textDate);
			ImageView home_logo=(ImageView) v.findViewById(R.id.home_logo);
			ImageView away_logo=(ImageView) v.findViewById(R.id.away_logo);
			homeLayout =(LinearLayout) v.findViewById(R.id.homeLayout);
			awayLayout =(LinearLayout) v.findViewById(R.id.awayLayout);
			homescore=(NumberPicker) v.findViewById(R.id.pickHomeScore);
			awayscore=(NumberPicker) v.findViewById(R.id.pickAwayScore);
			
			save_prediction=(Button) v.findViewById(R.id.save_prediction);
			save_prediction.setOnClickListener(this);
			(new getTeam(this,"?match_id="+match_id)).execute();
			
			home.setText(match.getHomeTeamName());
			away.setText(match.getAwayTeamName());
			stadium.setText(match.stadium);
			date.setText(match.date);
			home_logo.setImageResource(match.getHomeTeamIcon());

			away_logo.setImageResource(match.getAwayTeamIcon());
			
			homescore.setMinValue(0);
			homescore.setMaxValue(10);
	        homescore.setWrapSelectorWheel(true);

			awayscore.setMinValue(0);
			awayscore.setMaxValue(10);
	        homescore.setWrapSelectorWheel(true);
	        awayscore.setWrapSelectorWheel(true);
	        
	        homescore.setOnValueChangedListener(this);

			awayscore.setOnValueChangedListener(this);
			alreadyPredicted=match.predict;
			if (match.predict=true){
				currentPrd=match.prediction;
				homescore.setValue(Integer.parseInt(currentPrd.home_score));

				awayscore.setValue(Integer.parseInt(currentPrd.away_score));
			}
	        return v;
		}

		private Match getMatch() {
			// TODO Auto-generated method stub
			for (int i=0;i<temp.size();i++){
				if (temp.get(i).match_id==this.match_id){
					return temp.get(i);
				}
			}
			return null;
		}
		public class getTeam extends AsyncTask<Void, Void, String>{
			private MatchPredict profile;
			private String IP;
			getTeam(MatchPredict profile, String data){
				this.profile=profile;
				IP=ServerParams.url+ServerParams.getTeam+data;
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
		@SuppressLint("InflateParams")
		public Spinner getSpinnerObject(){
			LayoutInflater inf= getActivity().getLayoutInflater();
			View view=inf.inflate(R.layout.squad_spinner, null);
			Spinner spinner=(Spinner) view.findViewById(R.id.spinnerScorers);
			//Spinner spinner=new Spinner(getActivity());
			spinner.setOnItemSelectedListener(this);
			return spinner;
			
		}
		
		public ArrayList<Player> fillInSquad(String playerJSON) {
			// TODO Auto-generated method stub
			JSONArray player_list=null;
			JSONObject temp=null;
			
			ArrayList<Player> tempSquad=new ArrayList<Player>();
			try {
				temp=new JSONObject(playerJSON);
				player_list=temp.getJSONArray("data");
				
				
			}
			catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for (int i=0;i<player_list.length();i++){
				JSONObject unit_player = null;
				
				try {
					unit_player = (JSONObject) player_list.get(i);
					Player newPl= new Player(unit_player.getString("id"),
							unit_player.getString("n"),
							unit_player.getString("j"),
							unit_player.getString("p"));
						tempSquad.add(newPl);
						
					
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
	        return tempSquad;

		}
		public void handleResponse(String result) {
			// TODO Auto-generated method stub
			String[] myList = new String[2]; 
			myList=result.split("//");
		
			HomeTeam=fillInSquad(myList[0]);
			AwayTeam=fillInSquad(myList[1]);
			homeSquad.add("None");
			awaySquad.add("None");
			
			for (int i=0;i<HomeTeam.size();i++){
				homeSquad.add(HomeTeam.get(i).getString());
			}
			for (int i=0;i<AwayTeam.size();i++){
				awaySquad.add(AwayTeam.get(i).getString());
			}
			home_adp = new ArrayAdapter<String>(getActivity(),R.layout.score_spinners, homeSquad);
			away_adp = new ArrayAdapter<String>(getActivity(),R.layout.score_spinners, awaySquad);
			home_adp.setDropDownViewResource(R.layout.score_spinners);
			away_adp.setDropDownViewResource(R.layout.score_spinners);
			Spinner spinner=getSpinnerObject();
			spinner.setAdapter(home_adp);
			
			ScorerSpinners temp= new ScorerSpinners(spinner,"");
			homeScorers.add(temp);
			
			homeLayout.addView(spinner);
			
			Spinner spinner2=getSpinnerObject();
			spinner2.setAdapter(away_adp);
			
			awayLayout.addView(spinner2);

			temp= new ScorerSpinners(spinner2,"");
			awayScorers.add(temp);
			
		}
		@Override
		public void onValueChange(NumberPicker v, int oldVal, int newVal) {
			// TODO Auto-generated method stub
			if (v==homescore){
				home_prd=Integer.toString(newVal);
				Log.d("MSG", "here"+"old:"+oldVal+" new:"+newVal);
				
				for (int i=oldVal;i<newVal;i++){
					Spinner spinner=getSpinnerObject();
					spinner.setAdapter(home_adp);
					
					homeLayout.addView(spinner);
					
					ScorerSpinners temp= new ScorerSpinners(spinner,"");
					homeScorers.add(temp);
					
				}
				for (int i=newVal;i<oldVal;i++){
					homeLayout.removeView(homeScorers.get(homeScorers.size()-1).team);
					homeScorers.remove(homeScorers.size()-1);
				}
					
				
			}
			else if (v==awayscore){
				away_prd=Integer.toString(newVal);
				for (int i=oldVal;i<newVal;i++){
					Spinner spinner=getSpinnerObject();
					spinner.setAdapter(away_adp);
					spinner.setPrompt("PICK AWAY SCORER");
					
					awayLayout.addView(spinner);
					
					ScorerSpinners temp= new ScorerSpinners(spinner,"0");
					awayScorers.add(temp);
					
				
				}
				for (int i=newVal;i<oldVal;i++){
					awayLayout.removeView(awayScorers.get(awayScorers.size()-1).team);
					
					awayScorers.remove(awayScorers.size()-1);
					
				}
					
			}
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v==save_prediction){
				for (int i=0;i<temp.size();i++){
					if (temp.get(i).match_id== this.match_id){
						temp.get(i).predict=true;
						break;
					}
				}
				String scorers=getScorersFromSpinners();
				PredictionObject object = new PredictionObject(match_id, Integer.toString(homescore.getValue()), Integer.toString(awayscore.getValue()), scorers);
				ArrayList<PredictionObject> predictions;
				if (getArguments().containsKey("predictions")){
				 predictions=(ArrayList<PredictionObject>) getArguments().getSerializable("predictions");
				 for (int i=0;i<predictions.size();i++){
						if (predictions.get(i).match_id==this.match_id){
							predictions.set(i, object);					
						}
					}
					
				}
				else{
					predictions=new ArrayList<PredictionObject>();
					predictions.add(object);
				}
				getArguments().putSerializable("predictions", predictions);;
				getArguments().putSerializable("fixture", temp);;
				
				Fragment upcoming=new UpcomingFixtures();
				upcoming.setArguments(getArguments());
				FragmentManager frm=getFragmentManager();
				frm.beginTransaction().replace(R.id.container, upcoming).addToBackStack(null).commit();
				
				
			}
		}
		
		private String getScorersFromSpinners() {
			// TODO Auto-generated method stub
			String temp="";
			ArrayList<String> x = new ArrayList<String>();
			for (int i=0;i<homeScorers.size();i++){
				if (homeScorers.get(i).scorer!="")
				x.add(homeScorers.get(i).scorer);
			}
			for (int i=0;i<awayScorers.size();i++){
				if(awayScorers.get(i).scorer!="")
				x.add(awayScorers.get(i).scorer);
			}
			if (x.size()>0){
				x = new ArrayList<String>(new LinkedHashSet<String>(x));
				for (int i=0;i<x.size();i++){
					temp=temp+x.get(i)+"|";
				}
			
				return temp.substring(0, temp.length()-1);
			}
			else{
				return "";
			}
		}
		@Override
		public void onItemSelected(AdapterView<?> parent, View v, int pos,
				long id) {
			// TODO Auto-generated method stub
			int no=contains(homeScorers,parent);
			int no2=contains(awayScorers,parent);
			if (no!=-1){
				if(pos!=0){
					scorers_list.add(HomeTeam.get(pos-1).id);
					homeScorers.get(no).scorer=HomeTeam.get(pos-1).id;
				}
				else{
					homeScorers.get(no).scorer="";
				}
			}
			else if (no2!=-1){
				if(pos!=0){
					scorers_list.add(AwayTeam.get(pos-1).id);
					awayScorers.get(no2).scorer=AwayTeam.get(pos-1).id;
					
				}
				else{
					awayScorers.get(no2).scorer="";
				}
			}
		}
		
		private int contains(ArrayList<ScorerSpinners> scorers,
				AdapterView<?> spinner) {
			// TODO Auto-generated method stub
			for (int i=0;i<scorers.size();i++){
				if (scorers.get(i).team == spinner){
					return i;
				}
			}
			return -1;
		}
		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
		public class ScorerSpinners{
			Spinner team;
			String scorer;
			public ScorerSpinners(Spinner spinner, String string) {
				// TODO Auto-generated constructor stub
				team=spinner;
				scorer=string;
			}
			
		}
	}


