package com.jwilliams.machinistmate.app.ExtendedClasses;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by john.williams on 5/5/2014.
 */
public class RobotoBoldTextView  extends TextView {
    public RobotoBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public RobotoBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RobotoBoldTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {

            try {
                //Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Black.ttf");
                //setTypeface(tf);
                setTypeface(Typefaces.get(this.getContext(), "fonts/Roboto-Black.ttf"));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
