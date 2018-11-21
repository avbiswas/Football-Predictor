package com.zombieinawaterbottle.footballpredictor;

import java.util.ArrayList;

public class User {
	String username;
	String user_id;
	String name;
	String fav_club_id;
	ArrayList<Group> groups;
	String points;
	String rank;
	User(String id, String username, String name, String club){
		user_id=id;
		this.username=username;
		this.name=name;
		fav_club_id=club;
	}
	public User(FriendRequests tempRequest) {
		// TODO Auto-generated constructor stub
		this.user_id=tempRequest.user_id;
		this.username=tempRequest.username;
		this.name=tempRequest.name;
	}
	public int getClubIco(){
		return Team_List.getLogo(fav_club_id);
		
	}
}
