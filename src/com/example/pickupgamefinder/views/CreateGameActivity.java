package com.example.pickupgamefinder.views;

import java.io.IOException;
import java.util.Calendar;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.pickupgamefinder.R;
import com.example.pickupgamefinder.controllers.Controller;
import com.google.cloud.backend.android.CloudBackendActivity;
import com.google.cloud.backend.android.CloudCallbackHandler;
import com.google.cloud.backend.android.CloudEntity;

/**
 * The activity classthat's responsible for displaying and taking in input about a specific game.
 * @author Tarun Sharma(tsharma2)
 */
public class CreateGameActivity extends CloudBackendActivity implements OnItemSelectedListener {

	public static boolean isTimeSet = false;
	public static boolean isDateSet = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_game);
		// Show the Up button in the action bar.
		setupActionBar();
		createSportsSpinner();
	}

	/**
	 * Creates the spinner that allows a user to choose a sport.
	 */
	private void createSportsSpinner() {
		Spinner spinner = (Spinner) findViewById(R.id.spinner_sports);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, 
				R.array.sports_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);	
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	 /** 
     * Called when the user clicks the 'Submit' button is clicked.
     * @param view  the view on which the 'Submit' button resides.
     **/
	public void submitButtonPressed(View view) {
		Controller.submitButtonHandler(this, view);
	}
    
	/**
	 * Called when an item is selected on the spinner and is part of the OnItemSelectedListerner interface.
	 * This function is a stub.
	 */
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
    	Controller.setSport(parent.getItemAtPosition(pos));
    }

    /**
     * Function that handles the action for when nothing is selected on the spinner. It is part of the 
     * OnItemSelectedListerner interface. This function is a stub.
     */
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    /**
     * A class responsible for creating the time picker.
     * @author Tarun Sharma
     */
    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
	@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);
	
			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(getActivity(), this, hour, minute,
					DateFormat.is24HourFormat(getActivity()));
		}
	

		/**
		 * This function is called when the time is set.
		 * @param view   the current time picker view.
		 * @param hourOfDay  the chosen hour.
		 * @param minute  the chosen minute.
		 */
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			Controller.setTime(hourOfDay, minute);
			isTimeSet = true;
		}
    }

    /**
     * A class responsible for creating the date picker.
     * @author Tarun Sharma
     */
    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			
			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}
			
		/**
		 * This function is called when the time is set.
		 * @param view   the current date picker view.
		 * @param year  the chosen year.
		 * @param month  the chosen month.
		 * @param day  the chosen day.
		 */
		public void onDateSet(DatePicker view, int year, int month, int day) {
			Controller.setDate(year, month, day);
			isDateSet = true;
		}
    }

    /**
     * This function is called when the date picker is displayed.
     * @param view  the current view containing the date picker.
     */
    public void showDatePickerDialog(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     * This function is called when the time picker is displayed.
     * @param view  the current view containing the time picker.
     */
    public void showTimePickerDialog(View view) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    /**
     * Shows a toast message for a short duration.
     * @param text  the text to be displayed in the toast message.
     */
	public void showToast(String text) {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
		
	}


	/**
	 * Sends the data to the cloud datastore.
	 * @param name  the name of the person organising the game.
	 * @param sport  the chosen sport.
	 * @param time  the time of the game.
	 * @param date  the date of the game.
	 * @param info  additional info about the game.
	 * @param venue  the venue of the game.
	 */
	public void sendData(String name, String sport, String time, String date, String info, String venue) {
		CloudEntity newPost = new CloudEntity("Guestbook");
		putDataIntoPost(name, sport, time, date, info, venue, newPost);
		// create a response handler that will receive the result or an error
		CloudCallbackHandler<CloudEntity> handler = new CloudCallbackHandler<CloudEntity>() {
			@Override
			public void onComplete(final CloudEntity result) {

			}

			@Override
			public void onError(final IOException exception) {
				handleEndpointException(exception);
			}
		};

		// execute the insertion with the handler
		getCloudBackend().insert(newPost, handler);
	}

	/**
	 * Adds the data to the post cloud post.
	 * @param name  the name of the person organising the game.
	 * @param sport  the chosen sport.
	 * @param time  the time of the game.
	 * @param date  the date of the game.
	 * @param info  additional info about the game.
	 * @param venue  the venue of the game.
	 * @param newPost  the post to be sent.
	 */
	private void putDataIntoPost(String name, String sport, String time, String date, String info, String venue, 
			CloudEntity newPost) {
		newPost.put("name", name);
		newPost.put("sport", sport);
		newPost.put("time", time);
		newPost.put("date", date);
		newPost.put("info", info);
		newPost.put("venue", venue);
	}

	/**
	 * Displays any exceptions that occur during the post.
	 * @param e  an exception that may occur during the post.
	 */
	private void handleEndpointException(IOException e) {
		Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
	}

}
