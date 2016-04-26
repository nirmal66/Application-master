package com.yourapp.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yourapp.myapplication.R;

/**
 * Created by Nirmal on 27-10-2015.
 */
public class MySlidingAdapter extends RecyclerView.Adapter<MySlidingAdapter.CustomViewHolder> {

    private String[] title;
    private OnSlidingClickInterface mSlidingClickInterface;
    private Context context;


    // Provide a suitable constructor (depends on the kind of dataset)
    public MySlidingAdapter(String[] title, OnSlidingClickInterface SlidingClickInterface, Context context) {
        this.mSlidingClickInterface = SlidingClickInterface;
        this.title = title;
        this.context = context;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public RelativeLayout mainMenu;
        public ImageView icon;

        public CustomViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            mainMenu = (RelativeLayout) itemView.findViewById(R.id.main_menu);
            icon = (ImageView) itemView.findViewById(R.id.iv_icon);
        }
    }

    @Override
    public MySlidingAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_sliding, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MySlidingAdapter.CustomViewHolder holder, final int position) {

        holder.title.setText(title[position]);
        if (title[position].toString().equals("Backup and Restore")) {
            holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.data_backup));
        } else if (title[position].toString().equals("Applications")) {
            holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.application));
        } else if (title[position].toString().equals("Running Apps")) {
            holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ruuningapps));
        } else if (title[position].toString().equals("Move to SD Card")) {
            holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.movetosd));
        }
        else if (title[position].toString().equals("Apps Usage")) {
            holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.app_usage));
        }
        else if (title[position].toString().equals("Data Usage")) {
            holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.data_usage));
        }
        holder.mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlidingClickInterface.onSlidingItemClick(title[position]);
            }
        });
    }

    @Override
    public int getItemCount() {
        return title.length;
    }

    public interface OnSlidingClickInterface {
        void onSlidingItemClick(String menuName);
    }
}
