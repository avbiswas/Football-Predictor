package com.zombieinawaterbottle.footballpredictor;


public class Match {
	String match_id;
	String date;
	String stadium;
	String home_team_id;
	String away_team_id;
	String score;
	boolean predict;
	String matchday;
	String points;
	public PredictionObject prediction;
	
	public Match(String matchday,String id, String points){
		this.matchday=matchday;
		this.points=points;
		this.match_id=id;
		prediction=null;
		predict=false;
	}
	public Match(String id, String home,String away,String matchday,
			String stadium,String date){
		
		match_id=id;
		this.date=date;
		this.stadium=stadium;
		this.home_team_id=home;
		this.away_team_id=away;
		score=" vs ";
		predict=false;
	}
	public Match(String id, String home,String away,String matchday,
			String stadium,String date, String score){
		match_id=id;
		this.date=date;
		this.stadium=stadium;
		this.home_team_id=home;
		this.away_team_id=away;
		this.score=score;
		predict=true;
	}
	public String getHomeTeamName(){
		return Team_List.getName(home_team_id);
	}
	public String getAwayTeamName(){
		return Team_List.getName(away_team_id);
	}
	public int getHomeTeamIcon(){
		return Team_List.getLogo(home_team_id);
	}
	public int getAwayTeamIcon(){
		return Team_List.getLogo(away_team_id);
	}
	public void togglePredictionStatus(){
		predict=!predict;
	}
	public boolean getPredictionStatus(){
		return predict;
	}
}
