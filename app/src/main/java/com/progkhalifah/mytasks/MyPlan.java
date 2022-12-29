package com.progkhalifah.mytasks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.progkhalifah.mytasks.adapter.NoteMyImportanceAdapter;
import com.progkhalifah.mytasks.adapter.NoteMyPlanAdapter;
import com.progkhalifah.mytasks.pojo.NoteMyImportance;
import com.progkhalifah.mytasks.pojo.NoteMyPlan;
import com.progkhalifah.mytasks.viewmodel.MyTaskViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MyPlan extends AppCompatActivity {


    Toolbar toolbar;
    TextView txt_myplan_date;
    ImageView image_there_is_no_tasks;
    RecyclerView recyclerView;
    FloatingActionButton btn_add_myplan;
    private MyTaskViewModel myTaskViewModel;
    List<String> myplantasklist;
    public static final int EDIT_NOTE_REQUEST = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plan);
        //////////////////////////////////////////////////////////////////////
        intial();
        getcurrentdateandday();
        //////////////////////////////////////////////////////////////////////
        myplantasklist = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final NoteMyPlanAdapter adapter = new NoteMyPlanAdapter();
        recyclerView.setAdapter(adapter);

        myTaskViewModel = ViewModelProviders.of(this).get(MyTaskViewModel.class);
        myTaskViewModel.getAllnotesmyPlan().observe(this, new Observer<List<NoteMyPlan>>() {
            @Override
            public void onChanged(List<NoteMyPlan> noteMyPlans) {
                Toast.makeText(MyPlan.this, "Welcome to My Plan", Toast.LENGTH_SHORT).show(); // TODO: 10/15/2021 delete this toast
                if (noteMyPlans.isEmpty()) {
                    Toast.makeText(MyPlan.this, "Sorry there is no Tasks", Toast.LENGTH_SHORT).show();
                    image_there_is_no_tasks.setVisibility(View.VISIBLE);
                } else {
                    adapter.setListMyPlan(noteMyPlans);
                    image_there_is_no_tasks.setVisibility(View.GONE);
                }
                adapter.setListMyPlan(noteMyPlans);
            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            private ColorDrawable background = null;

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                background = new ColorDrawable(Color.RED);

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position =viewHolder.getAdapterPosition();
                switch (direction){
                    case ItemTouchHelper.LEFT:
                        myTaskViewModel.deleteMyPlan(adapter.getNoteMyPlanAt(viewHolder.getAdapterPosition()));
                        Snackbar snackbar = Snackbar.make(recyclerView, "Task Deleted Successfully", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        break;
                    default:
                        Toast.makeText(MyPlan.this, "Something went wrong...!", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(MyPlan.this, R.color.deletetask))
                        .addActionIcon(R.drawable.ic_delete_forever)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);


            }
        }).attachToRecyclerView(recyclerView);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        btn_add_myplan.setOnClickListener(new View.OnClickListener() {
            int notificationId = 1;
            @Override
            public void onClick(View v) {
                // create BottomSheetingDialog
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MyPlan.this);
                bottomSheetDialog.setContentView(R.layout.bottomsheet_importance_dialog);
                bottomSheetDialog.setCanceledOnTouchOutside(false);
                //initialize
                EditText edtxt_titlemyplan_tasks = bottomSheetDialog.findViewById(R.id.edtxt_titleimportance_tasks);
                EditText edtxt_descriptionmyplan_tasks = bottomSheetDialog.findViewById(R.id.edtxt_descriptionimportance_tasks);
                Button btn_to_reminder = bottomSheetDialog.findViewById(R.id.btn_to_reminder);
                TextView txt_reminder_me = bottomSheetDialog.findViewById(R.id.txt_reminder_me);
                TextView txt_reminder_me_unactivited = bottomSheetDialog.findViewById(R.id.txt_reminder_me_unactivited);
                Button btn_sumbitmyplan_task = bottomSheetDialog.findViewById(R.id.btn_submitimportance_tasks);
                Button btn_sumbitmyplan_task_forReminder = bottomSheetDialog.findViewById(R.id.btn_submitimportance_tasks_forReminder);
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                Calendar calendar = Calendar.getInstance();
                Intent intent = new Intent(MyPlan.this, AlarmReciver.class);
                AlarmManager alram;
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                alram = (AlarmManager) getSystemService(ALARM_SERVICE);
                txt_reminder_me.setOnClickListener(v1 -> {
                    txt_reminder_me.setVisibility(View.GONE);
                    btn_sumbitmyplan_task.setVisibility(View.GONE);
                    btn_sumbitmyplan_task_forReminder.setVisibility(View.INVISIBLE);
                    txt_reminder_me_unactivited.setVisibility(View.VISIBLE);
                    btn_to_reminder.setVisibility(View.VISIBLE);
                });
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                txt_reminder_me_unactivited.setOnClickListener(v1 -> {
                    txt_reminder_me_unactivited.setVisibility(View.GONE);
                    txt_reminder_me.setVisibility(View.VISIBLE);
                    btn_to_reminder.setVisibility(View.GONE);
                    btn_sumbitmyplan_task_forReminder.setVisibility(View.GONE);
                    btn_sumbitmyplan_task.setVisibility(View.VISIBLE);
                });
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                btn_to_reminder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn_sumbitmyplan_task_forReminder.setVisibility(View.VISIBLE);

//                         alramIntent = PendingIntent.getBroadcast(MyPlan.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                        calendar.set(Calendar.MINUTE, minute);


                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-mm-yy hh:mm");

                                        Toast.makeText(MyPlan.this, simpleDateFormat.format(calendar.getTime()), Toast.LENGTH_SHORT).show();
                                    }
                                };

                                new TimePickerDialog(MyPlan.this, onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();

                            }
                        };

                        new DatePickerDialog(MyPlan.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

                    }
                });// TODO: 10/20/2021 end of btn reminder

                btn_sumbitmyplan_task.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = edtxt_titlemyplan_tasks.getText().toString();
                        String description = edtxt_descriptionmyplan_tasks.getText().toString();
                        String timedate = txt_myplan_date.getText().toString();
                        if (title.trim().isEmpty() || description.trim().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Please insert a title and description", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        try {

                            if (!title.isEmpty() || description.isEmpty()) {
                                NoteMyPlan noteMyPlan = new NoteMyPlan(title, description, timedate);
                                myTaskViewModel.insertMyPlanTask(noteMyPlan);
                            }


                        } catch (Exception e) {
                            e.getStackTrace();
                            Toast.makeText(getApplicationContext(), e.getStackTrace().toString(), Toast.LENGTH_SHORT).show();
                            Log.d("MY Day Task", "onCreate:" + e.getStackTrace());
                        }

                        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    }
                });
                btn_sumbitmyplan_task_forReminder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = edtxt_titlemyplan_tasks.getText().toString();
                        String description = edtxt_descriptionmyplan_tasks.getText().toString();
                        String timedate = txt_myplan_date.getText().toString();
                        if (title.trim().isEmpty() || description.trim().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Please insert a title and description", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        try {

                            if (!title.isEmpty() || description.isEmpty()) {
                                NoteMyPlan noteMyPlan = new NoteMyPlan(title, description, timedate);
                                myTaskViewModel.insertMyPlanTask(noteMyPlan);
                            }


                        } catch (Exception e) {
                            e.getStackTrace();
                            Toast.makeText(getApplicationContext(), e.getStackTrace().toString(), Toast.LENGTH_SHORT).show();
                            Log.d("MY Day Task", "onCreate:" + e.getStackTrace());
                        }

                        intent.putExtra("notification", notificationId);
                        intent.putExtra("todo", edtxt_titlemyplan_tasks.getText().toString());
                        intent.putExtra("description", edtxt_descriptionmyplan_tasks.getText().toString());
                        PendingIntent alramIntent = PendingIntent.getBroadcast(MyPlan.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                        long alarmDateandTime = calendar.getTimeInMillis();

                        alram.set(AlarmManager.RTC_WAKEUP, alarmDateandTime, alramIntent);
                        Toast.makeText(getApplicationContext(), "he will reminder you", Toast.LENGTH_SHORT).show();
                        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


                    }
                });
                bottomSheetDialog.show();
            }
        });

        adapter.setOnItemClickListener(new NoteMyPlanAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(NoteMyPlan noteMyPlan) {
                Intent intent = new Intent(MyPlan.this, EditMyTasks.class);
                intent.putExtra(EditMyTasks.EXTRA_ID, noteMyPlan.getId());
                intent.putExtra(EditMyTasks.EXTRA_TITLE, noteMyPlan.getTitle());
                intent.putExtra(EditMyTasks.EXTRA_DESCRIPTION, noteMyPlan.getDescription());
                startActivityForResult(intent,EDIT_NOTE_REQUEST);
            }
        });


    }// TODO: 10/20/2021 end of onCreate

    private void intial() {
        toolbar = findViewById(R.id.toolbar_myplan);
        txt_myplan_date = findViewById(R.id.txt_myplan_date);
        image_there_is_no_tasks = findViewById(R.id.image_there_is_no_tasks);
        recyclerView = findViewById(R.id.recyclerview);
        btn_add_myplan = findViewById(R.id.button_add_myplan);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void getcurrentdateandday() {
        Calendar calendar = Calendar.getInstance();
        int dayofdate = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.SATURDAY:
                txt_myplan_date.setText("Saturday " + dayofdate + "/" + month + "/" + year);
                break;
            case Calendar.SUNDAY:
                txt_myplan_date.setText("Sunday " + dayofdate + "/" + month + "/" + year);
                break;
            case Calendar.MONDAY:
                txt_myplan_date.setText("Monday " + dayofdate + "/" + month + "/" + year);
                break;
            case Calendar.TUESDAY:
                txt_myplan_date.setText("Tuesday " + dayofdate + "/" + month + "/" + year);
                break;
            case Calendar.WEDNESDAY:
                txt_myplan_date.setText("Wednesday " + dayofdate + "/" + month + "/" + year);
                break;
            case Calendar.THURSDAY:
                txt_myplan_date.setText("Thursday" + dayofdate + "/" + month + "/" + year);
                break;
            case Calendar.FRIDAY:
                txt_myplan_date.setText("Friday " + dayofdate + "/" + month + "/" + year);
                break;
            default:
                Toast.makeText(this, "There is something happened", Toast.LENGTH_SHORT).show();
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== EDIT_NOTE_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(EditMyTasks.EXTRA_ID, 1);
            if (id == 1){
                Toast.makeText(this, "Task can't be update", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(EditMyTasks.EXTRA_TITLE);
            String description = data.getStringExtra(EditMyTasks.EXTRA_DESCRIPTION);
            String getdataandtime= txt_myplan_date.getText().toString();
            NoteMyImportance noteMyImportance = new NoteMyImportance(title, description,getdataandtime);
            noteMyImportance.setId(id);
            myTaskViewModel.updateMyImportance(noteMyImportance);
            Toast.makeText(this, "Task updated successfully", Toast.LENGTH_SHORT).show();

        }
    }
}