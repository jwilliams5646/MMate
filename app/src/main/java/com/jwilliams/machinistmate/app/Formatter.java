package com.jwilliams.machinistmate.app;

import java.text.DecimalFormat;

/**
 * Created by John Williams on 5/22/2014.
 * Formatter can be used by all classes that wish to use it.
 * It takes your answer and sends back the string you can directly insert into a view that takes text.
 */
public class Formatter {

    public static String formatOutput(double answer, int precision) {
        DecimalFormat df;
        String formats = "##.";
        if (precision > 0) {
            for (int x = 0; x < precision; x++) {
                formats = formats + "#";
            }
            df = new DecimalFormat(formats);
            return df.format(answer);
        } else {
            formats = "##";
            df = new DecimalFormat(formats);
            return df.format(answer);
        }
    }
}
