package ly.developer.mohammedalosifi1990.muslembagv2.ui.QuranListen;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import ly.developer.mohammedalosifi1990.muslembagv2.R;
import ly.developer.mohammedalosifi1990.muslembagv2.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_quran_listen)
public class QuranListenFragment extends BaseFragment {
    public static QuranListenFragment instant;

    @ViewById
    RecyclerView rvQranList;


    @AfterViews
    public void after() {
        rvQranList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvQranList.setHasFixedSize(true);
        rvQranList.setAdapter(new QuranAdapter(getContext()));
    }

    public static QuranListenFragment newInstance() {
        if (instant == null) {
            instant = new QuranListenFragment();
        }
        return instant;
    }

    public void onEvent(String s) {
        Toast.makeText(getContext(), "sdl", Toast.LENGTH_SHORT).show();
    }
}
