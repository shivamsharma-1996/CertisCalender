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
    RecyclerView rvMain;
    private HashMap<Integer ,Integer> boxIdMap = new HashMap<>();
    private String event = "5:00 - 10:00";
    // 2:00 - 10:00  // scale :  from 0AM to 10 AM
    // 1:00 - 14:00  // scale :  from -1AM to 9 AM
    // 10:00 - 14:00 possible?

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvMain = findViewById(R.id.rvMain);

        boxIdMap.put(1,R.id.v1);
        boxIdMap.put(2,R.id.v2);
        boxIdMap.put(3,R.id.v3);
        boxIdMap.put(4,R.id.v4);
        boxIdMap.put(5,R.id.v5);
        boxIdMap.put(6,R.id.v6);
        boxIdMap.put(7,R.id.v7);
        boxIdMap.put(8,R.id.v8);
        boxIdMap.put(9,R.id.v9);
        boxIdMap.put(10,R.id.v10);
        boxIdMap.put(11,R.id.v11);

        int startHour = Integer.parseInt(event.substring( 0, event.indexOf(":")));
        int endHour = Integer.parseInt(event.substring(event.indexOf("-") + 2, event.lastIndexOf(":")));
        int startMin = Integer.parseInt(event.substring(event.indexOf(":") + 1, event.indexOf("-") - 1));
        int endMin = Integer.parseInt(event.substring(event.lastIndexOf(":")  + 1));


        for(int viewId = 1, startTime = startHour  ; viewId<12 ; startTime++, viewId++)
        {
            ((TextView)findViewById(boxIdMap.get(viewId))).setText(startTime - 2 + "AM");
        }

        rvMain.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        rvMain.setAdapter(new MyAdapter(this, boxIdMap, startHour, endHour, startMin, endMin));
    }
}
