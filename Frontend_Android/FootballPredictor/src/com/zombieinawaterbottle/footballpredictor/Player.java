package com.zombieinawaterbottle.footballpredictor;

public class Player {
	String name;
	String id;
	String jersey;
	String position;
	Player(String id, String name, String jersey, String position){
		this.id=id;
		this.name=name;
		this.jersey=jersey;
		this.position=position;
	}
	public String getString(){
		return jersey+".\t"+name+"\t" + position;
	}
}
