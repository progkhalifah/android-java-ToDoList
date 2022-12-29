package com.progkhalifah.mytasks.pojo;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteMyPlanDao {

    @Insert
    void insert(NoteMyPlan noteMyPlan);

    @Update
    void update(NoteMyPlan noteMyPlan);

    @Delete
    void delete(NoteMyPlan noteMyPlan);

    @Query("DELETE FROM notemyplan_table")
    void deleteAllNoteMyPlan();

    @Query("SELECT * FROM notemyplan_table ORDER BY timedate DESC")
    LiveData<List<NoteMyPlan>> getAllNoteMyPlan();



}
