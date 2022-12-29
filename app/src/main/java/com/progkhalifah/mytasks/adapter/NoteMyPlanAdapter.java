package com.progkhalifah.mytasks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.progkhalifah.mytasks.R;
import com.progkhalifah.mytasks.pojo.MyTasksDatabase;
import com.progkhalifah.mytasks.pojo.NoteMyImportance;
import com.progkhalifah.mytasks.pojo.NoteMyPlan;

import java.util.ArrayList;
import java.util.List;

public class NoteMyPlanAdapter extends RecyclerView.Adapter<NoteMyPlanAdapter.NoteMyPlanHolder> {
    private List<NoteMyPlan> notesMyPlanList = new ArrayList<>();
    OnItemClickListener listener;
    MyTasksDatabase database;
    Context context;


    @NonNull
    @Override
    public NoteMyPlanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteMyPlanHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card_myplan,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull NoteMyPlanHolder holder, int position) {
        database = MyTasksDatabase.getInstance(context);

        NoteMyPlan currentNoteMyPlan = notesMyPlanList.get(position);
        holder.txt_title_myplan.setText(currentNoteMyPlan.getTitle());
        holder.txt_description_myplan.setText(currentNoteMyPlan.getDescription());
        holder.txt_timedate_myiplan.setText(currentNoteMyPlan.getTimedate());

        holder.image_check_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.image_check_list.setVisibility(View.GONE);
                holder.view_line_cancel.setVisibility(View.VISIBLE);
                holder.image_checked_list_active.setVisibility(View.VISIBLE);

                try {
                    database = Room.databaseBuilder(v.getContext(), MyTasksDatabase.class, "note_mytasks_database")
                            .allowMainThreadQueries().build();
                    NoteMyPlan d = notesMyPlanList.get(holder.getAdapterPosition());
                    database.noteMyPlanDao().delete(d);
                    int position = holder.getAdapterPosition();
                    notesMyPlanList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemChanged(position);
                    Toast.makeText(v.getContext(), "Tasks completed successfully", Toast.LENGTH_SHORT).show();

                }catch (Exception e){
                    Toast.makeText(v.getContext(), e.getStackTrace().toString(), Toast.LENGTH_SHORT).show();
                }




            }
        });

        holder.image_checked_list_active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.image_checked_list_active.setVisibility(View.GONE);
                holder.view_line_cancel.setVisibility(View.GONE);
                holder.image_check_list.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return notesMyPlanList.size();
    }


    public NoteMyPlan getNoteMyPlanAt(int position){
        return notesMyPlanList.get(position);
    }

    public void setListMyPlan(List<NoteMyPlan> notesMyPlanList){
        this.notesMyPlanList = notesMyPlanList;
        notifyDataSetChanged();
    }


    public class NoteMyPlanHolder extends RecyclerView.ViewHolder {
        private TextView txt_title_myplan;
        private TextView txt_description_myplan;
        private TextView txt_timedate_myiplan;
        private Button image_check_list;
        private Button image_checked_list_active;
        private View view_line_cancel;
        public NoteMyPlanHolder(@NonNull View itemView) {
            super(itemView);
            txt_title_myplan = itemView.findViewById(R.id.txt_title_of_myplan);
            txt_description_myplan = itemView.findViewById(R.id.txt_description_of_myplan);
            txt_timedate_myiplan = itemView.findViewById(R.id.txt_dateandday_of_myplan);
            image_check_list = itemView.findViewById(R.id.image_checke_list);
            view_line_cancel = itemView.findViewById(R.id.view_line_cancel);
            image_checked_list_active = itemView.findViewById(R.id.image_checked_list_active);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(notesMyPlanList.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(NoteMyPlan noteMyPlan);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }



}
