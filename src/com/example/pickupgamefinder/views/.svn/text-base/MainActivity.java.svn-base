package com.example.pickupgamefinder.views;

import com.example.pickupgamefinder.R;
import com.example.pickupgamefinder.R.layout;
import com.example.pickupgamefinder.R.menu;
import com.example.pickupgamefinder.controllers.Controller;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
    
    /** 
     * Called when the user clicks the 'Create a Game' button is clicked.
     * @param view  the view on which the 'Create a Game' button resides.
     **/
    public void createGameButtonClicked(View view) {
        Controller.createGameHandler(this, view); 
    }

}
