package com.example.mareu.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.Adapters.MeetingAdapter;
import com.example.mareu.Models.Meeting;
import com.example.mareu.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.mareu.Activities.MainActivity.mMeetingService;

public class MeetingFragment extends Fragment
{
    List<Meeting> meetings = new ArrayList<>();
    MeetingAdapter mMeetingAdapter;
    RecyclerView mRecyclerView;

    public MeetingFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        meetings = mMeetingService.getMeetings();

        if (!meetings.isEmpty()) {
            View view = inflater.inflate(R.layout.list_meeting, container, false);

            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            mRecyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

            mMeetingAdapter = new MeetingAdapter(meetings, this.getContext());
            mRecyclerView.setAdapter(mMeetingAdapter);

            return view;
        }
        else{
            View emptyView = inflater.inflate(R.layout.empty_recyclerview,container,false);
            return emptyView;
        }

    }


}
