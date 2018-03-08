package ly.developer.mohammedalosifi1990.muslembagv2.ui.PrayTimes;


import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.wang.avi.AVLoadingIndicatorView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import ly.developer.mohammedalosifi1990.muslembagv2.Services.PrayService;
import ly.developer.mohammedalosifi1990.muslembagv2.Services.PrayService_;
import ly.developer.mohammedalosifi1990.muslembagv2.Utils.GPSTracker;
import ly.developer.mohammedalosifi1990.muslembagv2.R;
import ly.developer.mohammedalosifi1990.muslembagv2.Utils.PrayTime;
import ly.developer.mohammedalosifi1990.muslembagv2.base.BaseFragment;
import ly.developer.mohammedalosifi1990.muslembagv2.data.Dao.LocationDao;
import ly.developer.mohammedalosifi1990.muslembagv2.data.enity.LocationData;
import ly.developer.mohammedalosifi1990.muslembagv2.wedgit.CustomButton;
import ly.developer.mohammedalosifi1990.muslembagv2.wedgit.CustomTextView;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_pray_times)
public class PrayTimes extends BaseFragment {

    Calendar calendar;
    PrayTime prayers;
    ArrayList<String> prayerTimes;
    @ViewById
    CustomTextView tvDate, tvLocation, tvNotFound;
    @ViewById
    CustomTextView tvPrayTime, tvPrayTime2, tvPrayTime3, tvPrayTime4, tvPrayTime5;
    @ViewById
    LinearLayout llNotFound;
    @ViewById
    ScrollView llPrayList;
    @ViewById
    ProgressBar pbLoad;
    @ViewById
    CustomButton btnGetLocation;
    @ViewById
    AVLoadingIndicatorView avi;

    double latitude, longitude;

    @AfterViews
    public void afterViews() {


        calendar = Calendar.getInstance();
        setDate();
        LocationData locationData = dbContext.getLocationDao().getData();
        if (locationData != null) {
            if (locationData.getContryName().length()<=0) {
                tvLocation.setVisibility(View.GONE);
            } else {
                tvLocation.setVisibility(View.VISIBLE);
                tvLocation.setText(locationData.getContryName() + " - المدينه :" + locationData.getCityName());
            }
            calcPrayTimes();
            llNotFound.setVisibility(View.GONE);
            llPrayList.setVisibility(View.GONE);
        } else {
            tvNotFound.setText("الرجــاء ضبط بيانات الموقع الخاصة بك ... ليتمكن التطبيق من تنبيهك في أوقات الصلاة");
            tvLocation.setText("الموقع : غير معروف");
            tvLocation.setVisibility(View.GONE);
            llPrayList.setVisibility(View.GONE);
            llNotFound.setVisibility(View.VISIBLE);
        }
    }

    @Click
    public void ivLeft() {
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        setDate();
        if (llNotFound.getVisibility() == View.GONE) {
            calcPrayTimes();
        }
    }

    @Click
    public void ivRight() {
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        setDate();
        if (llNotFound.getVisibility() == View.GONE) {
            calcPrayTimes();
        }
    }

