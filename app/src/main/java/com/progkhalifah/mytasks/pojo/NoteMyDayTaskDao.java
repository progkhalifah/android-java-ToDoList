package com.progkhalifah.mytasks.pojo;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.progkhalifah.mytasks.MydaysTasks;

import java.util.List;

@Dao
public interface NoteMyDayTaskDao {

    @Insert
    void insert(NoteMyDayTask noteMyDayTask);

    @Update
    void update(NoteMyDayTask noteMyDayTask);

    @Delete
    void delete(NoteMyDayTask noteMyDayTask);

    @Query("DELETE FROM notemyday_table")
    void deleteAllNoteMydaytask();

    @Query("SELECT * FROM notemyday_table ORDER BY timedate DESC")
    LiveData<List<NoteMyDayTask>> getAllNoteMyDayTask();


}
