package com.zombieinawaterbottle.footballpredictor;

import java.util.ArrayList;

public class Matchday {
	String number;
	String points;
	String total_matches;
	String predictions;
	String net_score;
	ArrayList<Match> matches;
	Matchday(String no, String tot, String pdtcn, String pts){
		number=no;
		points=pts;
		total_matches=tot;
		predictions=pdtcn;
		matches=new ArrayList<Match>();
	}
	public void setNetScore(String net){
		net_score=net;
	}
}
