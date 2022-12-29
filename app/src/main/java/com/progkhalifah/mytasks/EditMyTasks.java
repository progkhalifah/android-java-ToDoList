package com.progkhalifah.mytasks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditMyTasks extends AppCompatActivity {

    public static final String EXTRA_ID =
            "com.progkhalifah.mytasks.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.progkhalifah.mytasks.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.progkhalifah.mytasks.EXTRA_DESCRIPTION";


    EditText edtxt_edit_title_tasks, edtxt_edit_description_tasks;
    Button btn_submint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_tasks);

        getextardatafromanother();
        btn_submint.setOnClickListener(v -> {
            editdatatouser();
        });




    }// TODO: 10/17/2021 end of onCreate



    private void getextardatafromanother() {
        edtxt_edit_title_tasks = findViewById(R.id.edtxt_edit_title_tasks);
        edtxt_edit_description_tasks = findViewById(R.id.edtxt_edit_description_tasks);
        btn_submint = findViewById(R.id.btn_submit_tasks);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)){
            setTitle("Edit note");
            edtxt_edit_title_tasks.setText(intent.getStringExtra(EXTRA_TITLE));
            edtxt_edit_description_tasks.setText(intent.getStringExtra(EXTRA_DESCRIPTION));

        }else {
            setTitle("Warning: You will add note");
        }
    }

    private void editdatatouser() {

        String title = edtxt_edit_title_tasks.getText().toString();
        String description = edtxt_edit_description_tasks.getText().toString();
        if (title.isEmpty() || description.isEmpty()){
            Toast.makeText(this, "You have to enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }


        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        int id = getIntent().getIntExtra(EXTRA_ID, 1);
        if (id != -1)
            data.putExtra(EXTRA_ID, id);

        setResult(RESULT_OK, data);
        finish();
    }


}