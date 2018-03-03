package ly.developer.mohammedalosifi1990.muslembagv2.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ly.developer.mohammedalosifi1990.muslembagv2.data.Dao.LocationDao;
import ly.developer.mohammedalosifi1990.muslembagv2.data.Dao.PrayAAzanDao;
import ly.developer.mohammedalosifi1990.muslembagv2.data.enity.LocationData;
import ly.developer.mohammedalosifi1990.muslembagv2.data.enity.PrayForPlayAzan;

/**
 * Created by mfrhr20 on 22/02/2018.
 */

@Database(entities = {LocationData.class, PrayForPlayAzan.class},version = 1,exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    public abstract LocationDao getLocationDao();
    public abstract PrayAAzanDao getPrayAAzanDao();
}
