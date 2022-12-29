package com.progkhalifah.mytasks.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.progkhalifah.mytasks.pojo.NoteMyDayTask;
import com.progkhalifah.mytasks.pojo.NoteMyDayTaskRepository;
import com.progkhalifah.mytasks.pojo.NoteMyImportance;
import com.progkhalifah.mytasks.pojo.NoteMyImportanceRepository;
import com.progkhalifah.mytasks.pojo.NoteMyPlan;
import com.progkhalifah.mytasks.pojo.NoteMyPlanRepository;

import java.util.List;

public class MyTaskViewModel extends AndroidViewModel {

    private NoteMyDayTaskRepository myDayTaskRepository;
    private LiveData<List<NoteMyDayTask>> allnotesmydaytasks;
    //////////////////////////End of MyDayTaskRepository///////////////////////////////////////
    private NoteMyImportanceRepository myImportanceRepository;
    private LiveData<List<NoteMyImportance>> allnotesmyimportance;
    //////////////////////////End of MyImportanceRepository///////////////////////////////////////
    private NoteMyPlanRepository myPlanRepository;
    private LiveData<List<NoteMyPlan>> allnotesmyplan;
    //////////////////////////End of MyDayTaskRepository///////////////////////////////////////


    public MyTaskViewModel(@NonNull Application application) {
        super(application);
        myDayTaskRepository = new NoteMyDayTaskRepository(application);
        allnotesmydaytasks = myDayTaskRepository.getAllnotemydaytasks();
        //////////////////////////End of MyDayTaskRepository///////////////////////////////////////
        myImportanceRepository = new NoteMyImportanceRepository(application);
        allnotesmyimportance = myImportanceRepository.getAllnotemyimportance();
        //////////////////////////End of MyDayTaskRepository///////////////////////////////////////
        myPlanRepository = new NoteMyPlanRepository(application);
        allnotesmyplan = myPlanRepository.getAllnotemyplan();
        //////////////////////////End of MyDayTaskRepository///////////////////////////////////////
    }

    public void insertMyDayTask(NoteMyDayTask noteMyDayTask){
        myDayTaskRepository.insert(noteMyDayTask);
    }

    public void updateMyDayTask(NoteMyDayTask noteMyDayTask) {
        myDayTaskRepository.update(noteMyDayTask);
    }

    public void deleteMyDayTask(NoteMyDayTask noteMyDayTask) {
        myDayTaskRepository.delete(noteMyDayTask);
    }

    public void deleteAllNotesMyDayTask() {
        myDayTaskRepository.deleteAllNotes();
    }

    public LiveData<List<NoteMyDayTask>> getAllnotesmydaytasks() {
        return allnotesmydaytasks;
    }
    //////////////////////////End of MyDayTaskRepository///////////////////////////////////////


    public void insertMyImportanceTask(NoteMyImportance noteMyImportance){
        myImportanceRepository.insert(noteMyImportance);
    }

    public void updateMyImportance(NoteMyImportance noteMyImportance) {
        myImportanceRepository.update(noteMyImportance);
    }

    public void deleteMyImportance(NoteMyImportance noteMyImportance) {
        myImportanceRepository.delete(noteMyImportance);
    }

    public void deleteAllNotesMyImportance() {
        myImportanceRepository.deleteAllNotes();
    }

    public LiveData<List<NoteMyImportance>> getAllnotesmyimportance() {
        return allnotesmyimportance;
    }

    //////////////////////////End of MyDayTaskRepository///////////////////////////////////////
    public void insertMyPlanTask(NoteMyPlan noteMyPlan){
        myPlanRepository.insert(noteMyPlan);
    }

    public void updateMyPlan(NoteMyPlan noteMyPlan) {
        myPlanRepository.update(noteMyPlan);
    }

    public void deleteMyPlan(NoteMyPlan noteMyPlan) {
        myPlanRepository.delete(noteMyPlan);
    }

    public void deleteAllNotesMyPlan() {
        myPlanRepository.deleteAllNotes();
    }

    public LiveData<List<NoteMyPlan>> getAllnotesmyPlan() {
        return allnotesmyplan;
    }


    //////////////////////////End of MyDayTaskRepository///////////////////////////////////////




}
