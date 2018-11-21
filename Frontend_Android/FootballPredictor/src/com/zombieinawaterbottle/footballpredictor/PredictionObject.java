package com.zombieinawaterbottle.footballpredictor;

import java.util.ArrayList;

public class PredictionObject {
	String match_id, home_score,away_score,scorers;
	String prediction_id;
	public PredictionObject(String id, String s1, String s2, String scorers){
		match_id=id;
		home_score=s1;
		away_score=s2;
		this.scorers=scorers;
		
	}
	
	private String getScorers(ArrayList<String> scorers_list) {
		// TODO Auto-generated method stub
		String temp="";
		if (scorers_list.size()>0){
			for (int i=0;i<scorers_list.size()-1;i++){
				temp=temp+scorers_list.get(i);
				temp+="|";
			}
			return temp.substring(0, -1);
		}
		else{
			return "X";
		}
		
	}
	public String getIPParameter(){
		return ("?match_id="+match_id+"&score1="+home_score+"&score2="+away_score+"&scorers="+scorers);
	}
}
