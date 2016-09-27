package com.yourapp.fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.yourapp.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class StorageFragment extends Fragment {

    private PieChart mChart;
    private static final long KILOBYTE = 1024;
    private StatFs internalStatFs,externalStatFs;
    private long internalTotal,internalFree,externalTotal,externalFree,total,free,used;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_storage, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mChart = (PieChart) view.findViewById(R.id.chart1);
        mChart.setUsePercentValues(false);
        mChart.setDescription("");
        getStorage();
        mChart.setCenterText("Total GB :"+ total +" GB without OS storage");


        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);


        // entry label styling
        mChart.setEntryLabelColor(Color.WHITE);
        mChart.setEntryLabelTextSize(14f);
        setData();
    }

    private void setData() {

        List<PieEntry> entries = new ArrayList<>();
        getStorage();
        // entries.add(new PieEntry(total, "Total Memory"));
        entries.add(new PieEntry(free, "Free Memory"));
        entries.add(new PieEntry(used, "Used Memory"));

        PieDataSet set = new PieDataSet(entries, "Storage");
        set.setSliceSpace(3f);
        set.setSelectionShift(5f);
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(ColorTemplate.rgb("#27A96C"));
        colors.add(ColorTemplate.rgb("ff0000"));
        set.setColors(colors);
        PieData data = new PieData(set);
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);
        mChart.invalidate(); // refresh
    }

    private void getStorage()
    {
        internalStatFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        externalStatFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            internalTotal = (internalStatFs.getBlockCountLong() * internalStatFs.getBlockSizeLong()) / (KILOBYTE * KILOBYTE);
            internalFree = (internalStatFs.getAvailableBlocksLong() * internalStatFs.getBlockSizeLong()) / (KILOBYTE * KILOBYTE);
            externalTotal = (externalStatFs.getBlockCountLong() * externalStatFs.getBlockSizeLong()) / (KILOBYTE * KILOBYTE);
            externalFree = (externalStatFs.getAvailableBlocksLong() * externalStatFs.getBlockSizeLong()) / (KILOBYTE * KILOBYTE);
        } else {
            internalTotal = ((long) internalStatFs.getBlockCount() * (long) internalStatFs.getBlockSize()) / (KILOBYTE * KILOBYTE);
            internalFree = ((long) internalStatFs.getAvailableBlocks() * (long) internalStatFs.getBlockSize()) / (KILOBYTE * KILOBYTE);
            externalTotal = ((long) externalStatFs.getBlockCount() * (long) externalStatFs.getBlockSize()) / (KILOBYTE * KILOBYTE);
            externalFree = ((long) externalStatFs.getAvailableBlocks() * (long) externalStatFs.getBlockSize()) / (KILOBYTE * KILOBYTE);
        }

        total = (long) ((internalTotal + externalTotal) * 0.001);
        free = (long) ((internalFree + externalFree) * 0.001);
        used = (total - free);
    }
}
