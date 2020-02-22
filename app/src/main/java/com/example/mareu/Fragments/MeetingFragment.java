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
import com.example.mareu.Events.DeleteMeetingEvent;
import com.example.mareu.Models.Meeting;
import com.example.mareu.R;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

import static com.example.mareu.Activities.MainActivity.mMeetingService;

public class MeetingFragment extends Fragment
{
    List<Meeting> meetings = new ArrayList<>();
    MeetingAdapter mMeetingAdapter;
    RecyclerView mRecyclerView;
    private DeleteMeetingEvent event;

    public static MeetingFragment newInstance() {
        MeetingFragment fragment = new MeetingFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        meetings = mMeetingService.getMeetings();
        View recyclerView,emptyView;

        if (!meetings.isEmpty()) {
            recyclerView = inflater.inflate(R.layout.list_meeting, container, false);

            Context context = recyclerView.getContext();
            mRecyclerView = (RecyclerView) recyclerView;
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            mRecyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

            mMeetingAdapter = new MeetingAdapter(meetings,context);
            mRecyclerView.setAdapter(mMeetingAdapter);

            return recyclerView;
        }
        else{
            emptyView = inflater.inflate(R.layout.empty_recyclerview,container,false);
            return emptyView;
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(DeleteMeetingEvent event){
        mMeetingService.removeMeeting(event.meeting.getName());

    }

}
