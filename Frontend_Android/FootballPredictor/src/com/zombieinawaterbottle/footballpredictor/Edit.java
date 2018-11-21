package com.zombieinawaterbottle.footballpredictor;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class Edit extends Fragment {
	EditText name;
	EditText mail;
	Spinner club;
	View rootView;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.edit, container, false);
		
        getActivity().getActionBar().setTitle("EDIT PROFILE");
        name=(EditText) rootView.findViewById(R.id.editTextName);
		mail=(EditText) rootView.findViewById(R.id.editTextEmail);
		club=(Spinner) rootView.findViewById(R.id.editTextClub);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
		        R.array.team_list, R.layout.spinner_view);
		adapter.setDropDownViewResource(R.layout.spinner_view);
		club.setAdapter(adapter);
		
        return rootView;
	} 
}
