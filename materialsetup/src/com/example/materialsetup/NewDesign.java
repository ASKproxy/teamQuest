package com.example.materialsetup;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.materialsetup.FragmentDrawer.ClickListener;
import com.example.materialsetup.FragmentDrawer.RecyclerTouchListener;



public class NewDesign extends ActionBarActivity  implements  android.widget.PopupMenu.OnMenuItemClickListener{


    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    
    private DrawerAdapter drawerAdapter;
    private RecyclerView drawerListView;
    
    private ActionMode mActionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newdrawer);
        setUpDrawer();
        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//true for back arrow "<"
        getSupportActionBar().setHomeButtonEnabled(true);//make icon clickable if true
    }

    private void addDrawerItems() {
        String[] osArray = { "Android", "iOS", "Windows", "OS X", "Linux" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(NewDesign.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);//true to make navigation bar to scrren with icon clcick if false no icon visible "=_"
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        // Sync the toggle state after onRestoreInstanceState has occurred.
//        mDrawerToggle.syncState();
//    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setUpDrawer(){
    	 drawerListView = (RecyclerView) findViewById(R.id.drawerList);
    	
         drawerAdapter = new DrawerAdapter(getApplicationContext(), getData());
         drawerListView.setAdapter(drawerAdapter);
         drawerListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//         drawerListView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), drawerListView, new ClickListener() {
//             @Override
//             public void onClick(View view, int position) {
//            	
//            	 Toast.makeText(getApplicationContext(), view.getId()+"", 1000).show();
//            	 view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//            	
//            	 view.setBackgroundColor(getResources().getColor(R.color.windowBackground));
////            	 PopupMenu popUp = new PopupMenu(getApplicationContext(), view);
////            	 popUp.setOnMenuItemClickListener(NewDesign.this);
////            	 MenuInflater inflator = popUp.getMenuInflater();
////            	 inflator.inflate(R.menu.main, popUp.getMenu());
////            	 popUp.show();
//             }
//  
//             @Override
//             public void onLongClick(View view, int position) {
////             	drawerListener.onDrawerItemSelected(view, position);
//             }
//         }));
    }
    public static List<DrawerTexts> getData() {
        List<DrawerTexts> data = new ArrayList<DrawerTexts>();
 
 
        // preparing navigation drawer items
        for (int i = 0; i < 20; i++) {
        	DrawerTexts navItem = new DrawerTexts("Csk is very Csk is very Csk is very"+ i,"loc : mylapore,adayarmylapore,adayarmylapore,adayar mylapore,adayarmylapore,adayar mylapore,adayar"+ i, "date : 23,sep 15 - 24,sep 15"+i, "max 4 players"+i);
            
            data.add(navItem);
        }
       
        return data;
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            
            default:
                return super.onContextItemSelected(item);
        }
    }

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		View v = item.getActionView();
		if (v instanceof RelativeLayout)
		{
			Toast.makeText(getApplicationContext(), "done", 1000).show();
		}
		else{
			Toast.makeText(getApplicationContext(), "wrong", 1000).show();
		}
		return false;
	}
    
//    this is used to force menu selection on selecting some other view by 
//    mActionMode = startActionMode(mActionModeCallback);
    
//    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
//
//        // Called when the action mode is created; startActionMode() was called
//        @Override
//        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//            // Inflate a menu resource providing context menu items
//            MenuInflater inflater = mode.getMenuInflater();
//            inflater.inflate(R.menu.main, menu);
//            return true;
//        }
//
//        // Called each time the action mode is shown. Always called after onCreateActionMode, but
//        // may be called multiple times if the mode is invalidated.
//        @Override
//        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//            return false; // Return false if nothing is done
//        }
//
//        // Called when the user selects a contextual menu item
//        @Override
//        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.action_settings:
//                   
//                    mode.finish(); // Action picked, so close the CAB
//                    return true;
//                default:
//                    return false;
//            }
//        }
//
//        // Called when the user exits the action mode
//        @Override
//        public void onDestroyActionMode(ActionMode mode) {
////            mActionMode = null;
//        }
//    };
}
