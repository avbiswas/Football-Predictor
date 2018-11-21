package com.zombieinawaterbottle.footballpredictor;

import java.util.ArrayList;

import android.util.Log;

public class Team_List {
	public static ArrayList<Team> teams;
	public static void fill_Team_List(){
	teams=new ArrayList<Team>();
	
	teams.add(new Team("Arsenal","Emirates Stadium - London",R.drawable.ars));
	teams.add(new Team("Bournemouth","Dean Court - Bournemouth",R.drawable.bou));
	teams.add(new Team("Burnley","Turf Moor - Burnley",R.drawable.bur));
	teams.add(new Team("Chelsea","Stamford Bridge - London",R.drawable.che));
	teams.add(new Team("Crystal Palace","Selhurst Park - London",R.drawable.cry));
	teams.add(new Team("Everton","Goodison Park",R.drawable.eve));
	teams.add(new Team("Hull City","KC Stadium - Hull",R.drawable.hul));
	teams.add(new Team("Leicester City","King Power Stadium - Leicester",R.drawable.lei));
	teams.add(new Team("Liverpool","Anfield - Liverpool",R.drawable.liv));
	teams.add(new Team("Manchester City","Etihad Stadium - Manchester",R.drawable.mci));
	teams.add(new Team("Manchester United","Old Trafford - Manchester",R.drawable.man));
	teams.add(new Team("Middlesbrough","Riverside Stadium - Middlesbrough",R.drawable.mid));
	teams.add(new Team("Southampton","St Mary's Stadium - Southampton",R.drawable.sou));
	teams.add(new Team("Stoke City","Bet365 Stadium - Stoke-on-Trent",R.drawable.sto));
	teams.add(new Team("Sunderland","Stadium of Light - Sunderland",R.drawable.sun));
	teams.add(new Team("Swansea City","Liberty Stadium - Swansea",R.drawable.swa));
	teams.add(new Team("Tottenham Hotspur","White Hart Lane - London",R.drawable.tot));
	teams.add(new Team("Watford","Vicarage Road - Watford",R.drawable.wat));
	teams.add(new Team("West Bromwich Albion","The Hawthorns - West Bromwich",R.drawable.wba));
	teams.add(new Team("West Ham United","Olympic Stadium - London",R.drawable.whu));
	}
public static String getName(String pos)
{
	int position= Integer.parseInt(pos);
	return teams.get(position).team_name;
	
}
public static String getStadium(String pos)
{
	int position= Integer.parseInt(pos);
	return teams.get(position).team_stadium;
	
}
public  static int getLogo(String pos)
{
	int position= Integer.parseInt(pos);
	return teams.get(position).team_icon;
	
}

public static void show(){
	for (int i=0;i<teams.size();i++){
		Team x= teams.get(i);
		Log.d("MSG", i+" "+x.team_name+" "+x.team_icon+ " ");
	}
}

}
