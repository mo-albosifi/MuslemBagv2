package ly.developer.mohammedalosifi1990.muslembagv2.base;

import android.app.Notification;
import android.app.PendingIntent;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tapadoo.alerter.Alerter;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;

import br.com.goncalves.pugnotification.notification.PugNotification;
import ly.developer.mohammedalosifi1990.muslembagv2.Application.AppInstanse;
import ly.developer.mohammedalosifi1990.muslembagv2.R;
import ly.developer.mohammedalosifi1990.muslembagv2.data.AppDataBase;

/**
 * Created by Mohammed_Albosifi on 23/10/17.
 */
@EFragment
public abstract class BaseFragment extends Fragment {

    protected AppDataBase dbContext;
    @App
    protected AppInstanse appInstanse;

    @AfterViews
    protected void onCreate() {
        dbContext = Room.databaseBuilder(getContext(), AppDataBase.class, "AppDataBase").allowMainThreadQueries().build();
    }


    @Override
    public void onDetach() {
        dbContext = null;
        appInstanse = null;
        super.onDetach();
    }


    @UiThread
    public void showToast(String... parms) {
        if (parms[0] != "") {
            switch (parms[1]) {
                case "e":
                    MDToast.makeText(getContext(), parms[0], Toast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
                    break;
                case "s":
                    MDToast.makeText(getContext(), parms[0], Toast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
                    break;
                case "i":
                    MDToast.makeText(getContext(), parms[0], Toast.LENGTH_LONG, MDToast.TYPE_INFO).show();
                    break;
                case "w":
                    MDToast.makeText(getContext(), parms[0], Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                    break;
                default:
                    MDToast.makeText(getContext(), parms[0], Toast.LENGTH_LONG).show();
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
        Alerter.create(getActivity())
                .setTitle(title)
                .setText(content)
                .setTextTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/flat.ttf"))
                .setBackgroundColorInt(getContext().getResources().getColor(R.color.colorPrimary))
                .setDuration(2000)
                .show();
    }

    public void makeNotifation(String title, String message, int smallIcon, int largeIcon, PendingIntent pendingIntent) {

        PugNotification.with(getContext())
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
