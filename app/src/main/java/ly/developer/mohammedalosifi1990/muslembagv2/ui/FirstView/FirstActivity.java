package ly.developer.mohammedalosifi1990.muslembagv2.ui.FirstView;

import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import ly.developer.mohammedalosifi1990.muslembagv2.R;
import ly.developer.mohammedalosifi1990.muslembagv2.base.BaseActivity;
import ly.developer.mohammedalosifi1990.muslembagv2.ui.MainActivity_;
import ly.developer.mohammedalosifi1990.muslembagv2.wedgit.CustomTextView;

@EActivity(R.layout.activity_first)
public class FirstActivity extends BaseActivity {

    @ViewById
    ExpandableLayout expandableLayout;
    @ViewById
    LinearLayout llQuran;
    @ViewById
    ImageView ivQuranDownArrow, ivNotFoundLcation;
    @ViewById
    CustomTextView tvNearPray;
    boolean expandState = true;// true is open
    SharedPreferences prefs = null;
    @Click
    public void llQuran() {
        if (expandState) {
            expandState = false;
            ivQuranDownArrow.setImageResource(R.drawable.ic_arrow_down);
        } else {
            expandState = true;
            ivQuranDownArrow.setImageResource(R.drawable.ic_arrow_up);
        }
        expandableLayout.toggle();
    }


    @UiThread
    @AfterViews
    protected void afterViews() {
        setTitle("حقيبة المسلم");
        prefs = getSharedPreferences("com.mycompany.myAppName", MODE_PRIVATE);

        if (prefs.getBoolean("firstrun", true)) {

            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            alertDialog.setTitle("مرحبــا");

            alertDialog.setMessage("هذا أول تشغيل لك للتطبيق .. أشكرك لأستخدامه ، وأرجو أن ينال رضاك وأستحساك .. وشكرا ");
            alertDialog.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
             alertDialog.show();

            prefs.edit().putBoolean("firstrun", false).commit();
        }


        if (dbContext.getLocationDao().getData() == null) {
            ivNotFoundLcation.setVisibility(View.VISIBLE);
            tvNearPray.setVisibility(View.GONE);
            makeToolip(ivNotFoundLcation, "لم تقم بضبط بيانات الموقع الخاص بك ... أظغـظ الأيقونة لبدء أستكشاف الموقع");
        }
    }

    @Click
    public void llPrayTimes() {
        Intent in = new Intent(FirstActivity.this, MainActivity_.class);
        in.putExtra("openType", 1);
        startActivity(in);
    }
    @Click
    public void llQuranListin() {
        Intent in = new Intent(FirstActivity.this, MainActivity_.class);
        in.putExtra("openType", 2);
        startActivity(in);
    }

    @Click
    public void ivNotFoundLcation() {
        llPrayTimes();
    }


}
