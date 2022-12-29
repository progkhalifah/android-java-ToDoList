package com.progkhalifah.mytasks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.progkhalifah.mytasks.R;
import com.progkhalifah.mytasks.pojo.MyTasksDatabase;
import com.progkhalifah.mytasks.pojo.NoteMyDayTask;
import com.progkhalifah.mytasks.pojo.NoteMyImportance;

import java.util.ArrayList;
import java.util.List;

public class NoteMyImportanceAdapter extends RecyclerView.Adapter<NoteMyImportanceAdapter.NoteMyImportanceHolder> {

    private List<NoteMyImportance> notesMyImportanceList = new ArrayList<>();
    OnItemClickListener listener;
    MyTasksDatabase database;
    Context context;

    @NonNull
    @Override
    public NoteMyImportanceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new NoteMyImportanceHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card_myimportance,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull NoteMyImportanceHolder holder, int position) {

        database = MyTasksDatabase.getInstance(context);

        NoteMyImportance currentNoteMyImportance = notesMyImportanceList.get(position);
        holder.txt_title_myimportance.setText(currentNoteMyImportance.getTitle());
        holder.txt_description_myimportance.setText(currentNoteMyImportance.getDescription());
        holder.txt_timedate_myimportance.setText(currentNoteMyImportance.getTimedate());

        // TODO: 10/16/2021 here make an action of button check
        holder.image_check_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.image_check_list.setVisibility(View.GONE);
                holder.view_line_cancel.setVisibility(View.VISIBLE);
                holder.image_checked_list_active.setVisibility(View.VISIBLE);

                try {
                    database = Room.databaseBuilder(v.getContext(), MyTasksDatabase.class, "note_mytasks_database")
                            .allowMainThreadQueries().build();
                    NoteMyImportance d = notesMyImportanceList.get(holder.getAdapterPosition());
                    database.noteMyImportanceDao().delete(d);
                    int position = holder.getAdapterPosition();
                    notesMyImportanceList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemChanged(position);
                    Toast.makeText(v.getContext(), "Tasks completed successfully", Toast.LENGTH_SHORT).show();

                }catch (Exception e){
                    Toast.makeText(v.getContext(), e.getStackTrace().toString(), Toast.LENGTH_SHORT).show();
                }




            }
        });


    }

    @Override
    public int getItemCount() {
        return notesMyImportanceList.size();
    }

    public NoteMyImportance getNoteMyImportanceAt(int position){
        return notesMyImportanceList.get(position);
    }

    public void setListMyImportance(List<NoteMyImportance> notesMyDayTaskList){
        this.notesMyImportanceList = notesMyDayTaskList;
        notifyDataSetChanged();
    }

    public class NoteMyImportanceHolder extends RecyclerView.ViewHolder {

        private TextView txt_title_myimportance;
        private TextView txt_description_myimportance;
        private TextView txt_timedate_myimportance;
        private Button image_check_list;
        private Button image_checked_list_active;
        private View view_line_cancel;


        public NoteMyImportanceHolder(@NonNull View itemView) {
            super(itemView);
            txt_title_myimportance = itemView.findViewById(R.id.txt_title_of_importance);
            txt_description_myimportance = itemView.findViewById(R.id.txt_description_of_importance);
            txt_timedate_myimportance = itemView.findViewById(R.id.txt_dateandday_of_importance);
            image_check_list = itemView.findViewById(R.id.image_checke_list);
            view_line_cancel = itemView.findViewById(R.id.view_line_cancel);
            image_checked_list_active = itemView.findViewById(R.id.image_checked_list_active);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(notesMyImportanceList.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(NoteMyImportance noteMyImportance);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }



}
