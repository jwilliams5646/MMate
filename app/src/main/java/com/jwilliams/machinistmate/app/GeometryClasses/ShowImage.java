package com.jwilliams.machinistmate.app.GeometryClasses;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import com.jwilliams.machinistmate.app.R;
import com.squareup.picasso.Picasso;

/**
 * Created by john.williams on 9/10/2014.
 */
public class ShowImage extends Dialog {
    Context maContext;
    int drawable;

    public ShowImage(Context context, int drawable) {
        super(context);
        this.maContext = context;
        this.drawable = drawable;
        Log.d("Drawable: ", Integer.toString(drawable));
    }

    public void setDialog(){
        Dialog dialog = new Dialog(maContext);

        //No title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_image);

        dialog.setCancelable(true);

        ImageView img = (ImageView)dialog.findViewById(R.id.geometry_image);

        Picasso.with(maContext)
                .load(drawable)
                .fit()
                .centerInside()
                .into(img);

        dialog.show();

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });

    }
}
