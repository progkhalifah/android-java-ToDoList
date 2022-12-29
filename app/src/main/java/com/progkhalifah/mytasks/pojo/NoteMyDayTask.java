package com.progkhalifah.mytasks.pojo;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notemyday_table")
public class NoteMyDayTask {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String description;
    private String link;
    private String timedate;

    public NoteMyDayTask(String title, String description, String timedate) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.timedate = timedate;
    }

    @Ignore
    public NoteMyDayTask(String title, String timedate) {
        this.title = title;
        this.timedate = timedate;
    }

    @Ignore
    public NoteMyDayTask() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTimedate() {
        return timedate;
    }

    public void setTimedate(String timedate) {
        this.timedate = timedate;
    }
}
