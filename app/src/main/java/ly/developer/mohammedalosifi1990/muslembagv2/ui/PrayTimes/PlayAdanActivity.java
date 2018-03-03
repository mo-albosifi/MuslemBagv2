package ly.developer.mohammedalosifi1990.muslembagv2.ui.PrayTimes;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.io.IOException;

import ly.developer.mohammedalosifi1990.muslembagv2.R;

@EActivity(R.layout.activity_play_adan)
public class PlayAdanActivity extends AppCompatActivity {

    MediaPlayer mp;

    @AfterViews
    public void after() {
        mp = MediaPlayer.create(this, R.raw.azan_alfajer);
        mp.start();

     }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp.stop();
    }
}
