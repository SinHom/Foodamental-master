package com.foodamental;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	DBAdapter myDb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_db);

		openDB();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();	
		closeDB();
	}


	private void openDB() {
		myDb = new DBAdapter(this);
		myDb.open();
	}
	private void closeDB() {
		myDb.close();
	}

	
	
	private void displayText(String message) {
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(message);
	}
	
	

	public void onClick_AddRecord(View v) {
		displayText("Clicked add record!");
		
		long newId = myDb.insertRow(10001, "Coca", 1);
		
		// Query for the record we just added.
		// Use the ID:
		Cursor cursor = myDb.getRow(newId);
		displayRecordSet(cursor);
	}

	public void onClick_ClearAll(View v) {
		displayText("Clicked clear all!");
		myDb.deleteAll();
	}

	public void onClick_DisplayRecords(View v) {
		displayText("Clicked display record!");
		
		Cursor cursor = myDb.getAllRows();
		displayRecordSet(cursor);
	}
	
	// Display an entire recordset to the screen.
	private void displayRecordSet(Cursor cursor) {
		String message = "";
		// populate the message from the cursor
		
		// Reset cursor to start, checking to see if there's data:
		if (cursor.moveToFirst()) {
			do {
				// Process the data:
				int id = cursor.getInt(DBAdapter.COL_ROWID);
				String name = cursor.getString(DBAdapter.COL_NAME);
				int studentNumber = cursor.getInt(DBAdapter.COL_NAME);
				String favColour = cursor.getString(DBAdapter.COL_QUANTITY);
				
				// Append data to the message:
				message += "id=" + id
						   +", name=" + name
						   +", #=" + studentNumber
						   +", Colour=" + favColour
						   +"\n";
			} while(cursor.moveToNext());
		}
		
		// Close the cursor to avoid a resource leak.
		cursor.close();
		
		displayText(message);
	}
}







