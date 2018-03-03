package ly.developer.mohammedalosifi1990.muslembagv2.data.enity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by pc on 03/03/18.
 */
@Entity(tableName = "PrayForPlayAzan")
public class PrayForPlayAzan {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String prayName;

    @ColumnInfo
    private String prayTime;

    public PrayForPlayAzan(String prayName, String prayTime) {
        this.prayName = prayName;
        this.prayTime = prayTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrayName() {
        return prayName;
    }

    public void setPrayName(String prayName) {
        this.prayName = prayName;
    }

    public String getPrayTime() {
        return prayTime;
    }

    public void setPrayTime(String prayTime) {
        this.prayTime = prayTime;
    }
}
