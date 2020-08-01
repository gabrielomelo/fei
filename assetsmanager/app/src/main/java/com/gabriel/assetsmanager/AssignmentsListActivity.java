package com.gabriel.assetsmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.gabriel.dao.AssetDAO;
import com.gabriel.dao.AssignmentDAO;
import com.gabriel.dbutils.DBAdapter;
import com.gabriel.entities.Asset;
import com.gabriel.entities.Assignment;

import java.util.ArrayList;
import java.util.List;

public class AssignmentsListActivity extends AppCompatActivity {

    public ListView listView;
    public List<Assignment> assigns;
    public List<Assignment> filteredAssigns;
    private AssignmentDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_assignment);
        this.dao = new AssignmentDAO(new DBAdapter(this));
        this.listView = findViewById(R.id.assignment_list);
        this.filteredAssigns = new ArrayList<>();
        this.loadAssignments();
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
                setFilteredAssets(Integer.parseInt(newText));
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
            case R.id.list_collabs:
                startActivity(new Intent(this, CollabListActivity.class));
                return true;
            case R.id.assign_asset:
                startActivity(new Intent(this, RegisterAssignmentActivity.class));
                return true;
            case R.id.list_assets:
                startActivity(new Intent(this, AssetsListActivity.class));
                return true;
            case R.id.assignment_list:
                startActivity(new Intent(this, AssignmentsListActivity.class));
                return true;
            default:
                return true;
        }
    }

    protected void setFilteredAssets(Integer filter) {
        this.filteredAssigns.clear();
        for(Assignment a : assigns) {
            if(a.getID().equals(filter)) {
                this.filteredAssigns.add(a);
            }
        }
        this.listView.invalidateViews();
    }

    protected void removeAssignment(Assignment assign) {
        AssignmentDAO dao = new AssignmentDAO(new DBAdapter(this));
        dao.delete(assign.getID());
        this.reloadList();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.reloadList();
    }

    public void reloadList() {
        this.assigns = this.dao.getAll();
        this.filteredAssigns.clear();
        this.filteredAssigns.addAll(this.assigns);
        this.listView.invalidateViews();
    }

    private void loadAssignments() {
        this.assigns = this.dao.getAll();
        this.filteredAssigns.addAll(this.assigns);
        this.listView.setAdapter(
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.filteredAssigns)
        );
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                removeAssignment(filteredAssigns.get(position));
            }
        });
    }

}
