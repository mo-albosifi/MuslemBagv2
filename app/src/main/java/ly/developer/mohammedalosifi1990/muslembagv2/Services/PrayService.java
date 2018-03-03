package ly.developer.mohammedalosifi1990.muslembagv2.Services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.UiThread;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import br.com.goncalves.pugnotification.notification.PugNotification;
import ly.developer.mohammedalosifi1990.muslembagv2.R;
import ly.developer.mohammedalosifi1990.muslembagv2.Utils.GPSTracker;
import ly.developer.mohammedalosifi1990.muslembagv2.Utils.PrayTime;
import ly.developer.mohammedalosifi1990.muslembagv2.data.AppDataBase;
import ly.developer.mohammedalosifi1990.muslembagv2.data.enity.LocationData;

@EService
public class PrayService extends Service {
    private static final String TAG = "MyLocationService";
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 1000;
    private static final float LOCATION_DISTANCE = 10f;
    LocationData locationData = new LocationData();

    public double latuide = 0.0, longtuide = 0.0;
    public String cityName = "", countryName = "";
    private AppDataBase dbContext = Room.databaseBuilder(this, AppDataBase.class, "AppDataBase").allowMainThreadQueries().build();
    LocationData ld;
    ArrayList<String> prayerTimes,prayerName;
    private class LocationListener implements android.location.LocationListener {
        Location mLastLocation;


        public LocationListener(String provider) {
            mLastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location) {
            mLastLocation.set(location);

            latuide = location.getLatitude();
            longtuide = location.getLongitude();

            toast(location.getLatitude() + "");
            toast(location.getLongitude() + "");

            Geocoder gcd = new Geocoder(PrayService.this, Locale.getDefault());
            List<Address> addresses = null;
            try {

                addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                if (addresses.size() > 0) {

                    countryName = addresses.get(0).getCountryName();

                    if (!addresses.get(0).getFeatureName().toLowerCase().contains("unnamed")) {
                        cityName = addresses.get(0).getFeatureName();
                    } else {
                        cityName = "غير معروف";
                    }


                }

                if (dbContext.getLocationDao().getData() == null) {
                    dbContext.getLocationDao().insert(new LocationData(latuide, longtuide, countryName, cityName));
                } else {
                    dbContext.getLocationDao().update(new LocationData(latuide, longtuide, countryName, cityName));
                }

                EventBus.getDefault().postSticky(dbContext.getLocationDao().getData());


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }

    int i = 0;
    public Calendar calendar;
    PrayTime prayers;

    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.NETWORK_PROVIDER),
            new LocationListener(LocationManager.GPS_PROVIDER)
            , new LocationListener(LocationManager.PASSIVE_PROVIDER)
    };


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        checkPray();
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {

        initializeLocationManager();


        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    LOCATION_INTERVAL,
                    LOCATION_DISTANCE,
                    mLocationListeners[0]
            );
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    LOCATION_INTERVAL,
                    LOCATION_DISTANCE,
                    mLocationListeners[1]
            );
            mLocationManager.requestLocationUpdates(
                    LocationManager.PASSIVE_PROVIDER,
                    LOCATION_INTERVAL,
                    LOCATION_DISTANCE,
                    mLocationListeners[2]
            );



        } catch (java.lang.SecurityException ex) {
        } catch (IllegalArgumentException ex) {
        }

    }

    private void initializeLocationManager() {
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    @Background
    public void checkPray() {

        while (true) {
            calendar = Calendar.getInstance();

            prayers = getPrayObj();

            prayers.setTimeFormat(prayers.Time12);
            prayers.setCalcMethod(prayers.Jafari);
            prayers.setAsrJuristic(prayers.Shafii);
            prayers.setAdjustHighLats(prayers.AngleBased);
            int[] offsets = {0, 0, 0, 0, 0, 0, 0}; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
            prayers.tune(offsets);


            ld = dbContext.getLocationDao().getData();
            prayerName=prayers.getTimeNames();

            prayerTimes = prayers.getPrayerTimes(calendar, ld.getLatitude(), ld.getLonitude(), getTimeZone());


            i = 0;
            for (i = 0; i < prayerTimes.size(); i++) {
                if (getInt(prayerTimes.get(i).substring(0, 2)) == calendar.get(Calendar.HOUR)
                        && getInt(prayerTimes.get(i).substring(3, 5)) == calendar.get(Calendar.MINUTE)
                        && getAMPM() == getAMPMFromPrayTimes(prayerTimes.get(i))) {
                    makeNotifation(prayerName.get(i), prayerTimes.get(i));
                }
            }


            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {

            }
        }
    }


    @UiThread
    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public double getTimeZone() {
        return TimeZone.getDefault().getOffset(new Date().getTime()) / 3600000.0;
    }


    public PrayTime getPrayObj() {
        if (prayers == null) {
            prayers = new PrayTime();
        }
        return prayers;
    }

    public int getInt(String text) {
        return Integer.parseInt(text);
    }

    public String getAMPM() {
        if (calendar.get(Calendar.AM_PM) == 0) {
            return "am";
        } else
            return "pm";
    }

    public String getAMPMFromPrayTimes(String time) {
        if (time.toLowerCase().contains("am")) {
            return "am";
        } else
            return "pm";

    }

    public void makeNotifation(String title, String message) {

        PugNotification.with(this)
                .load()
                .title(title)
                .message(message)
                .smallIcon(R.drawable.msjed)
                .largeIcon(R.drawable.msjed)
                .flags(Notification.DEFAULT_ALL)
                .color(R.color.colorPrimary)
//    .click(cctivity, bundle)
//    .dismiss(activity, bundle)
//    .ticker(ticker)
//    .when(when)
//    .vibrate(vibrate)
//    .lights(color, ledOnMs, ledOfMs)
//    .sound(sound)
                .autoCancel(true)
                .simple()
                .build();
    }
}

