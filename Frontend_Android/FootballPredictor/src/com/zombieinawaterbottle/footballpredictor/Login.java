package com.zombieinawaterbottle.footballpredictor;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;











import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Login  extends Activity implements OnClickListener {
	Button signIn;
	EditText ID;
	EditText password;
	Button register;
	Intent login_intent;
	CheckBox remember;
	boolean checked;
	SharedPreferences sharedpreferences;
	 public static final String MyPREFERENCES = "LoginPrefs";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);	
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Settings.System.putInt( this.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_login);
		remember=(CheckBox) findViewById(R.id.checkbox_remember);
		
		signIn=new Button(this);
		signIn=(Button)findViewById(R.id.button1);
		signIn.setOnClickListener(this);
		
		register=new Button(this);
		register=(Button)findViewById(R.id.button4);
		register.setOnClickListener(this);
		
		ID=new EditText(this);
		password=new EditText(this);
		ID=(EditText)findViewById(R.id.editText1);
		password=(EditText)findViewById(R.id.editText2);
		if (sharedpreferences.contains("id") && sharedpreferences.contains("password")){
			ID.setText(sharedpreferences.getString("id", null));
			password.setText(sharedpreferences.getString("password", null));
			
		}
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v==signIn){
			String data="?id="+ID.getText().toString()+"&password="+password.getText().toString();
			(new CheckLoginAttempt(this,data)).execute();
			
			
			
		}
		else if (v==register){ 
			Intent intent=new Intent(this,Register.class);
			startActivity(intent);
			
		}
		
		
	}
	public void handleResponse(String response){
		if(response.equalsIgnoreCase("Invalid Login")){

			Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
			ID.setText("");
			password.setText("");
		}
		else{
			login_intent=new Intent(this,HomeScreen.class);
			login_intent.putExtra("user", response);
			if (checked){
				rememberData();
			}
			(new getFixtures(this,"?matchday=0")).execute();
		}
				

	}
	public class CheckLoginAttempt extends AsyncTask<Void, Void, String>{
		private Login login;
		private String IP;
		CheckLoginAttempt(Login login, String data){
			this.login=login;
			IP=ServerParams.url+ServerParams.login+data;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result==null){
				Toast.makeText(getApplicationContext(), "Can't connect to server", Toast.LENGTH_SHORT).show();
				
			}
			//Log.d("MSG",result);
			login.handleResponse(result);
			
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
	public class getFixtures extends AsyncTask<Void, Void, String>{
		private Login profile;
		private String IP;
		getFixtures(Login profile, String data){
			this.profile=profile;
			IP=ServerParams.url+ServerParams.fixtures+data;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result==null){
				Toast.makeText(getApplicationContext(), "Can't connect to server", Toast.LENGTH_SHORT).show();
			}
			//Log.d("MSG",result);
			profile.handleFixtureResponse(result);
			
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
	public void handleFixtureResponse(String result) {
		// TODO Auto-generated method stub
		
		login_intent.putExtra("fixture",result);
		
		startActivity(login_intent);
	}
	public void onCheckboxClicked(View v) {
	    // Is the view now checked?
	    checked = ((CheckBox) remember).isChecked();

	    // Check which checkbox was clicked
	            
	}
	public void rememberData(){
		
		SharedPreferences.Editor editor = sharedpreferences.edit();
        
        editor.putString("id", ID.getText().toString());
        editor.putString("password", password.getText().toString());
        
        editor.commit();
	}

}
