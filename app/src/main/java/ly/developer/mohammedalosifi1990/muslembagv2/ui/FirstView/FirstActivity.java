package ly.developer.mohammedalosifi1990.muslembagv2.ui.FirstView;

import android.content.Intent;

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

        if (dbContext.getLocationDao().getData() == null) {
            ivNotFoundLcation.setVisibility(View.VISIBLE);
            tvNearPray.setVisibility(View.GONE);
            makeToolip(ivNotFoundLcation, "لم تقم بضبط بيانات الموقع الخاص بك ... أظغـظ الأيقونة لبدء أستكشاف الموقع");
        }
    }

    @Click
    public void llPrayTimes() {
        Intent in = new Intent(FirstActivity.this, MainActivity_.class);
        in.putExtra("openType", "pray");
        startActivity(in);
    }

    @Click
    public void ivNotFoundLcation() {
        llPrayTimes();
    }


}
