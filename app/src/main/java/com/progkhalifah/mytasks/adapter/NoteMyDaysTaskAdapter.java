package com.progkhalifah.mytasks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.PrimaryKey;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.progkhalifah.mytasks.MydaysTasks;
import com.progkhalifah.mytasks.R;
import com.progkhalifah.mytasks.pojo.MyTasksDatabase;
import com.progkhalifah.mytasks.pojo.NoteMyDayTask;
import com.progkhalifah.mytasks.viewmodel.MyTaskViewModel;

import java.util.ArrayList;
import java.util.List;

public class NoteMyDaysTaskAdapter extends RecyclerView.Adapter<NoteMyDaysTaskAdapter.NoteMyDayTaskHolder> {

    private List<NoteMyDayTask> notesMyDayTaskList = new ArrayList<>();
    OnItemClickListener listener;
    MyTasksDatabase database;
    Context context;

    @NonNull
    @Override
    public NoteMyDayTaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteMyDayTaskHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card_mydaystasks,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteMyDayTaskHolder holder, int position) {

        database = MyTasksDatabase.getInstance(context);

        NoteMyDayTask currentNoteMydayTask = notesMyDayTaskList.get(position);
        holder.txt_title_mydaytask.setText(currentNoteMydayTask.getTitle());
        holder.txt_description_mydaytask.setText(currentNoteMydayTask.getDescription());
        holder.txt_timedate_mydaytask.setText(currentNoteMydayTask.getTimedate());

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
                    NoteMyDayTask d = notesMyDayTaskList.get(holder.getAdapterPosition());
                    database.noteMyDayTaskDao().delete(d);
                    int position = holder.getAdapterPosition();
                    notesMyDayTaskList.remove(position);
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
        return notesMyDayTaskList.size();
    }

    public NoteMyDayTask getNoteMyDayTaskAt(int position){
        return notesMyDayTaskList.get(position);
    }

    public void setListMyDayTasks(List<NoteMyDayTask> notesMyDayTaskList){
        this.notesMyDayTaskList = notesMyDayTaskList;
        notifyDataSetChanged();
    }


    public class NoteMyDayTaskHolder extends RecyclerView.ViewHolder {
        private TextView txt_title_mydaytask;
        private TextView txt_description_mydaytask;
        private TextView txt_timedate_mydaytask;
        private Button image_check_list;
        private Button image_checked_list_active;
        private View view_line_cancel;
        public NoteMyDayTaskHolder(@NonNull View itemView) {
            super(itemView);
            txt_title_mydaytask = itemView.findViewById(R.id.txt_title_of_daytasks);
            txt_description_mydaytask = itemView.findViewById(R.id.txt_description_of_daytasks);
            txt_timedate_mydaytask = itemView.findViewById(R.id.txt_dateandday_of_daytasks);
            image_check_list = itemView.findViewById(R.id.image_checke_list);
            view_line_cancel = itemView.findViewById(R.id.view_line_cancel);
            image_checked_list_active = itemView.findViewById(R.id.image_checked_list_active);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(notesMyDayTaskList.get(position));
                    }
                }
            });

        }
    }


    public interface OnItemClickListener{
        void onItemClick(NoteMyDayTask noteMyDayTask);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
