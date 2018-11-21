package com.zombieinawaterbottle.footballpredictor;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class Stat extends Fragment {
	String url,data;
	WebView breakdown;
	private ProgressBar loading;
	public Stat(String url, String data) {
		// TODO Auto-generated constructor stub
		this.url=url;
		this.data=data;
		
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.points_breakdown_match, container, false);
		getActivity().getActionBar().setTitle("LEADERBOARD");
		String IP=ServerParams.url+this.url+this.data;
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
