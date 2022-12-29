package com.progkhalifah.mytasks.pojo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteMyDayTaskRepository {
    private NoteMyDayTaskDao noteMyDayTaskDao;
    private LiveData<List<NoteMyDayTask>> allnotemydaytasks;

    public NoteMyDayTaskRepository(Application application){
        MyTasksDatabase database = MyTasksDatabase.getInstance(application);
        noteMyDayTaskDao = database.noteMyDayTaskDao();
        allnotemydaytasks = noteMyDayTaskDao.getAllNoteMyDayTask();
    }

    public void insert(NoteMyDayTask noteMyDayTask){
        new InsertNoteMyDayTaskAsyncTask(noteMyDayTaskDao).execute(noteMyDayTask);
    }

    private static class InsertNoteMyDayTaskAsyncTask extends AsyncTask<NoteMyDayTask, Void, Void>{

        private NoteMyDayTaskDao noteMyDayTaskDao;

        public InsertNoteMyDayTaskAsyncTask(NoteMyDayTaskDao noteMyDayTaskDao) {
            this.noteMyDayTaskDao = noteMyDayTaskDao;
        }

        @Override
        protected Void doInBackground(NoteMyDayTask... noteMyDayTasks) {
            noteMyDayTaskDao.insert(noteMyDayTasks[0]);

            return null;
        }
    }
    //////////////////////////////////////////END INSERT//////////////////////////////////////////////////////

    public void update(NoteMyDayTask noteMyDayTask){
        new UpdateNoteMyDayTaskAsyncTask(noteMyDayTaskDao).execute(noteMyDayTask);
    }

    private static class UpdateNoteMyDayTaskAsyncTask extends AsyncTask<NoteMyDayTask, Void, Void>{

        private NoteMyDayTaskDao noteMyDayTaskDao;

        public UpdateNoteMyDayTaskAsyncTask(NoteMyDayTaskDao noteMyDayTaskDao) {
            this.noteMyDayTaskDao = noteMyDayTaskDao;
        }

        @Override
        protected Void doInBackground(NoteMyDayTask... noteMyDayTasks) {
            noteMyDayTaskDao.update(noteMyDayTasks[0]);

            return null;
        }
    }


    ////////////////////////////////////////////END UPDATE////////////////////////////////////////////////////

    public void delete(NoteMyDayTask noteMyDayTask) {
        new DeleteNoteMyDayTaskAsyncTask(noteMyDayTaskDao).execute(noteMyDayTask);
    }

    private static class DeleteNoteMyDayTaskAsyncTask extends AsyncTask<NoteMyDayTask, Void, Void> {

        private NoteMyDayTaskDao noteMyDayTaskDao;

        public DeleteNoteMyDayTaskAsyncTask(NoteMyDayTaskDao noteMyDayTaskDao) {
            this.noteMyDayTaskDao = noteMyDayTaskDao;
        }

        @Override
        protected Void doInBackground(NoteMyDayTask... noteMyDayTasks) {
            noteMyDayTaskDao.delete(noteMyDayTasks[0]);

            return null;
        }
    }

    ////////////////////////////////////////////END DELETE////////////////////////////////////////////////////

    public void deleteAllNotes() {
        new DeleteAllNoteMyDayTasksAsyncTask(noteMyDayTaskDao).execute();
    }

    public static class DeleteAllNoteMyDayTasksAsyncTask extends AsyncTask<Void, Void, Void>{

        private NoteMyDayTaskDao noteMyDayTaskDao;

        public DeleteAllNoteMyDayTasksAsyncTask(NoteMyDayTaskDao noteMyDayTaskDao) {
            this.noteMyDayTaskDao = noteMyDayTaskDao;
        }

        @Override
        protected Void doInBackground(Void... Void) {
            noteMyDayTaskDao.deleteAllNoteMydaytask();

            return null;
        }
    }



    ////////////////////////////////////////////END DELETEALL////////////////////////////////////////////////////

    public LiveData<List<NoteMyDayTask>> getAllnotemydaytasks() {
        return allnotemydaytasks;
    }

    ////////////////////////////////////////////END RETRIVEALL////////////////////////////////////////////////////

}
