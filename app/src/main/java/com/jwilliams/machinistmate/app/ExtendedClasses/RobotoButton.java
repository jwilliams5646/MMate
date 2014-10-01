package com.jwilliams.machinistmate.app.ExtendedClasses;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by john.williams on 6/4/2014.
 */
public class RobotoButton extends Button {
    public RobotoButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public RobotoButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RobotoButton(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            try {
                setTypeface(Typefaces.get(this.getContext(), "fonts/Roboto-Regular.ttf"));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
