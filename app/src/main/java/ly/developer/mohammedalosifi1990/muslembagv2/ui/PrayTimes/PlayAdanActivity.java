package ly.developer.mohammedalosifi1990.muslembagv2.ui.PrayTimes;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import ly.developer.mohammedalosifi1990.muslembagv2.Application.AppInstanse;
import ly.developer.mohammedalosifi1990.muslembagv2.R;
import ly.developer.mohammedalosifi1990.muslembagv2.Services.PrayService;
import ly.developer.mohammedalosifi1990.muslembagv2.Services.PrayService_;
import ly.developer.mohammedalosifi1990.muslembagv2.Utils.PrayTime;
import ly.developer.mohammedalosifi1990.muslembagv2.base.BaseActivity;
import ly.developer.mohammedalosifi1990.muslembagv2.data.enity.LocationData;
import ly.developer.mohammedalosifi1990.muslembagv2.wedgit.CustomTextView;

@EActivity(R.layout.activity_play_adan)
public class PlayAdanActivity extends BaseActivity {

    MediaPlayer mp;

    @ViewById
    CustomTextView txt;
    @AfterViews
    public void after() {



        txt.setText("");
        txt.append("\n"+dbContext.getPrayAAzanDao().getData().getPrayName());
        txt.append("\n"+dbContext.getPrayAAzanDao().getData().getPrayTime());

        mp = MediaPlayer.create(this, R.raw.azan_alfajer);

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mp.stop();
                makeNotifation("sss","ssss",R.drawable.msjed,R.drawable.msjed,null);
            }
        });

        mp.start();

     }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp.stop();
    }
}
