package com.zombieinawaterbottle.footballpredictor;

public class Group_Member {
	String user_id;
	String user_name;
	String points;
	String rank;
	String latest_points;
	String matches_predicted;
	Group_Member( String rank, String id, String name, String pts,
			String matches, String latest){
		user_id=id;
		user_name=name;
		points=pts;
		this.rank=rank;
		latest_points=latest;
		matches_predicted=matches;
	}
}
