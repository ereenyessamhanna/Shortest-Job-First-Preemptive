package com.egypt.ereeny_shortest_job_first_preemptive;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomAdapter extends ArrayAdapter<ProcessAdapterModel> {
    Context context;
    ArrayList<ProcessAdapterModel> processAdapterModelArrayList;

    public CustomAdapter(Context context, ArrayList<ProcessAdapterModel> processAdapterModelArrayList) {
        super(context,R.layout.row_listview,processAdapterModelArrayList);
        this.context = context;
        this.processAdapterModelArrayList = processAdapterModelArrayList;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View myConvertView = convertView;

        if(myConvertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            myConvertView = inflater.inflate(R.layout.row_listview,parent,false);
        }


        TextView processName = (TextView) myConvertView.findViewById(R.id.name);
        TextView processStartTime = (TextView) myConvertView.findViewById(R.id.startTime);
        TextView processEndTime = (TextView) myConvertView.findViewById(R.id.endTime);

        processName.setText(processAdapterModelArrayList.get(position).getName());
        processName.setTextColor(Color.BLUE);

        processStartTime.setText("Start: " + processAdapterModelArrayList.get(position).getStartTime());
        processStartTime.setTextColor(Color.GREEN);

        processEndTime.setText("End: " + processAdapterModelArrayList.get(position).getEndTime());
        processEndTime.setTextColor(Color.RED);




        return myConvertView;
    }
}
