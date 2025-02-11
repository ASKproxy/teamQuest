package com.example.materialsetup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.materialsetup.MultiSpinner.MultiSpinnerListener;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener,MultiSpinnerListener {
	private Toolbar mToolbar;
	private FragmentDrawer drawerFragment;
	
	MultiAutoCompleteTextView locationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setTitle("no materila used");
        
//        drawerFragment = (FragmentDrawer)
//                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
//        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
//        drawerFragment.setDrawerListener(this);
        
    	final String[] words=new String[] {
			     "menu1", "word2", "word3", "word4", "word5","word6","w0rd8"
			 };
	
		
		final List<String> locationList = new ArrayList<String>();

		    locationTextView = (MultiAutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
		    
		    
			final ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line){
				@Override
				public View getView(int position, View convertView,
						ViewGroup parent) {
		
					return super.getView(position, convertView, parent);
				}
				@Override
				public View getDropDownView(int position, View convertView,
						ViewGroup parent) {
					// TODO Auto-generated method stub
					return super.getDropDownView(2, convertView, parent);
				}
				
			};
			locationTextView.setAdapter(locationAdapter);
			locationList.addAll(Arrays.asList(words));
			locationAdapter.addAll(locationList);
			locationTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer() );
			locationTextView.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {

					String text = locationTextView.getText().toString();
					if(text != null && !text.trim().equals(""))
					{
						locationAdapter.clear();
						locationList.clear();
						locationList.addAll(Arrays.asList(words));
					String[] arr = text.split(",");
					for(int i=0;i< arr.length;i++){
						if(locationList.remove(arr[i].trim())){
							locationAdapter.clear();
						}else{
							for(int j = 0 ; j < locationList.size() ; j ++ )
							{
								if (arr[i].trim().contains(locationList.get(j))) {
									locationList.remove(j);
								}
							}
						}
					}
					locationAdapter.addAll(locationList);
					}
					
				
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					
				}
				
				@Override
				public void afterTextChanged(Editable s) {
					
				}

			
			});
			locationTextView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					int i = 0;
					i++;
		
					
				}
			});
			Button tt = (Button) findViewById(R.id.clicktext);
			tt.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					locationTextView.showDropDown();
					
				}
			});
			MultiSpinner multiSpinner = (MultiSpinner) findViewById(R.id.multi_spinner);
			multiSpinner.setItems(Arrays.asList(words), "you can type", this);
			
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onDrawerItemSelected(View view, int position) {
            displayView(position);
    }
 
    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                title = getString(R.string.title_home);
                break;
            
            default:
                break;
        }
 
//        if (fragment != null) {
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.container_body, fragment);
//            fragmentTransaction.commit();
// 
//            // set the toolbar title
//            getSupportActionBar().setTitle(title);
//            
//        }
        Intent intent = new Intent(MainActivity.this,NewDesign.class);
        startActivity(intent);
    }

	@Override
	public void onItemsSelected(boolean[] selected) {
		// TODO Auto-generated method stub
		
	}
}
