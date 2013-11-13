package com.example.pickupgamefinder.controllers;

import android.widget.EditText;

import com.example.pickupgamefinder.R;
import com.example.pickupgamefinder.views.CreateGameActivity;

/**
 * This class checks if the user enters a valid input.
 * @author Tarun Sharma.
 */
public class ValidateInput {
    /**
     * Checks if a valid name is entered.
     * @param createGameActivity
     * @return  false if the name is blank, true otherwise.
     */
    public static boolean isNameEntered(CreateGameActivity createGameActivity) {
    	EditText editTextName = (EditText)createGameActivity.findViewById(R.id.edit_text_name);
    	String name = editTextName.getText().toString(); 
    	if (name.equals("")) {
    		editTextName.setError("Name not entered.");
    		return false;
    	}
    	else {
    		return true;
    	}
    }

    /**
     * Checks if the time is set.
     * @param createGameActivity  the current activity.
     * @return  true if the time is set, false otherwise.
     */
	public static boolean isTimeSet(CreateGameActivity createGameActivity) {
		if (CreateGameActivity.isTimeSet) {
			return true;
		}
		else {
			createGameActivity.showToast("Time of game not entered.");
			return false;
		}
	}

	/**
	 * Checks if the date is set.
     * @param createGameActivity  the current activity.
     * @return  true if the date is set, false otherwise.
	 */
	public static boolean isDateSet(CreateGameActivity createGameActivity) {
		if (CreateGameActivity.isDateSet) {
			return true;
		}
		else {
			createGameActivity.showToast("Date of game not entered.");
			return false;
		}
	}

	/**
	 * Checks if the venue is set.
     * @param createGameActivity  the current activity.
     * @return  true if the venue is set, false otherwise.
	 */
	public static boolean isVenueSet(CreateGameActivity createGameActivity) {
    	EditText editTextVenue = (EditText)createGameActivity.findViewById(R.id.edit_text_venue);
    	String venue = editTextVenue.getText().toString(); 
    	if (venue.equals("")) {
    		editTextVenue.setError("Venue not entered.");
    		return false;
    	}
    	else {
    		return true;
    	}
	}

}
