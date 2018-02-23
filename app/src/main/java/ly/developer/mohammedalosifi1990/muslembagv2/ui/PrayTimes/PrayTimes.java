package ly.developer.mohammedalosifi1990.muslembagv2.ui.PrayTimes;


import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;

import com.wang.avi.AVLoadingIndicatorView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import ly.developer.mohammedalosifi1990.muslembagv2.Utils.GPSTracker;
import ly.developer.mohammedalosifi1990.muslembagv2.R;
import ly.developer.mohammedalosifi1990.muslembagv2.Utils.PrayTime;
import ly.developer.mohammedalosifi1990.muslembagv2.base.BaseFragment;
import ly.developer.mohammedalosifi1990.muslembagv2.data.enity.LocationData;
import ly.developer.mohammedalosifi1990.muslembagv2.wedgit.CustomButton;
import ly.developer.mohammedalosifi1990.muslembagv2.wedgit.CustomTextView;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_pray_times)
public class PrayTimes extends BaseFragment {

    GPSTracker gps;
    Calendar calendar;

    @ViewById
    CustomTextView tvDate, tvLocation,tvNotFound;
    @ViewById
    LinearLayout llNotFound, llData;
    @ViewById
    CustomButton btnGetLocation;
    @ViewById
    AVLoadingIndicatorView avi;

    double latitude,longitude;
    @AfterViews
    public void afterViews() {

        calendar = Calendar.getInstance();
        setDate();
        LocationData locationData = dbContext.getLocationDao().getData();
        if (locationData != null) {
            tvLocation.setVisibility(View.VISIBLE);
            tvLocation.setText(locationData.getContryName() + " - " + locationData.getCityName());
        } else {
            tvNotFound.setText("الرجــاء ضبط بيانات الموقع الخاصة بك ... ليتمكن التطبيق من تنبيهك في أوقات الصلاة");
            llData.setVisibility(View.GONE);
            llNotFound.setVisibility(View.VISIBLE);
        }
    }

    @Click
    public void ivLeft() {
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        setDate();
    }

    @Click
    public void ivRight() {
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        setDate();
    }

    private void setDate() {
        tvDate.setText(getDayName(calendar.get(Calendar.DAY_OF_WEEK)) + "   " +
                calendar.get(Calendar.DAY_OF_MONTH) + " / " +
                calendar.get(Calendar.MONTH)
                + " / " + calendar.get(Calendar.YEAR));
    }

    @Click
    public void btnGetLocation() {
        btnGetLocation.setVisibility(View.GONE);
        avi.setVisibility(View.VISIBLE);
        tvNotFound.setText("يتم الأن محاولة الحصول علي بيانات الموقع الخاصة بكـ");
        getLocationData();
    }

    @Click
    public void ivReload(){
        btnGetLocation.setVisibility(View.GONE);
        avi.setVisibility(View.VISIBLE);
        llData.setVisibility(View.GONE);
        llNotFound.setVisibility(View.VISIBLE);
        tvNotFound.setText("يتم الأن محاولة الحصول علي بيانات الموقع الخاصة بكـ");
        getLocationData();
    }

    public void getLocationData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gps = new GPSTracker(getContext());

                // check if GPS enabled
                if (gps.canGetLocation()) {

                     latitude = gps.getLatitude();
                     longitude = gps.getLongitude();


                    Geocoder gcd = new Geocoder(getContext(), Locale.getDefault());
                    List<Address> addresses = null;
                    try {
                        addresses = gcd.getFromLocation(latitude, longitude, 1);
                        if (addresses.size() > 0) {

                            dbContext.getLocationDao().deleteAll();
                            String cityName = "";
//                    tvDate.append(addresses.get(0).getCountryCode()+"\n");
                            tvLocation.setText("البلد : "+addresses.get(0).getCountryName()+" - ");
                            if (!addresses.get(0).getFeatureName().toLowerCase().contains("unnamed")) {
                                tvLocation.append(" - " + addresses.get(0).getFeatureName());
                                cityName = addresses.get(0).getFeatureName();
                            } else {
                                cityName = "غير معروف";
                                tvLocation.append("المدينة : "+cityName);
                            }
                            llNotFound.setVisibility(View.GONE);
                            llData.setVisibility(View.VISIBLE);
                            dbContext.getLocationDao().insert(new LocationData(latitude, longitude, addresses.get(0).getCountryName(), cityName));
                            calcPrayTimes();
                        } else {
                            LocationData locationData=dbContext.getLocationDao().getData();
                         if (locationData!=null){
                             tvLocation.setText(""+locationData.getContryName() +" - "+"المدينة :"+locationData.getCityName());
                             llNotFound.setVisibility(View.GONE);
                             llData.setVisibility(View.VISIBLE);
                             calcPrayTimes();
                         }else {
                             tvNotFound.setText("لم يمكن النطبيق من أيحاد بيانت الموقع الهاص بكـ ... الرجاء أعادة المحاولة ");
                             llNotFound.setVisibility(View.VISIBLE);
                             llData.setVisibility(View.GONE);
                             btnGetLocation.setVisibility(View.VISIBLE);
                             avi.setVisibility(View.GONE);
                         }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    gps.showSettingsAlert();
                }
            }
        },3000);
    }

    public void calcPrayTimes(){



        TimeZone tz = TimeZone.getDefault();
        System.out.println("TimeZone   "+tz.getDisplayName(false, TimeZone.SHORT)+" Timezon id :: " +tz.getID());

//        Output:

//        TimeZone GMT+09:30 Timezon id :: Australia/Darwin


        double timezone = 2;
        // Test Prayer times here
        PrayTime prayers = new PrayTime();

        prayers.setTimeFormat(prayers.Time12);
        prayers.setCalcMethod(prayers.Jafari);
        prayers.setAsrJuristic(prayers.Shafii);
        prayers.setAdjustHighLats(prayers.AngleBased);
        int[] offsets = {0, 0, 0, 0, 0, 0, 0}; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
        prayers.tune(offsets);



        ArrayList<String> prayerTimes = prayers.getPrayerTimes(calendar,latitude, longitude, timezone);
        ArrayList<String> prayerNames = prayers.getTimeNames();

        for (int i = 0; i < prayerTimes.size(); i++) {
            tvLocation.append(prayerNames.get(i) + " - " + prayerTimes.get(i));
        }

    }

    private String getDayName(int val) {
        String dayName = "";
        switch (val) {
            case Calendar.SATURDAY:
                dayName = "السبت";
                break;
            case Calendar.SUNDAY:
                dayName = "الأحد";
                break;
            case Calendar.MONDAY:
                dayName = "الأثنين";
                break;
            case Calendar.TUESDAY:
                dayName = "التـلاتاء";
                break;
            case Calendar.WEDNESDAY:
                dayName = "الأربعاء";
                break;
            case Calendar.THURSDAY:
                dayName = "الخميس";
                break;
            case Calendar.FRIDAY:
                dayName = "الجمعة";
                break;
        }
        return dayName;
    }

}
