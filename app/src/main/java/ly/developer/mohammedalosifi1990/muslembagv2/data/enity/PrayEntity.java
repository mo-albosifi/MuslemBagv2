package ly.developer.mohammedalosifi1990.muslembagv2.data.enity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by pc on 26/02/18.
 */

@Entity(tableName = "PrayEntity")
public class PrayEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String prayEnglishName;
    @ColumnInfo
    private int prayHour;
    @ColumnInfo
    private int prayMints;
    @ColumnInfo
    private String amPm;
    @ColumnInfo
    private boolean soundState;
    @ColumnInfo
    private boolean isDone;

    public PrayEntity(String prayEnglishName, int prayHour, int prayMints, String amPm, boolean soundState, boolean isDone) {
        this.prayEnglishName = prayEnglishName;
         this.prayHour = prayHour;
        this.prayMints = prayMints;
        this.amPm = amPm;
        this.soundState = soundState;
        this.isDone = isDone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrayEnglishName() {
        return prayEnglishName;
    }

    public void setPrayEnglishName(String prayEnglishName) {
        this.prayEnglishName = prayEnglishName;
    }


    public int getPrayHour() {
        return prayHour;
    }

    public void setPrayHour(int prayHour) {
        this.prayHour = prayHour;
    }

    public int getPrayMints() {
        return prayMints;
    }

    public void setPrayMints(int prayMints) {
        this.prayMints = prayMints;
    }

    public String getAmPm() {
        return amPm;
    }

    public void setAmPm(String amPm) {
        this.amPm = amPm;
    }

    public boolean isSoundState() {
        return soundState;
    }

    public void setSoundState(boolean soundState) {
        this.soundState = soundState;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}


