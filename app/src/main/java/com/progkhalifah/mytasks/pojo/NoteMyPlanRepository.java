package com.progkhalifah.mytasks.pojo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteMyPlanRepository {

    private NoteMyPlanDao noteMyPlanDao;
    private LiveData<List<NoteMyPlan>> allnotemyplan;

    public NoteMyPlanRepository(Application application) {
        MyTasksDatabase database = MyTasksDatabase.getInstance(application);
        noteMyPlanDao = database.noteMyPlanDao();
        allnotemyplan = noteMyPlanDao.getAllNoteMyPlan();
    }

    public void insert(NoteMyPlan noteMyPlan){
        new InsertNoteMyPlanAsyncTask(noteMyPlanDao).execute(noteMyPlan);
    }

    private static class InsertNoteMyPlanAsyncTask extends AsyncTask<NoteMyPlan, Void, Void> {

        private NoteMyPlanDao noteMyPlanDao;

        public InsertNoteMyPlanAsyncTask(NoteMyPlanDao noteMyPlanDao) {
            this.noteMyPlanDao = noteMyPlanDao;
        }

        @Override
        protected Void doInBackground(NoteMyPlan... noteMyPlans) {
            noteMyPlanDao.insert(noteMyPlans[0]);

            return null;
        }
    }

    //////////////////////////////////////////END INSERT//////////////////////////////////////////////////////


    public void update(NoteMyPlan noteMyPlan){
        new UpdateNoteMyPlanAsyncTask(noteMyPlanDao).execute(noteMyPlan);
    }

    private static class UpdateNoteMyPlanAsyncTask extends AsyncTask<NoteMyPlan, Void, Void>{

        private NoteMyPlanDao noteMyPlanDao;

        public UpdateNoteMyPlanAsyncTask(NoteMyPlanDao noteMyPlanDao) {
            this.noteMyPlanDao = noteMyPlanDao;
        }

        @Override
        protected Void doInBackground(NoteMyPlan... noteMyPlans) {
            noteMyPlanDao.update(noteMyPlans[0]);

            return null;
        }
    }


    ////////////////////////////////////////////END UPDATE////////////////////////////////////////////////////

    public void delete(NoteMyPlan noteMyPlan) {
        new DeleteNoteMyPlanAsyncTask(noteMyPlanDao).execute(noteMyPlan);
    }

    private static class DeleteNoteMyPlanAsyncTask extends AsyncTask<NoteMyPlan, Void, Void> {

        private NoteMyPlanDao noteMyPlanDao;

        public DeleteNoteMyPlanAsyncTask(NoteMyPlanDao noteMyPlanDao) {
            this.noteMyPlanDao = noteMyPlanDao;
        }

        @Override
        protected Void doInBackground(NoteMyPlan... noteMyPlans) {
            noteMyPlanDao.delete(noteMyPlans[0]);

            return null;
        }
    }

    ////////////////////////////////////////////END DELETE////////////////////////////////////////////////////

    public void deleteAllNotes() {
        new DeleteAllNoteMyPlanAsyncTask(noteMyPlanDao).execute();
    }

    public static class DeleteAllNoteMyPlanAsyncTask extends AsyncTask<Void, Void, Void>{

        private NoteMyPlanDao noteMyPlanDao;

        public DeleteAllNoteMyPlanAsyncTask(NoteMyPlanDao noteMyImportanceDao) {
            this.noteMyPlanDao = noteMyImportanceDao;
        }

        @Override
        protected Void doInBackground(Void... Void) {
            noteMyPlanDao.deleteAllNoteMyPlan();

            return null;
        }
    }

    ////////////////////////////////////////////END DELETEALL////////////////////////////////////////////////////

    public LiveData<List<NoteMyPlan>> getAllnotemyplan() {
        return allnotemyplan;
    }

    ////////////////////////////////////////////END RETRIVEALL////////////////////////////////////////////////////

}
