package com.dms.emam.git;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.dms.emam.git.DataBase.DatabaseHandler;
import com.dms.emam.git.Repo.NetworkInterface;
import com.dms.emam.git.Repo.RepoAdaptor;
import com.dms.emam.git.Repo.Repositry;


import java.util.ArrayList;

public class RepositoriesList extends Activity implements SwipeRefreshLayout.OnRefreshListener,NetworkInterface {

    ListView listView;
    int LastAdded=0;
    private SwipeRefreshLayout swipeRefreshLayout;
    RepoAdaptor repoAdaptor;
    DatabaseHandler databaseHandler;
    RepositryConnection connection = new RepositryConnection(this);
    ArrayList<Repositry> allRepositries= new ArrayList<>();
    ArrayList<Repositry> addedRepositries= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories_list);
        databaseHandler= new DatabaseHandler(this);
        listView =(ListView) findViewById(R.id.list);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(this);


        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub

                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0) {

                    // add next 10 items in array list and notify adapter change
                    if (LastAdded>=allRepositries.size())
                        return;
                    for (int i=LastAdded;i<LastAdded+10;i++)
                    {
                        if (i>=allRepositries.size())
                            return;
                        addedRepositries.add(allRepositries.get(i));
                    }
                    LastAdded+=10;
                    repoAdaptor.notifyDataSetChanged();

                }
            }}
            );

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Repositry repositry = (Repositry)parent.getAdapter().getItem(position);
                urlRequest(repositry);
            }
        });

        if (!isNetworkConnected())
        {
            Toast.makeText(this, "No Internet Connection",Toast.LENGTH_SHORT).show();
            ResponseRepositriesArray(databaseHandler.getRepositries());
            return;
        }
        connection.getRepositries(this);
    }


    @Override
    public void ResponseRepositriesArray(ArrayList<Repositry> repositries) {

        this.allRepositries=repositries;
        addedRepositries = new ArrayList<>();
        LastAdded=10;
        if (allRepositries.size()==0)return;
        for (int i=0;i<LastAdded;i++)
        {
            addedRepositries.add(allRepositries.get(i));
        }
        repoAdaptor=new RepoAdaptor(RepositoriesList.this,R.layout.activity_repo_details,this.addedRepositries);
        listView.setAdapter(repoAdaptor);
        repoAdaptor.notifyDataSetChanged();
        databaseHandler.cleanDatabase();
        for (int i=0;i<repositries.size();i++)
        databaseHandler.addrRepositry(repositries.get(i));
    }

    @Override
    public void onRefresh() {
        if (!isNetworkConnected())
        {
            Toast.makeText(this, "No Internet Connection",Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
            return;
        }
        databaseHandler.cleanDatabase();
        swipeRefreshLayout.setRefreshing(true);
        connection.getRepositries(this);
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(getApplicationContext(),"Refreshed",Toast.LENGTH_SHORT).show();

    }

    void urlRequest(final  Repositry repositry)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(RepositoriesList.this);
        builder.setTitle("open Html");

        builder.setPositiveButton("repositry html URL ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(repositry.getRepositoryHtml_url()));
                        startActivity(browserIntent);
                    }
                }
        );
        builder.setNegativeButton("owner html URL",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(repositry.getOwnerHtml_url()));
                        startActivity(browserIntent);
                    }
                }
        );
        builder.show();

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

}
