package ly.developer.mohammedalosifi1990.muslembagv2.wedgit;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by mfrhr20 on 23/02/2018.
 */

public class CustomButton extends android.support.v7.widget.AppCompatButton {
    public CustomButton(Context context) {
        super(context);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/flat.ttf"));
    }

    public CustomButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/flat.ttf"));

    }

    public CustomButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/flat.ttf"));

    }
}
