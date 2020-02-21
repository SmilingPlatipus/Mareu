package com.example.mareu.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.Models.Meeting;
import com.example.mareu.R;

import java.util.Iterator;
import java.util.List;

import static com.example.mareu.Activities.MainActivity.mMeetingService;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.ViewHolder>
{
    private List<Meeting> meetings;
    private Context mContext;

    public MeetingAdapter(List<Meeting> meetings, Context mContext) {
        this.meetings = meetings;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_meeting,
                                                                     parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        String meetingInformations,meetingEmails;
        meetingEmails = new String();
        Meeting item;
        Iterator currentMeeting = meetings.iterator();

        do {
            item = (Meeting) currentMeeting.next();
            meetingInformations = item.getName() + "  " + item.getStartHour()+item.getStartMinutes()+ "  " + item.getEndHour()+item.getEndMinutes()+"  " + item.getRoom();
            holder.meetingSummary.setText(meetingInformations);

            Iterator currentEmail = item.getEmails().iterator();
            do {
                meetingEmails += currentEmail.next();
                if (currentEmail.hasNext())
                    meetingEmails += ", ";
            }while (currentEmail.hasNext());
            holder.meetingPeoples.setText(meetingEmails);

        }while (currentMeeting.hasNext());

        // Gestion clics sur bouton supprimer

        holder.deleteButton.setOnClickListener(new View.OnClickListener()
        {
            String meetingToDelete = mMeetingService.getMeetingName(position);
            @Override
            public void onClick(View view) {
                mMeetingService.removeMeeting(meetingToDelete);
                Toast.makeText(mContext, "Réunion " + meetingToDelete + " supprimée", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView meetingSummary, meetingPeoples;
        ImageButton deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            meetingSummary = itemView.findViewById(R.id.meeting_summary);
            meetingPeoples = itemView.findViewById(R.id.meeting_peoples);
            deleteButton = itemView.findViewById(R.id.meeting_delete_button);
        }




}


}
