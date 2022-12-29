package com.progkhalifah.mytasks.pojo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteMyImportanceRepository {
    private NoteMyImportanceDao noteMyImportanceDao;
    private LiveData<List<NoteMyImportance>> allnotemyimportance;

    public NoteMyImportanceRepository(Application application){
        MyTasksDatabase database = MyTasksDatabase.getInstance(application);
        noteMyImportanceDao = database.noteMyImportanceDao();
        allnotemyimportance = noteMyImportanceDao.getAllNoteMyImportance();
    }

    public void insert(NoteMyImportance noteMyImportance){
        new InsertNoteMyImportanceAsyncTask(noteMyImportanceDao).execute(noteMyImportance);
    }

    private static class InsertNoteMyImportanceAsyncTask extends AsyncTask<NoteMyImportance, Void, Void> {

        private NoteMyImportanceDao noteMyImportanceDao;

        public InsertNoteMyImportanceAsyncTask(NoteMyImportanceDao noteMyImportanceDao) {
            this.noteMyImportanceDao = noteMyImportanceDao;
        }

        @Override
        protected Void doInBackground(NoteMyImportance... noteMyImportances) {
            noteMyImportanceDao.insert(noteMyImportances[0]);

            return null;
        }
    }

    //////////////////////////////////////////END INSERT//////////////////////////////////////////////////////

    public void update(NoteMyImportance noteMyImportance){
        new UpdateNoteMyImportanceAsyncTask(noteMyImportanceDao).execute(noteMyImportance);
    }

    private static class UpdateNoteMyImportanceAsyncTask extends AsyncTask<NoteMyImportance, Void, Void>{

        private NoteMyImportanceDao noteMyImportanceDao;

        public UpdateNoteMyImportanceAsyncTask(NoteMyImportanceDao noteMyImportanceDao) {
            this.noteMyImportanceDao = noteMyImportanceDao;
        }

        @Override
        protected Void doInBackground(NoteMyImportance... noteMyImportances) {
            noteMyImportanceDao.update(noteMyImportances[0]);

            return null;
        }
    }


    ////////////////////////////////////////////END UPDATE////////////////////////////////////////////////////

    public void delete(NoteMyImportance noteMyImportance) {
        new DeleteNoteMyImportanceAsyncTask(noteMyImportanceDao).execute(noteMyImportance);
    }

    private static class DeleteNoteMyImportanceAsyncTask extends AsyncTask<NoteMyImportance, Void, Void> {

        private NoteMyImportanceDao noteMyImportanceDao;

        public DeleteNoteMyImportanceAsyncTask(NoteMyImportanceDao noteMyImportanceDao) {
            this.noteMyImportanceDao = noteMyImportanceDao;
        }

        @Override
        protected Void doInBackground(NoteMyImportance... noteMyImportances) {
            noteMyImportanceDao.delete(noteMyImportances[0]);

            return null;
        }
    }

    ////////////////////////////////////////////END DELETE////////////////////////////////////////////////////

    public void deleteAllNotes() {
        new DeleteAllNoteMyImportanceAsyncTask(noteMyImportanceDao).execute();
    }

    public static class DeleteAllNoteMyImportanceAsyncTask extends AsyncTask<Void, Void, Void>{

        private NoteMyImportanceDao noteMyImportanceDao;

        public DeleteAllNoteMyImportanceAsyncTask(NoteMyImportanceDao noteMyImportanceDao) {
            this.noteMyImportanceDao = noteMyImportanceDao;
        }

        @Override
        protected Void doInBackground(Void... Void) {
            noteMyImportanceDao.deleteAllNoteMyImportance();

            return null;
        }
    }

    ////////////////////////////////////////////END DELETEALL////////////////////////////////////////////////////

    public LiveData<List<NoteMyImportance>> getAllnotemyimportance() {
        return allnotemyimportance;
    }

    ////////////////////////////////////////////END RETRIVEALL////////////////////////////////////////////////////
}
