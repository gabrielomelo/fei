package com.gabriel.assetsmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import com.gabriel.dao.CollaboratorDAO;
import com.gabriel.dbutils.DBAdapter;
import com.gabriel.entities.Collaborator;

import java.util.ArrayList;
import java.util.List;

public class CollabListActivity extends AppCompatActivity {


    public ListView listView;
    public List<Collaborator> collabs;
    public List<Collaborator> filteredCollabs;
    private CollaboratorDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collab_list);
        this.dao = new CollaboratorDAO(new DBAdapter(this));
        this.listView = findViewById(R.id.collabs_list);
        this.filteredCollabs = new ArrayList<>();
        this.loadCollabs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        SearchView searchView =  (SearchView) menu.findItem(R.id.search_bar).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                setFilteredCollabs(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.reg_asset:
                startActivity(new Intent(this, RegisterAssetActivity.class));
                return true;
            case R.id.reg_collab:
                startActivity(new Intent(this, RegisterCollabActivity.class));
                return true;
            case R.id.list_assets:
                startActivity(new Intent(this, AssetsListActivity.class));
                return true;
            case R.id.list_collabs:
                startActivity(new Intent(this, CollabListActivity.class));
                return true;
            case R.id.assign_asset:
                startActivity(new Intent(this, RegisterAssignmentActivity.class));
                return true;
            case R.id.assignment_list:
                startActivity(new Intent(this, AssignmentsListActivity.class));
                return true;
            default:
                return true;
        }
    }

    protected void setFilteredCollabs(String filter) {
        this.filteredCollabs.clear();
        for(Collaborator c : collabs) {
            if(c.getName().toLowerCase().contains(filter.toLowerCase())) {
                this.filteredCollabs.add(c);
            }
        }
        this.listView.invalidateViews();
    }

    protected void editCollab(Collaborator c) {
        Intent intt = new Intent(this, EditCollaboratorActivity.class);
        intt.putExtra("TARGET_COLLAB", c);
        startActivity(intt);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.collabs = this.dao.getAll();
        this.filteredCollabs.clear();
        this.filteredCollabs.addAll(this.collabs);
        this.listView.invalidateViews();
    }

    private void loadCollabs() {
        this.collabs = this.dao.getAll();
        this.filteredCollabs.addAll(this.collabs);
        this.listView.setAdapter(
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.filteredCollabs)
        );
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editCollab(filteredCollabs.get(position));
            }
        });
    }



}
