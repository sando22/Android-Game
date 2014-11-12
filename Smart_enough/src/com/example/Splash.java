package com.example;

import com.example.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity {


	
	protected void onCreate(Bundle Ole ){

		 super.onCreate(Ole);
		 setContentView(R.layout.endgame);
		 Thread timer = new Thread() {
			 public void run() {
				 try{
					 sleep(5000);
				 } catch(InterruptedException e){
					 e.printStackTrace();
				 }finally {
					 Intent openXmlMainActivity = new Intent("com.example.XmlMainActivity");
					 startActivity(openXmlMainActivity);
				 }
			 }
		 };
		 timer.start();
	}
	
}

