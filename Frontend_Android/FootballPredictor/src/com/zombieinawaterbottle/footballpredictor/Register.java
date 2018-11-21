package com.zombieinawaterbottle.footballpredictor;

import android.app.Activity;
import android.app.Fragment;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.zombieinawaterbottle.footballpredictor.ServerParams;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends Activity implements OnClickListener, android.view.View.OnClickListener, OnItemSelectedListener{
	EditText name; 
	EditText mail; 
	EditText password; 
	Spinner club; 
	boolean indian; 
	EditText username; 
	TextView status;
	Button submit;
	int fav_club_id;
	String fav_club;
	
	@Override
	    public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
			Settings.System.putInt( this.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0);
			fav_club_id=1;
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
			setContentView(R.layout.layout_register);
			name=(EditText) findViewById(R.id.editTextName);
			mail=(EditText) findViewById(R.id.editTextEmail);
			club=(Spinner) findViewById(R.id.editTextClub);
			ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
			R.array.team_list, R.layout.spinner_view);
			adapter.setDropDownViewResource(R.layout.spinner_view);
			club.setAdapter(adapter);
			club.setOnItemSelectedListener(this);
			password=(EditText) findViewById(R.id.editTextPass);
			username=(EditText) findViewById(R.id.editTextUserName);
			status=(TextView) findViewById(R.id.statusbar);
			submit=(Button) findViewById(R.id.registerButton);
			submit.setOnClickListener(this);
			String fav_club = club.getSelectedItem().toString();
			status.setText(fav_club);

	}
	public void onRadioButtonClicked(View view) {
	    // Is the button now checked?
	    boolean checked = ((RadioButton) view).isChecked();
	    
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.radio_indian:
	            if (checked)
	            	indian=true;
	            	status.setText("Indian");
	            	break;
	        case R.id.radio_not_indian:
	            if (checked)
	                indian=false;

            	status.setText("Not Indian");
	            break;
	    }
	   
	}
	@Override
	
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(!(name.getText().toString().matches("") || mail.getText().toString().matches("") || password.getText().toString().matches("") || username.getText().toString().matches(""))){
			String sendData="name="+name.getText().toString()+"&mail="+mail.getText().toString()+"&username="+username.getText().toString()
					+"&password="+password.getText().toString()+"&indian="+(indian==true?"1":"0")+"&fav_club="+fav_club_id;
			 Log.d("IP ADDRESS:",sendData);
			(new RegisterAttempt(this,sendData)).execute();
		}
		else{
			status.setText("Please fill up all the fields");
		}
	}
	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int pos,
			long id) {
		// TODO Auto-generated method stub
		fav_club=parent.getItemAtPosition(pos).toString();
		status.setText(fav_club);
		fav_club_id=pos;
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		status.setText("Select Club");
	}
	
	public class RegisterAttempt extends AsyncTask<Void, Void, String>{
		private Register signup;
		private String IP;
		RegisterAttempt(Register signup,String data){
			this.signup=signup;
			IP=ServerParams.url+ServerParams.regn+"?"+data;
			
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			signup.handleResponse(result);
			
			
			}
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String response="0";
			try {
				HttpURLConnection connection=(HttpURLConnection) (new URL(IP)).openConnection();
				connection.setDoOutput(true);
				connection.setRequestProperty("Connection", "close");
				BufferedReader br=new BufferedReader(new InputStreamReader(new BufferedInputStream(connection.getInputStream())));
				response=br.readLine();
			    
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
			return response;
		}
		
	}
	public void handleResponse(String result) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
		if (!(result.equalsIgnoreCase("Username already taken"))){
				Intent intent=new Intent(this,Login.class);
				startActivity(intent);
		}
	}
	
	
}
