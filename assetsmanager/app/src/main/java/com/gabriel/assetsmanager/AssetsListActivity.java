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

import com.gabriel.dao.AssetDAO;
import com.gabriel.dbutils.DBAdapter;
import com.gabriel.entities.Asset;

import java.util.ArrayList;
import java.util.List;

public class AssetsListActivity extends AppCompatActivity {

    public ListView listView;
    public List<Asset> assets;
    public List<Asset> filteredAssets;
    private AssetDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        this.dao = new AssetDAO(new DBAdapter(this));
        this.listView = findViewById(R.id.assets_list);
        this.filteredAssets = new ArrayList<>();
        this.loadAssets();
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
                setFilteredAssets(newText);
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
            case R.id.list_assets:
                startActivity(new Intent(this, AssetsListActivity.class));
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

    protected void setFilteredAssets(String filter) {
        this.filteredAssets.clear();
        for(Asset a : assets) {
            if(a.getSerialNumber().toLowerCase().contains(filter.toLowerCase())) {
                this.filteredAssets.add(a);
            }
        }
        this.listView.invalidateViews();
    }

    protected void editAssets(Asset asset) {
        Intent intt = new Intent(this, EditAssetActivity.class);
        intt.putExtra("TARGET_ASSET", asset);
        startActivity(intt);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.assets = this.dao.getAll();
        this.filteredAssets.clear();
        this.filteredAssets.addAll(this.assets);
        this.listView.invalidateViews();
    }

    private void loadAssets() {
        this.assets = this.dao.getAll();
        this.filteredAssets.addAll(this.assets);
        this.listView.setAdapter(
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.filteredAssets)
        );
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editAssets(filteredAssets.get(position));
            }
        });
    }



}
