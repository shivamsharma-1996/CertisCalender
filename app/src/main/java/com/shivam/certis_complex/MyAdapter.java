package com.shivam.certis_complex;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.HashMap;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private HashMap<Integer, Integer> boxIdMap;
    private int startHour, endHour, startMin, endMin;

    private final static int COLUMN_COUNT = 11;
    private int boxWidth;

    public MyAdapter(Context context, HashMap<Integer, Integer> boxIdMap, int startHour, int endHour, int startMin, int endMin) {
        this.context = context;
        this.boxIdMap = boxIdMap;
        this.startHour = startHour;
        this.endHour = endHour;
        this.startMin = startMin;
        this.endMin = endMin;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch (CertiseItemType.lookupByCode(viewType)) {
            case FIRST_ROW:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calender_row_layout, parent, false);
                return new FirstRowViewHolder(view);

            default:
            case OTHER_ROW:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calender_row_layout, parent, false);
                return new SecondRowViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 1;
        else
            return 2;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        switch (position) {
            case 0:
                FirstRowViewHolder firstRowViewHolder = (FirstRowViewHolder) holder;
                firstRowViewHolder.mOfficerName.setVisibility(View.INVISIBLE);
                firstRowViewHolder.mAttendence.setVisibility(View.INVISIBLE);
                firstRowViewHolder.mOfficerCheckbox.setVisibility(View.INVISIBLE);
                break;
            case 1:
                final SecondRowViewHolder officerAvailableViewHolder = (SecondRowViewHolder) holder;
                final View boxView = officerAvailableViewHolder.itemView.findViewById(boxIdMap.get(endHour));


                ViewTreeObserver vto = boxView.getViewTreeObserver();
                vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        boxView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        boxWidth = boxView.getMeasuredWidth();
                        int marginStart = 0, marginEnd = 0;

                        switch (startMin) {
                            case 15:
                                marginStart = (boxWidth * 1) / 4;
                                break;
                            case 30:
                                marginStart = (boxWidth * 2) / 4;
                                break;
                            case 45:
                                marginStart = (boxWidth * 3) / 4;
                                break;
                            case 00:
                                marginStart = (boxWidth * 4) / 4;
                                break;
                        }
                        switch (endMin) {
                            case 15:
                                marginEnd = boxWidth - (boxWidth * 1) / 4;
                                break;
                            case 30:
                                marginEnd = boxWidth - (boxWidth * 2) / 4;
                                break;
                            case 45:
                                marginEnd = boxWidth - (boxWidth * 3) / 4;
                                break;
                            case 00:
                                marginEnd = boxWidth - (boxWidth * 4) / 4;
                                break;
                        }

                        ConstraintLayout layout = officerAvailableViewHolder.itemView.findViewById(R.id.constraint_layout);
                        ConstraintSet set = new ConstraintSet();
                        View view1 = LayoutInflater.from(context).inflate(R.layout.calender_dynamic_view, null);
                        view1.findViewById(R.id.tvGate).setBackgroundColor(Color.GREEN);
                        view1.setId(R.id.view1);
                        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(0, 0);
                        view1.setLayoutParams(layoutParams);
                        layout.addView(view1);
                        set.clone(layout);

                        if (endHour - 2 < COLUMN_COUNT)
                            if (marginEnd > 0)
                                set.connect(view1.getId(), ConstraintSet.END, boxIdMap.get(endHour + 1), ConstraintSet.START, marginEnd);
                            else
                                set.connect(view1.getId(), ConstraintSet.END, boxIdMap.get(endHour), ConstraintSet.START, marginEnd);
                        else
                            set.connect(view1.getId(), ConstraintSet.END, officerAvailableViewHolder.itemView.getId(), ConstraintSet.END, 0);
                        set.connect(view1.getId(), ConstraintSet.START, boxIdMap.get(startHour), ConstraintSet.START, marginStart);
                        set.connect(view1.getId(), ConstraintSet.BOTTOM, officerAvailableViewHolder.itemView.getId(), ConstraintSet.BOTTOM, 0);
                        set.connect(view1.getId(), ConstraintSet.TOP, officerAvailableViewHolder.itemView.getId(), ConstraintSet.TOP, 5);
                        set.applyTo(layout);

                        //view2
                        View view2 = LayoutInflater.from(context).inflate(R.layout.calender_dynamic_view, null);
                        view2.setId(R.id.view2);
                        view2.findViewById(R.id.tvGate).setBackgroundColor(Color.BLUE);
                        ConstraintLayout.LayoutParams layoutParams1 = new ConstraintLayout.LayoutParams(0, 0);
                        view2.setLayoutParams(layoutParams1);
                        layout.addView(view2);
                        set.clone(layout);


                        if (endHour - 2 < COLUMN_COUNT)
                            if (marginEnd > 0)
                                set.connect(view2.getId(), ConstraintSet.END, boxIdMap.get(endHour + 1), ConstraintSet.START, marginEnd);
                            else
                                set.connect(view2.getId(), ConstraintSet.END, boxIdMap.get(endHour), ConstraintSet.START, marginEnd);
                        else
                            set.connect(view2.getId(), ConstraintSet.END, officerAvailableViewHolder.itemView.getId(), ConstraintSet.END, 0);
                        set.connect(view2.getId(), ConstraintSet.START, boxIdMap.get(startHour), ConstraintSet.START, marginStart);
                        set.connect(view2.getId(), ConstraintSet.BOTTOM, officerAvailableViewHolder.itemView.getId(), ConstraintSet.BOTTOM, 5);
                        set.connect(view2.getId(), ConstraintSet.TOP, view1.getId(), ConstraintSet.BOTTOM, 5);
                        set.applyTo(layout);

                        //view3
                        View view3 = LayoutInflater.from(context).inflate(R.layout.calender_dynamic_view, null);
                        view3.findViewById(R.id.tvGate).setBackgroundColor(Color.YELLOW);
                        view3.setId(R.id.view3);
                        ConstraintLayout.LayoutParams layoutParams2 = new ConstraintLayout.LayoutParams(0, 0);
                        view3.setLayoutParams(layoutParams2);
                        layout.addView(view3);
                        set.clone(layout);

                        if (endHour - 2 < COLUMN_COUNT)
                            if (marginEnd > 0)
                                set.connect(view3.getId(), ConstraintSet.END, boxIdMap.get(endHour + 1), ConstraintSet.START, marginEnd);
                            else
                                set.connect(view3.getId(), ConstraintSet.END, boxIdMap.get(endHour), ConstraintSet.START, marginEnd);
                        else
                            set.connect(view3.getId(), ConstraintSet.END, officerAvailableViewHolder.itemView.getId(), ConstraintSet.END, 0);
                        set.connect(view3.getId(), ConstraintSet.START, boxIdMap.get(startHour), ConstraintSet.START, marginStart);
                        set.connect(view3.getId(), ConstraintSet.BOTTOM, officerAvailableViewHolder.itemView.getId(), ConstraintSet.BOTTOM, 5);
                        set.connect(view3.getId(), ConstraintSet.TOP, view2.getId(), ConstraintSet.BOTTOM, 5);
                        set.applyTo(layout);

                        ConstraintSet constraintSet = new ConstraintSet();
                        constraintSet.clone(layout);                  //layout is the parent constraintlaout
                        constraintSet.connect(view1.getId(),  ConstraintSet.BOTTOM, view2.getId(), ConstraintSet.TOP, 0);
                        //constraintSet.connect(view1.getId(), ConstraintSet.TOP, officerAvailableViewHolder.itemView.getId(), ConstraintSet.TOP, 5);
                        constraintSet.applyTo(layout);

                        constraintSet.clone(layout);                  //layout is the parent constraintlaout
                        constraintSet.connect(view2.getId(),  ConstraintSet.BOTTOM, view3.getId(), ConstraintSet.TOP, 0);
                        //constraintSet.connect(view2.getId(), ConstraintSet.TOP, view1.getId(), ConstraintSet.BOTTOM, 5);
                        constraintSet.applyTo(layout);
                    }
                });
                break;
        }
    }


    @Override
    public int getItemCount() {
        return 10;
    }


    private class FirstRowViewHolder extends RecyclerView.ViewHolder {
        TextView mOfficerName, mAttendence;
        CheckBox mOfficerCheckbox;

        public FirstRowViewHolder(View itemView) {
            super(itemView);
            mOfficerName = itemView.findViewById(R.id.tv_officer_name);
            mAttendence = itemView.findViewById(R.id.tv_available);
            mOfficerCheckbox = itemView.findViewById(R.id.check_officer);
        }
    }

    private static class SecondRowViewHolder extends RecyclerView.ViewHolder {
        CheckBox mOfficerCheckbox;

        public SecondRowViewHolder(View itemView) {
            super(itemView);
            mOfficerCheckbox = itemView.findViewById(R.id.check_officer);
        }
    }
}