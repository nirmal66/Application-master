/*
* Copyright (C) 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.yourapp.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yourapp.businesslogic.CustomNetworkUsage;
import com.yourapp.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Provide views to RecyclerView with the directory entries.
 */
public class NetworkUsageAdapter extends RecyclerView.Adapter<NetworkUsageAdapter.ViewHolder> {

    private List<CustomNetworkUsage> mcustomNetworkUsageList = new ArrayList<>();

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mPackageName;
        private final TextView mLastTimeUsed;
        private final ImageView mAppIcon;
        private final ProgressBar mNetworkUsage;

        public ViewHolder(View v) {
            super(v);
            mPackageName = (TextView) v.findViewById(R.id.title);
            mLastTimeUsed = (TextView) v.findViewById(R.id.package_name);
            mAppIcon = (ImageView) v.findViewById(R.id.iv_icon);
            mNetworkUsage = (ProgressBar) v.findViewById(R.id.network_progress);
        }

        public TextView getLastTimeUsed() {
            return mLastTimeUsed;
        }

        public TextView getPackageName() {
            return mPackageName;
        }

        public ImageView getAppIcon() {
            return mAppIcon;
        }

        public ProgressBar getNetworkUsage() { return mNetworkUsage;
        }
    }

    public NetworkUsageAdapter() {
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_usage_network, viewGroup, false);
        return new ViewHolder(v);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            viewHolder.getPackageName().setText(
                    mcustomNetworkUsageList.get(position).title);
            viewHolder.getLastTimeUsed().setText(mcustomNetworkUsageList.get(position).internetStatus);
            viewHolder.getAppIcon().setImageDrawable(mcustomNetworkUsageList.get(position).appIcon);
            viewHolder.getNetworkUsage().setMax(500);
            viewHolder.getNetworkUsage().setProgress(mcustomNetworkUsageList.get(position).networkusage);
    }

    @Override
    public int getItemCount() {
        return mcustomNetworkUsageList.size();
    }

    public void setCustomUsageStatsList(List<CustomNetworkUsage> customNetworkUsageList) {
        mcustomNetworkUsageList = customNetworkUsageList;
    }
}