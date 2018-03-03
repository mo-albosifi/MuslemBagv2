package ly.developer.mohammedalosifi1990.muslembagv2.data.enity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by mfrhr20 on 22/02/2018.
 */

@Entity(tableName = "LocationData")
public class LocationData {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private double latitude;

    @ColumnInfo
    private double lonitude;


    @ColumnInfo
    private String contryName;

    @ColumnInfo
    private String cityName;

    public LocationData() {
    }

    public LocationData(double latitude, double lonitude, String contryName, String cityName) {
        this.latitude = latitude;
        this.lonitude = lonitude;
        this.contryName = contryName;
        this.cityName = cityName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLonitude() {
        return lonitude;
    }

    public void setLonitude(double lonitude) {
        this.lonitude = lonitude;
    }

    public String getContryName() {
        return contryName;
    }

    public void setContryName(String contryName) {
        this.contryName = contryName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
