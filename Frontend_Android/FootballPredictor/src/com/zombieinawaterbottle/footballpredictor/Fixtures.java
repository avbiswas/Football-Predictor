package com.zombieinawaterbottle.footballpredictor;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fixtures extends Fragment {
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fixtures, container, false);
		
        getActivity().getActionBar().setTitle("FIXTURES");
        
        
        return v;
	}
}
