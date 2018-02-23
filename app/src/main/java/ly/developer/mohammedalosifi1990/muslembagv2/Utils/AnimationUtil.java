package ly.developer.mohammedalosifi1990.muslembagv2.Utils;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;

/**
 * Created by Mohammed_Albosifi on 29/09/17.
 */

public class AnimationUtil {

    public LayoutAnimationController getAnnimateForReyclerView(int height){
        AnimationSet set = new AnimationSet(true);

        // Fade in animation
        Animation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(500);
        fadeIn.setFillAfter(true);
        set.addAnimation(fadeIn);

        // Slide up animation from bottom of screen
        Animation slideUp = new TranslateAnimation(0, 0, height, 0);
        slideUp.setInterpolator(new DecelerateInterpolator(4.f));
        slideUp.setDuration(500);
        set.addAnimation(slideUp);

        // Set up the animation controller              (second parameter is the delay)
        return new LayoutAnimationController(set, 0.2f);

    }

    public AnimationUtil() {
    }
}
