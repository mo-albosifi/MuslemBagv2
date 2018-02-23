package ly.developer.mohammedalosifi1990.muslembagv2.data.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import ly.developer.mohammedalosifi1990.muslembagv2.data.enity.LocationData;

/**
 * Created by mfrhr20 on 22/02/2018.
 */
@Dao
public interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(LocationData... locationData);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public void update(LocationData... locationData);

    @Delete
    public void delete(LocationData locationData);

    @Query("select * from LocationData")
    public LocationData getData();

    @Query("delete from LocationData")
    public void deleteAll();

}
