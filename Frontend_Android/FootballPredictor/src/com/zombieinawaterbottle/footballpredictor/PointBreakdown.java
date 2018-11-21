package com.zombieinawaterbottle.footballpredictor;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class PointBreakdown extends Fragment{
	String match_id,user_id;
	WebView breakdown;
	private ProgressBar loading;
	public PointBreakdown(String match_id, String user_id) {
		// TODO Auto-generated constructor stub
		this.match_id=match_id;
		this.user_id=user_id;
		
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.points_breakdown_match, container, false);
		getActivity().getActionBar().setTitle("POINTS BREAKDOWN");
		String IP=ServerParams.url+ServerParams.getScores+"?match_id="+match_id+"&user_id="+user_id;
        loading=(ProgressBar) v.findViewById(R.id.loading);
        
		breakdown = (WebView) v.findViewById(R.id.webview);
		breakdown.setVisibility(View.INVISIBLE);
		breakdown.loadUrl(IP);
		breakdown.setWebViewClient(new WebViewClient() {

			   public void onPageFinished(WebView view, String url) {
			        loading.setVisibility(View.GONE);
			        breakdown.setVisibility(View.VISIBLE);
			    }
			});
		return v;
	}
}
