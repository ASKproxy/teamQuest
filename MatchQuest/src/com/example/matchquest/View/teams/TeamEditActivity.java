package com.example.matchquest.View.teams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.matchquest.R;
import com.example.matchquest.DataManipulation.Teams.TeamEditDM;
import com.example.matchquest.SQLiteData.TeamQuestSqlite;
import com.example.matchquest.View.CommonActivity;
import com.example.matchquest.View.CommonViewClass;
import com.example.matchquest.common.Details;
import com.example.matchquest.model.Players;
import com.example.matchquest.model.Team;

public class TeamEditActivity  extends CommonActivity{

	String playerId;
	
	Team team;
	
	RecyclerView teamEditRecycleView; 
	
	TeamEditAdapter teamEditAdpter;
	
	TextView teamMemberViewCount;
	
	Button addMemberButton;
	
	AddMemersPopUp addMemberPopUp;
	
	TeamNamePopUp teamNamePopUp;
	
	RelativeLayout editLayout;
	
	List<Players> playerList = new ArrayList<Players>();
	
	TeamEditDM teamEditDetail;
	
	TeamQuestSqlite teamEditSql;
	
	TextView emptyText;
	
	String totalCount;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		
        setContentView(R.layout.team_edit_layout);
        playerId = getIntent().getStringExtra("playerNo");
        team =  (Team) getIntent().getSerializableExtra("Team");
        editLayout = (RelativeLayout) findViewById(R.id.team_edit_layout);
        
        getSupportActionBar().setTitle(team.getTeamName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		
		teamEditSql = new TeamQuestSqlite(getApplicationContext());
//		playerList= teamEditSql.getPlayerSqlite().getAllPlayers(team);
		registernetworkListener(false);
		
		teamEditRecycleView = (RecyclerView) findViewById(R.id.team_edit_recycleview);
		teamEditRecycleView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
		emptyText = (TextView) findViewById(R.id.empty_view);
		 
		teamEditAdpter = new TeamEditAdapter(TeamEditActivity.this, playerList, team, teamEditRecycleView);
		teamEditRecycleView.setAdapter(teamEditAdpter);
		
		teamMemberViewCount = (TextView) findViewById(R.id.team_members_count_view);
		totalCount = getString(R.string.team_member_count);
		teamMemberViewCount.setText("(" + playerList.size() + "/" + totalCount + ")");
		
		addMemberButton = (Button) findViewById(R.id.add_members_edit);
		addMemberButton.setVisibility(View.GONE);
		
		if((team.getCaptain().equals(Details.getPlayer(this).getPlayerId()) || team.getViceCaptain().equals(Details.getPlayer(this).getPlayerId())) &&
				playerList.size() != Integer.parseInt(getApplicationContext().getString(R.string.team_member_count)))
		{
			addMemberButton.setVisibility(View.VISIBLE);
		}
		addMemberPopUp = new AddMemersPopUp();
		teamNamePopUp  = new TeamNamePopUp();
		addMemberButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				editLayout.setAlpha(0.3f);
				addMemberPopUp.showPopup(TeamEditActivity.this,playerList,team);
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		 getMenuInflater().inflate(R.menu.team_edit_menu, menu);
	        return true;
	}
	  @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
	        
	        int id = item.getItemId();
	        if (id == R.id.team_edit_menuitem) {
	        	editLayout.setAlpha(0.3f);
	        	teamNamePopUp.showPopup(TeamEditActivity.this,team);
	            return true;
	        }
	        return super.onOptionsItemSelected(item);
	  }
	 
	  
	  
	public void reGainLayout()
	{
		editLayout.setAlpha(1);
	}
	public void reLoad(List<Players> players)
	{
		playerList.addAll(players);
		CommonViewClass.sortPlayersList(playerList);
		teamEditSql.getPlayerSqlite().insertPlayers(playerList, team,true);
		teamEditAdpter.notifyDataSetChanged();
		teamMemberViewCount.setText("(" + playerList.size() + "/" + totalCount + ")");
		addMemberButton.setVisibility(View.GONE);
		
		if((team.getCaptain().equals(Details.getPlayer(this).getPlayerId()) || team.getViceCaptain().equals(Details.getPlayer(this).getPlayerId())) &&
				playerList.size() != Integer.parseInt(getApplicationContext().getString(R.string.team_member_count)))
		{
			addMemberButton.setVisibility(View.VISIBLE);
		}

	}
	
	public void makeCall(String number)
	{
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:"+number));
		startActivity(callIntent);
	}
	public void makeViceCaptain(Team team,String playerId)
	{
		if(!CommonViewClass.isNetworkAvailable(getApplicationContext()))
		{
			CommonViewClass.showdialog(getApplicationContext(), "Please ,Connect to internet !!");
			return;
		}
		teamEditDetail = new TeamEditDM();
		int result = teamEditDetail.makeViceCaptain(team, playerId);
		if(result == 1)
		{
		teamEditAdpter.getTeam().setViceCaptain(playerId);
		teamEditAdpter.notifyDataSetChanged();
		}
	}
	
	public void makeCaptain(Team team,String playerId)
	{
		if(!CommonViewClass.isNetworkAvailable(getApplicationContext()))
		{
			CommonViewClass.showdialog(getApplicationContext(), "Please ,Connect to internet !!");
			return;
		}
		teamEditDetail = new TeamEditDM();
		int result = teamEditDetail.makeCaptain(team, playerId);
		if(result == 1)
		{
		teamEditAdpter.getTeam().setCaptain(playerId);	
		teamEditAdpter.notifyDataSetChanged();
		}
	}
	
	public void deleteFromTeam(Team team, String playerId)
	{
		if(!CommonViewClass.isNetworkAvailable(getApplicationContext()))
		{
			CommonViewClass.showdialog(getApplicationContext(), "Please ,Connect to internet !!");
			return;
		}
		TeamEditDM teamEdit = new TeamEditDM();
		
		int result = teamEdit.removeFromTeam(team, playerId);
		if(result == 1)
		{
			Iterator<Players> iterator = playerList.iterator();
			while(iterator.hasNext())	
			{
				Players player = iterator.next();
				if(player.getPlayerId().equals(playerId))
				{
					iterator.remove();
					break;
				}
			}
			teamEditAdpter.notifyDataSetChanged();
		
		}
	}
	public void updateTeamName(String name)
	{
		getSupportActionBar().setTitle(name);
	}
	@Override
	protected void loadInBackGround() {
		teamEditDetail = new TeamEditDM();
		playerList.clear();
		playerList.addAll(team.getPlayersList());
			
		teamEditSql.getPlayerSqlite().insertPlayers(playerList, team,true);
		
	}

	@Override
	protected void updateView() {
		if(playerList != null && playerList.size() >0 )
		{
		teamEditAdpter.notifyDataSetChanged();
		teamMemberViewCount.setText("(" + playerList.size() + "/" + totalCount + ")");
		
		}else{
			teamEditRecycleView.setVisibility(View.GONE);
			emptyText.setVisibility(View.VISIBLE);
		}
		addMemberButton.setVisibility(View.GONE);
		
		if((team.getCaptain().equals(Details.getPlayer(this).getPlayerId()) || team.getViceCaptain().equals(Details.getPlayer(this).getPlayerId())) &&
				playerList.size() != Integer.parseInt(getApplicationContext().getString(R.string.team_member_count)))
		{
			addMemberButton.setVisibility(View.VISIBLE);
		}
		
	}
}
