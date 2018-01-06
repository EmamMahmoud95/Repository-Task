package com.dms.emam.git;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.dms.emam.git.Repo.RepoAdaptor;
import com.dms.emam.git.Repo.Repositry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by emam_ on 1/4/2018.
 */

public class RepositryConnection {



    RequestQueue queue ;
    Context context;
    ProgressDialog progressDialog;

    RepositryConnection(Context context)
    {
        this.context=context;
    }



    public void  getRepositries (final RepositoriesList repositoriesList)
    {
        queue = Volley.newRequestQueue(context);

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        Response.Listener resp =  new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Repositry> repositries =paraseJson(response);
                repositoriesList.ResponseRepositriesArray(repositries);
            }
        };
        Response.ErrorListener err = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context,"connection error",Toast.LENGTH_LONG).show();
            }
        };

        JsonArrayRequest jr = new JsonArrayRequest(Request.Method.GET, "https://api.github.com/users/square/repos", null,resp,err);
        RequestQueue.RequestFinishedListener f = new RequestQueue.RequestFinishedListener() {
            @Override
            public void onRequestFinished(Request request) {
                progressDialog.hide();
            }
        };
        queue.addRequestFinishedListener(f);
        queue.add(jr);

    }




    public ArrayList<Repositry> paraseJson(JSONArray arr)
    {
        ArrayList<Repositry> repositries= new ArrayList<>();

        for (int i = 0; i < arr.length(); i++) {
            Repositry repositry= new Repositry();
            try {
                JSONObject repo= arr.getJSONObject(i);
                repositry.setRepoName(repo.getString("name"));
                repositry.setDescription(repo.getString("description"));
                repositry.setFork(repo.getBoolean("fork"));
                repositry.setRepositoryHtml_url(repo.getString("html_url"));

                repo=repo.getJSONObject("owner");

                repositry.setOwnerHtml_url(repo.getString("html_url"));
                repositry.setUsernameOfOwner(repo.getString("login"));
                repositries.add(repositry);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Log.i("lolo",repositries.get(0).getRepoName());
        return  repositries;
    }

}
