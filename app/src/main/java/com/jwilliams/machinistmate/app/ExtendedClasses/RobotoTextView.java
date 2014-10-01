package com.jwilliams.machinistmate.app.ExtendedClasses;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by john.williams on 5/5/2014.
 */
public class RobotoTextView extends TextView {
    public RobotoTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public RobotoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RobotoTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            try {
                setTypeface(Typefaces.get(this.getContext(),"fonts/Roboto-Regular.ttf"));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
