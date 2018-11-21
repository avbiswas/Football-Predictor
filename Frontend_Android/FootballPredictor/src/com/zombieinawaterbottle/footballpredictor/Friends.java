package com.zombieinawaterbottle.footballpredictor;

public class Friends {
	String id;
	String name;
	String username;
	public Friends(String name, String username, String id){
		this.id=id;
		this.username=username;
		this.name=name;
	}
	public User getUser(){
		return (new User(id,name,username,"0"));
	}
}
