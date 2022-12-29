package com.progkhalifah.mytasks.pojo;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notemyplan_table")
public class NoteMyPlan {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title ;
    private String description ;
    private String link ;
    private String timedate;

    public NoteMyPlan(String title, String description,String timedate) {
        this.title = title;
        this.description = description;
        this.timedate = timedate;
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
