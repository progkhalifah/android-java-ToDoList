package com.progkhalifah.mytasks.pojo;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteMyImportanceDao {

    @Insert
    void insert(NoteMyImportance noteMyImportance);

    @Update
    void update(NoteMyImportance noteMyImportance);

    @Delete
    void delete(NoteMyImportance noteMyImportance);

    @Query("DELETE FROM notemyimportance_table")
    void deleteAllNoteMyImportance();

    @Query("SELECT * FROM notemyimportance_table ORDER BY timedate DESC")
    LiveData<List<NoteMyImportance>> getAllNoteMyImportance();


}
