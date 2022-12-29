package com.progkhalifah.mytasks.pojo;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {NoteMyDayTask.class, NoteMyImportance.class, NoteMyPlan.class} , version = 1)
public abstract class MyTasksDatabase extends RoomDatabase {

    private static MyTasksDatabase instance;

    public abstract NoteMyDayTaskDao noteMyDayTaskDao();
    public abstract NoteMyImportanceDao noteMyImportanceDao();
    public abstract NoteMyPlanDao noteMyPlanDao();

    public static synchronized MyTasksDatabase getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MyTasksDatabase.class, "note_mytasks_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{

        private NoteMyDayTaskDao noteMyDayTaskDao;
        private NoteMyImportanceDao noteMyImportanceDao;
        private NoteMyPlanDao noteMyPlanDao;

        public PopulateDbAsyncTask(MyTasksDatabase db){
            noteMyDayTaskDao =db.noteMyDayTaskDao();
            noteMyImportanceDao =db.noteMyImportanceDao();
            noteMyPlanDao =db.noteMyPlanDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteMyDayTaskDao.insert(new NoteMyDayTask("Title 1", "Description 1",  "today is 1"));
            noteMyDayTaskDao.insert(new NoteMyDayTask("Title 2", "Description 2", "today is 2"));
            noteMyDayTaskDao.insert(new NoteMyDayTask("Title 3", "Description 3",  "today is 3"));

            noteMyImportanceDao.insert(new NoteMyImportance("Title 3", "Description 3", "today is 3"));
            noteMyImportanceDao.insert(new NoteMyImportance("Title 3", "Description 3",  "today is 3"));
            noteMyImportanceDao.insert(new NoteMyImportance("Title 3", "Description 3",  "today is 3"));

            noteMyPlanDao.insert(new NoteMyPlan("Title 3", "Description 3",  "today is 3"));
            noteMyPlanDao.insert(new NoteMyPlan("Title 3", "Description 3",  "today is 3"));
            noteMyPlanDao.insert(new NoteMyPlan("Title 3", "Description 3",  "today is 3"));
            return null;
        }
    }


}

