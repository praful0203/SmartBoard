package com.example.prafulmishra.smartboard_alpha;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by PrafulMishra on 19-03-2018.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {

    String data[];
    public DataAdapter(String data[])
    {
        this.data = data;
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list,parent,false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {
        public DataViewHolder(View itemView) {
            super(itemView);
            TextView lblnoticeid,notice,time;

        }
    }
}
