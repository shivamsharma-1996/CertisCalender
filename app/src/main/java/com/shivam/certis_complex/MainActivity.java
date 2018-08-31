package com.shivam.certis_complex;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
{
    RecyclerView calenderView;
    private HashMap<Integer ,Integer> boxIdMap = new HashMap<>();
    private int[] calenderColumns = new int[]{
            R.id.v1,
            R.id.v2,
            R.id.v3,
            R.id.v4,
            R.id.v5,
            R.id.v6,
            R.id.v7,
            R.id.v8,
            R.id.v9,
            R.id.v10,
            R.id.v11};

    private String event = "5:30 - 8:30";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calenderView = findViewById(R.id.calenderView);

        int startHour = Integer.parseInt(event.substring( 0, event.indexOf(":")));
        int endHour = Integer.parseInt(event.substring(event.indexOf("-") + 2, event.lastIndexOf(":")));
        int startMin = Integer.parseInt(event.substring(event.indexOf(":") + 1, event.indexOf("-") - 1));
        int endMin = Integer.parseInt(event.substring(event.lastIndexOf(":")  + 1));

        for(int index  = 0; index < 11; index ++)
        {
            boxIdMap.put(startHour - 2 + index, calenderColumns[index]);
        }

        //for setting scale
        for(int viewId = startHour-2 ; viewId< (startHour-2) + 11 ; viewId++)
        {
            ((TextView)findViewById(boxIdMap.get(viewId))).setText(viewId + "AM");
        }

        calenderView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        calenderView.setAdapter(new MyAdapter(this, boxIdMap, startHour, endHour, startMin, endMin));

    }
}
