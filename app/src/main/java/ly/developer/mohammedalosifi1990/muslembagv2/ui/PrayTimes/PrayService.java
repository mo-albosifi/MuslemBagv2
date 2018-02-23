package ly.developer.mohammedalosifi1990.muslembagv2.ui.PrayTimes;

import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;

import ly.developer.mohammedalosifi1990.muslembagv2.R;

public class PrayService extends Service {
    public PrayService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {



        return super.onStartCommand(intent, flags, startId);
    }

    public void displayAlert(String prayName){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Test dialog");
        builder.setIcon(R.drawable.ic_arrow_left);
        builder.setMessage("Content");

        AlertDialog alert = builder.create();
        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alert.show();
    }
}
