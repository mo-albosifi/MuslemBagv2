package ly.developer.mohammedalosifi1990.muslembagv2.Application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import org.androidannotations.annotations.EApplication;

import ly.developer.mohammedalosifi1990.muslembagv2.Utils.AnimationUtil;
import ly.developer.mohammedalosifi1990.muslembagv2.Utils.Utility;


/**
 * Created by Mohammed_Albosifi on 23/11/2017.
 */
@EApplication
public class AppInstanse extends Application {

    AnimationUtil animationUtil;
    Utility utility;
    Context appContext;


    String prayName="",praTime="";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void clear(){
        animationUtil=null;
        utility=null;
        appContext=null;
    }



    public synchronized AnimationUtil getAnimationUtil() {
        if (animationUtil==null){
            animationUtil=new AnimationUtil();
        }
        return animationUtil;
    }

    public synchronized Utility getUtility() {
        if (utility==null){
            utility=new Utility();
        }
        return utility;
    }

    public String getPrayName() {
        return prayName;
    }

    public void setPrayName(String prayName) {
        this.prayName = prayName;
    }

    public String getPraTime() {
        return praTime;
    }

    public void setPraTime(String praTime) {
        this.praTime = praTime;
    }
}
