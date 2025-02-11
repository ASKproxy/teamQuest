package com.example.cardview;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

public class MainActivity extends ActionBarActivity {
	 private Toolbar toolbar;

	 private RecyclerView mRecyclerView;
	 private RecyclerView.Adapter mAdapter;
	 private RecyclerView.LayoutManager mLayoutManager;

	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_main);
	 

	  String[] myDataset = { "Alpha", "Beta", "CupCake", "Donut", "Eclair",
	    "Froyo", "Gingerbread", "Honeycomb", "Ice Cream Sandwitch",
	    "JellyBean", "KitKat", "LollyPop" };

	  mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

	  // getSupportActionBar().setIcon(R.drawable.ic_launcher);

	  // getSupportActionBar().setTitle("Android Versions");

	  // use this setting to improve performance if you know that changes
	  // in content do not change the layout size of the RecyclerView
	  mRecyclerView.setHasFixedSize(true);

	  // use a linear layout manager
	  mLayoutManager = new LinearLayoutManager(this);
	  mRecyclerView.setLayoutManager(mLayoutManager);
	  
	   // specify an adapter (see also next example)
	  mAdapter = new CardViewDataAdapter(myDataset);
	  mRecyclerView.setAdapter(mAdapter);
	  
	 }

	 @Override
	 public boolean onCreateOptionsMenu(Menu menu) {
	  // Inflate the menu; this adds items to the action bar if it is present.
	  getMenuInflater().inflate(R.menu.main, menu);
	  return true;
	 }

	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
	  // Handle action bar item clicks here. The action bar will
	  // automatically handle clicks on the Home/Up button, so long
	  // as you specify a parent activity in AndroidManifest.xml.
	  int id = item.getItemId();
	  if (id == R.id.action_settings) {
	   Toast.makeText(getApplicationContext(), "Settings Clicked",
	     Toast.LENGTH_SHORT).show();
	   return true;
	  } 
	  return super.onOptionsItemSelected(item);
	 }
}
