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
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
import com.progkhalifah.mytasks.adapter.NoteMyDaysTaskAdapter;
import com.progkhalifah.mytasks.pojo.NoteMyDayTask;
import com.progkhalifah.mytasks.viewmodel.MyTaskViewModel;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MydaysTasks extends AppCompatActivity {


    Toolbar toolbar;
    TextView txt_myday_date;
    ImageView image_there_is_no_tasks;
    RecyclerView recyclerView;
    FloatingActionButton btn_add_mydaytasks;
    private MyTaskViewModel myTaskViewModel;
    List<String> mydaytasklist;
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydays_tasks);
        //////////////////////////////////////////////////////////////////////
        intial();
        getcurrentdateandday();

        mydaytasklist = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final NoteMyDaysTaskAdapter adapter = new NoteMyDaysTaskAdapter();
        recyclerView.setAdapter(adapter);

        myTaskViewModel = ViewModelProviders.of(this).get(MyTaskViewModel.class);
        myTaskViewModel.getAllnotesmydaytasks().observe(this, new Observer<List<NoteMyDayTask>>() {
            @Override
            public void onChanged(List<NoteMyDayTask> noteMyDayTasks) {
                Toast.makeText(MydaysTasks.this, "Welcome to My Day Task", Toast.LENGTH_SHORT).show(); // TODO: 10/15/2021 delete this toast
                if (noteMyDayTasks.isEmpty()) {
                    Toast.makeText(MydaysTasks.this, "Sorry there is no Tasks", Toast.LENGTH_SHORT).show();
                    image_there_is_no_tasks.setVisibility(View.VISIBLE);
                } else {
                    adapter.setListMyDayTasks(noteMyDayTasks);
                    image_there_is_no_tasks.setVisibility(View.GONE);
                }
                adapter.setListMyDayTasks(noteMyDayTasks);
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
                final int position = viewHolder.getAdapterPosition();
                switch (direction) {
                    case ItemTouchHelper.LEFT:
                        myTaskViewModel.deleteMyDayTask(adapter.getNoteMyDayTaskAt(viewHolder.getAdapterPosition()));
                        Snackbar snackbar = Snackbar.make(recyclerView, "Task Deleted Successfully", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        break;
                    default:
                        Toast.makeText(MydaysTasks.this, "Something went wrong...!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(MydaysTasks.this, R.color.deletetask))
                        .addActionIcon(R.drawable.ic_delete_forever)
                        .create()
                        .decorate();


                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(recyclerView);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        btn_add_mydaytasks.setOnClickListener(v -> {
            int notificationId = 1;
            // create BottomSheetingDialog
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MydaysTasks.this);
            bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog);
            bottomSheetDialog.setCanceledOnTouchOutside(false);
            //initialize
            EditText edtxt_title_tasks = bottomSheetDialog.findViewById(R.id.edtxt_titlemyday_tasks);
            EditText edtxt_description_tasks = bottomSheetDialog.findViewById(R.id.edtxt_descriptionmyday_tasks);
            Button btn_to_reminder = bottomSheetDialog.findViewById(R.id.btn_to_reminder);
            TextView txt_reminder_me = bottomSheetDialog.findViewById(R.id.txt_reminder_me);
            TextView txt_reminder_me_unactivited = bottomSheetDialog.findViewById(R.id.txt_reminder_me_unactivited);
            Button btn_sumbitmyday_task = bottomSheetDialog.findViewById(R.id.btn_submitmyday_tasks);
            Button btn_sumbitmyday_task_forReminder = bottomSheetDialog.findViewById(R.id.btn_submitmyday_tasks_forReminder);
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            Calendar calendar = Calendar.getInstance();
            Intent intent = new Intent(MydaysTasks.this, AlarmReciver.class);
            AlarmManager alram;
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            alram = (AlarmManager) getSystemService(ALARM_SERVICE);
            txt_reminder_me.setOnClickListener(v1 -> {
                txt_reminder_me.setVisibility(View.GONE);
                btn_sumbitmyday_task.setVisibility(View.GONE);
                btn_sumbitmyday_task_forReminder.setVisibility(View.INVISIBLE);
                txt_reminder_me_unactivited.setVisibility(View.VISIBLE);
                btn_to_reminder.setVisibility(View.VISIBLE);
            });
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            txt_reminder_me_unactivited.setOnClickListener(v1 -> {
                txt_reminder_me_unactivited.setVisibility(View.GONE);
                txt_reminder_me.setVisibility(View.VISIBLE);
                btn_to_reminder.setVisibility(View.GONE);
                btn_sumbitmyday_task_forReminder.setVisibility(View.GONE);
                btn_sumbitmyday_task.setVisibility(View.VISIBLE);
            });
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            btn_to_reminder.setOnClickListener(v1 -> {
                btn_sumbitmyday_task_forReminder.setVisibility(View.VISIBLE);

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

                                Toast.makeText(MydaysTasks.this, simpleDateFormat.format(calendar.getTime()), Toast.LENGTH_SHORT).show();
                            }
                        };

                        new TimePickerDialog(MydaysTasks.this, onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();

                    }
                };

                new DatePickerDialog(MydaysTasks.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();


            });// TODO: 10/15/2021 end of reminder

            btn_sumbitmyday_task.setOnClickListener(v1 -> {
                String title = edtxt_title_tasks.getText().toString();
                String description = edtxt_description_tasks.getText().toString();
                String timedate = txt_myday_date.getText().toString();
                if (title.trim().isEmpty() || description.trim().isEmpty()) {
                    Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {

                    if (!title.isEmpty() || description.isEmpty()) {
                        NoteMyDayTask noteMyDayTask = new NoteMyDayTask(title, description, timedate);
                        myTaskViewModel.insertMyDayTask(noteMyDayTask);
                    }


                } catch (Exception e) {
                    e.getStackTrace();
                    Toast.makeText(this, e.getStackTrace().toString(), Toast.LENGTH_SHORT).show();
                    Log.d("MY Day Task", "onCreate:" + e.getStackTrace());
                }


            });

            btn_sumbitmyday_task_forReminder.setOnClickListener(v1 -> {

                String title = edtxt_title_tasks.getText().toString();
                String description = edtxt_description_tasks.getText().toString();
                String timedate = txt_myday_date.getText().toString();
                if (title.trim().isEmpty() || description.trim().isEmpty()) {
                    Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {

                    if (!title.isEmpty() || description.isEmpty()) {
                        NoteMyDayTask noteMyDayTask = new NoteMyDayTask(title, description, timedate);
                        myTaskViewModel.insertMyDayTask(noteMyDayTask);
                    }


                } catch (Exception e) {
                    e.getStackTrace();
                    Toast.makeText(this, e.getStackTrace().toString(), Toast.LENGTH_SHORT).show();
                    Log.d("MY Day Task", "onCreate:" + e.getStackTrace());
                }

                intent.putExtra("notification", notificationId);
                intent.putExtra("todo", edtxt_title_tasks.getText().toString());
                intent.putExtra("description", edtxt_description_tasks.getText().toString());
                PendingIntent alramIntent = PendingIntent.getBroadcast(MydaysTasks.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                long alarmDateandTime = calendar.getTimeInMillis();

                alram.set(AlarmManager.RTC_WAKEUP, alarmDateandTime, alramIntent);

                Toast.makeText(this, "he will reminder you", Toast.LENGTH_SHORT).show();
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            });


            bottomSheetDialog.show();
        });// TODO: 10/15/2021 end of mydaytasks
        //////////////////////////////////////////////////////////////////////


        adapter.setOnItemClickListener(new NoteMyDaysTaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(NoteMyDayTask noteMyDayTask) {
                Intent intent = new Intent(MydaysTasks.this, EditMyTasks.class);
                intent.putExtra(EditMyTasks.EXTRA_ID, noteMyDayTask.getId());
                intent.putExtra(EditMyTasks.EXTRA_TITLE, noteMyDayTask.getTitle());
                intent.putExtra(EditMyTasks.EXTRA_DESCRIPTION, noteMyDayTask.getDescription());
                startActivityForResult(intent,EDIT_NOTE_REQUEST);
            }
        });



    }// TODO: 10/15/2021 end of on Create

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void intial() {
        toolbar = findViewById(R.id.toolbar_mydaytaks);
        txt_myday_date = findViewById(R.id.txt_myday_date);
        image_there_is_no_tasks = findViewById(R.id.image_there_is_no_tasks);
        recyclerView = findViewById(R.id.recyclerview);
        btn_add_mydaytasks = findViewById(R.id.button_add_mydaystasks);
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
                txt_myday_date.setText("Saturday " + dayofdate + "/" + month + "/" + year);
                break;
            case Calendar.SUNDAY:
                txt_myday_date.setText("Sunday " + dayofdate + "/" + month + "/" + year);
                break;
            case Calendar.MONDAY:
                txt_myday_date.setText("Monday " + dayofdate + "/" + month + "/" + year);
                break;
            case Calendar.TUESDAY:
                txt_myday_date.setText("Tuesday " + dayofdate + "/" + month + "/" + year);
                break;
            case Calendar.WEDNESDAY:
                txt_myday_date.setText("Wednesday " + dayofdate + "/" + month + "/" + year);
                break;
            case Calendar.THURSDAY:
                txt_myday_date.setText("Thursday" + dayofdate + "/" + month + "/" + year);
                break;
            case Calendar.FRIDAY:
                txt_myday_date.setText("Friday " + dayofdate + "/" + month + "/" + year);
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
            String getdataandtime= txt_myday_date.getText().toString();
            NoteMyDayTask noteMyDayTask = new NoteMyDayTask(title, description, getdataandtime);
            noteMyDayTask.setId(id);
            myTaskViewModel.updateMyDayTask(noteMyDayTask);
            Toast.makeText(this, "Task updated successfully", Toast.LENGTH_SHORT).show();



        }
    }
}