    private void setDate() {
        tvDate.setText(getDayName(calendar.get(Calendar.DAY_OF_WEEK)) + "   " +
                calendar.get(Calendar.YEAR) + " - " +
                (calendar.get(Calendar.MONTH) + 1)
                + " - " + calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Click
    public void btnGetLocation() {
        btnGetLocation.setVisibility(View.GONE);
        avi.setVisibility(View.VISIBLE);
        tvNotFound.setText("جاري الحصول علي بيانات الموقع الخاصة بكـ..");
        if (!utility.isServiceRunning(getContext(), PrayService_.class)) {
            getActivity().startService(new Intent(getContext(), PrayService_.class));
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dbContext.getLocationDao().getData() != null) {
                    calcPrayTimes();
                } else {
                    llPrayList.setVisibility(View.GONE);
                    avi.setVisibility(View.GONE);
                    btnGetLocation.setVisibility(View.VISIBLE);
                    llNotFound.setVisibility(View.VISIBLE);

                }
            }
        }, 3000);
    }



    public void calcPrayTimes() {
        if (!utility.isServiceRunning(getContext(), PrayService_.class))
        {
            getActivity().startService(new Intent(getContext(), PrayService_.class));
        }

        llPrayList.setVisibility(View.GONE);
        llNotFound.setVisibility(View.GONE);
        pbLoad.setVisibility(View.VISIBLE);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                prayers = new PrayTime();

                prayers.setTimeFormat(prayers.Time12);
                prayers.setCalcMethod(prayers.Jafari);
                prayers.setAsrJuristic(prayers.Shafii);
                prayers.setAdjustHighLats(prayers.AngleBased);
                int[] offsets = {0, 0, 0, 0, 0, 0, 0}; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
                prayers.tune(offsets);


                LocationData ld = dbContext.getLocationDao().getData();
                prayerTimes = prayers.getPrayerTimes(calendar, ld.getLatitude(), ld.getLonitude(), getTimeZone());

                tvPrayTime.setText(prayerTimes.get(0).substring(0, 5));
                tvPrayTime2.setText(prayerTimes.get(2).substring(0, 5));
                tvPrayTime3.setText(prayerTimes.get(3).substring(0, 5));
                tvPrayTime4.setText(prayerTimes.get(5).substring(0, 5));
                tvPrayTime5.setText(prayerTimes.get(6).substring(0, 5));


                pbLoad.setVisibility(View.GONE);
                llPrayList.setVisibility(View.VISIBLE);
            }
        }, 500);
    }

    public double getTimeZone() {
        return TimeZone.getDefault().getOffset(new Date().getTime()) / 3600000.0;
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



/*
    public void run() {
        gps = new GPSTracker(getContext());
        String contryName = "";
        String cityName = "";
        if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            if (latitude != 0.0 && longitude != 0.0) {


                Geocoder gcd = new Geocoder(getContext(), Locale.getDefault());
                List<Address> addresses = null;
                try {

                    addresses = gcd.getFromLocation(latitude, longitude, 1);
                    if (addresses.size() > 0) {

                        contryName = addresses.get(0).getCountryName();
                        cityName = "";
                        tvLocation.setText("البلد " + contryName + " - ");
                        if (!addresses.get(0).getFeatureName().toLowerCase().contains("unnamed")) {
                            tvLocation.append(" - " + addresses.get(0).getFeatureName());
                            cityName = addresses.get(0).getFeatureName();
                        } else {
                            cityName = "غير معروف";
                            tvLocation.append("المدينة " + cityName);
                        }
                        tvLocation.setVisibility(View.VISIBLE);
                        llNotFound.setVisibility(View.GONE);

                    } else {

                        LocationData locationData = dbContext.getLocationDao().getData();
                        if (locationData != null) {
                            tvLocation.setText("" + locationData.getContryName() + " - " + "المدينة :" + locationData.getCityName());
                            llNotFound.setVisibility(View.GONE);
                            tvLocation.setVisibility(View.VISIBLE);
//
                        } else {
                            tvLocation.setVisibility(View.GONE);
                        }
                    }
                } catch (IOException e) {
                    LocationData locationData = dbContext.getLocationDao().getData();
                    if (locationData != null) {

                        if (locationData.getContryName().length()<=0){
                            tvLocation.setVisibility(View.GONE);
                        }else {
                            tvLocation.setText("" + locationData.getContryName() + " - " + "المدينة :" + locationData.getCityName());
                            tvLocation.setVisibility(View.VISIBLE);
                        }
                        llNotFound.setVisibility(View.GONE);
                        calcPrayTimes();
                    } else {
                        tvLocation.setVisibility(View.GONE);
                        tvNotFound.setText("لم يمكن النطبيق من أيحاد بيانت الموقع الخاص بكـ ... الرجاء أعادة المحاولة ");
                        llNotFound.setVisibility(View.VISIBLE);
                        btnGetLocation.setVisibility(View.VISIBLE);
                        avi.setVisibility(View.GONE);
                    }
                }
                dbContext.getLocationDao().deleteAll();
                dbContext.getLocationDao().insert(new LocationData(latitude, longitude, (contryName.length()<=0)?"":"البلد :"+contryName, (cityName.length()<=0)?"":" - المدينه"+ cityName));
                calcPrayTimes();


            } else {
                if (dbContext.getLocationDao().getData()!=null){
                    calcPrayTimes();
                }else {
                    tvLocation.setVisibility(View.GONE);
                    tvNotFound.setText("لم يمكن النطبيق من أيحاد بيانت الموقع الخاص بكـ ... الرجاء أعادة المحاولة ");
                    llNotFound.setVisibility(View.VISIBLE);
                    btnGetLocation.setVisibility(View.VISIBLE);
                    avi.setVisibility(View.GONE);
                }
            }

        } else {
            gps.showSettingsAlert();
        }
    }
}
*/

}
