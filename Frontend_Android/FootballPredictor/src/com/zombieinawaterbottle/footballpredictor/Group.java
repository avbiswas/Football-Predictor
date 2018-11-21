package com.zombieinawaterbottle.footballpredictor;

public class Group {
	public String group_id;
	public String strength;
	public String group_name;
	public String user_rank;
	public String no_of_predictions;
	public String user_points;
	public String max_points;
	public String leader;
	public String avg_points;
	public String matchday;
	public Group(String id, String name, String matchday){
		this.group_id=id;
		this.group_name=name;
		this.matchday=matchday;
	}
	public void setUser_Pdctns(String pdts){
		no_of_predictions=pdts;
	}
	public void setUser_points(String pts){
		user_points=pts;
	}
	public void setAvg(String avg){
		avg_points=avg;
	}
	public void setMax(String max){
		max_points=max;
	}
	public void setLeader(String leader){
		this.leader=leader;
	}
	public void setUser_Rank(String rank) {
		// TODO Auto-generated method stub
		user_rank=rank;

	}
	public void setStrength(String strength) {
		// TODO Auto-generated method stub
		this.strength=strength;
	}
	
}
