package ly.developer.mohammedalosifi1990.muslembagv2.base;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tapadoo.alerter.Alerter;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import br.com.goncalves.pugnotification.notification.PugNotification;
import ly.developer.mohammedalosifi1990.muslembagv2.Application.AppInstanse;
import ly.developer.mohammedalosifi1990.muslembagv2.R;

/**
 * Created by Mohammed_Albosifi on 22/10/17.
 */

@EActivity
public abstract class BaseActivity extends AppCompatActivity {


    @App
    protected AppInstanse appInstanse;

    @AfterViews
    protected void onCreate() {
    }

    @Override
    protected void onDestroy() {

        appInstanse = null;
        super.onDestroy();
    }


    @UiThread
    public void showToast(String... parms) {
        if (parms[0] != "") {
            switch (parms[1]) {
                case "e":
                    MDToast.makeText(this, parms[0], Toast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
                    break;
                case "s":
                    MDToast.makeText(this, parms[0], Toast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
                    break;
                case "i":
                    MDToast.makeText(this, parms[0], Toast.LENGTH_LONG, MDToast.TYPE_INFO).show();
                    break;
                case "w":
                    MDToast.makeText(this, parms[0], Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                    break;
                default:
                    MDToast.makeText(this, parms[0], Toast.LENGTH_LONG).show();
            }
        }
    }

    public void showSnakBar(View view, String message) {
        if (message != null) {
            {
                Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        }
    }

    public void makeAlerter(String title, String content) {
        Alerter.create(this)
                .setTitle(title)
                .setText(content)
                .setDuration(2)
                .show();
    }

    public void makeNotifation(String title, String message, int smallIcon, int largeIcon, PendingIntent pendingIntent) {

        PugNotification.with(this)
                .load()
                .title(title)
                .message(message)
                .smallIcon(smallIcon)
                .largeIcon(largeIcon)
                .flags(Notification.DEFAULT_ALL)
                .button(R.drawable.alerter_ic_notifications, "فتح", pendingIntent)
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


    public void showAlertDialog(String title,String message,DialogInterface.OnClickListener pos,DialogInterface.OnClickListener neg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle(title);

        alertDialog.setMessage(message);
        alertDialog.setPositiveButton("موافق",pos);
        alertDialog.setNegativeButton("الغـاء",neg);
        alertDialog.show();
    }


    public int getInt(String val) {
        return Integer.parseInt(val);
    }

    public String getStr(int val) {
        return String.valueOf(val);
    }

    public boolean isEmpty(EditText myeditText) {
        return myeditText.getText().toString().trim().length() == 0;
    }

    public boolean isEmpty(AutoCompleteTextView myAutoCompleteTextView) {
        return myAutoCompleteTextView.getText().toString().trim().length() == 0;
    }

    public boolean isEmpty(TextView myTextView) {
        return myTextView.getText().toString().trim().length() == 0;
    }


}
