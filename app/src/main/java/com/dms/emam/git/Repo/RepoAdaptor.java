package com.dms.emam.git.Repo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dms.emam.git.R;

import java.util.ArrayList;

/**
 * Created by emam_ on 1/4/2018.
 */

public class RepoAdaptor extends ArrayAdapter<Repositry> {

    Activity context;
    int layourResource;
    ArrayList<Repositry> mdata = new ArrayList<>();
    public RepoAdaptor(Activity act, int resource, ArrayList<Repositry> data) {
        super(act, resource, data);
        context = act;
        layourResource = resource;
        mdata = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mdata.size();
    }

    @Override
    public int getPosition(Repositry item) {
        return super.getPosition(item);
    }

    @Override
    public Repositry getItem(int position) {
        return mdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        viewHolder holder = null;
        if (row == null || (row.getTag() == null)) {
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(layourResource, null);
            holder = new viewHolder();

            holder.repoName = (TextView) row.findViewById(R.id.name);
            holder.descritopn= (TextView) row.findViewById(R.id.description);
            holder.owner= (TextView) row.findViewById(R.id.owner);
            holder.detailsView= (RelativeLayout)row.findViewById(R.id.detailsView);
            row.setTag(holder);
        } else {
            holder = (viewHolder) row.getTag();
        }
        holder.repo = getItem(position);
        holder.repoName.setText(holder.repo.repoName);
        holder.descritopn.setText(holder.repo.description);
        holder.owner.setText(holder.repo.usernameOfOwner);
        if (holder.repo.fork== false)
        {
            holder.repoName.setTextColor(Color.WHITE);
            holder.descritopn.setTextColor(Color.WHITE);
            holder.owner.setTextColor(Color.WHITE);
            GradientDrawable bgShape = (GradientDrawable)holder.detailsView.getBackground();
            bgShape.setColor(Color.parseColor("#00B017"));
         //   holder.detailsView.setBackgroundColor(Color.parseColor("#00B017"));
        }
        else
        {
            GradientDrawable bgShape = (GradientDrawable)holder.detailsView.getBackground();
            bgShape.setColor(Color.WHITE);
//            holder.detailsView.setBackgroundColor(Color.WHITE);
            holder.repoName.setTextColor(Color.BLACK);
            holder.descritopn.setTextColor(Color.BLACK);
            holder.owner.setTextColor(Color.BLACK);
        }



        return row;
    }


class viewHolder {
    Repositry repo;
    TextView repoName;
    TextView descritopn;
    TextView owner;
    RelativeLayout detailsView;


}


}
