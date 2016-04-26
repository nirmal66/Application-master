package com.yourapp.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yourapp.myapplication.R;

/**
 * Created by Nirmal on 27-10-2015.
 */
public class MyBatteryAdapter extends RecyclerView.Adapter<MyBatteryAdapter.CustomViewHolder> {

    private String[] title;
    private String[] detail;


    // Provide a suitable constructor (depends on the kind of dataset)
    public MyBatteryAdapter(String[] title, String[] detail) {
        this.title = title;
        this.detail = detail;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public TextView detail;

        public CustomViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            detail = (TextView) itemView.findViewById(R.id.detail);
        }
    }

    @Override
    public MyBatteryAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyBatteryAdapter.CustomViewHolder holder, int position) {

        holder.title.setText(title[position]);
        holder.detail.setText(detail[position]);
    }

    @Override
    public int getItemCount() {
        return title.length;
    }

}
