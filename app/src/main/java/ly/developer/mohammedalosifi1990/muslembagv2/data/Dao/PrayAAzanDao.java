package ly.developer.mohammedalosifi1990.muslembagv2.data.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import ly.developer.mohammedalosifi1990.muslembagv2.data.enity.LocationData;
import ly.developer.mohammedalosifi1990.muslembagv2.data.enity.PrayForPlayAzan;

/**
 * Created by pc on 03/03/18.
 */
@Dao
public interface PrayAAzanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(PrayForPlayAzan prayForPlayAzan);


    @Delete
    public void delete(PrayForPlayAzan prayForPlayAzan);

    @Query("select * from PrayForPlayAzan")
    public PrayForPlayAzan getData();

    @Query("delete from PrayForPlayAzan")
    public void deleteAll();
}
