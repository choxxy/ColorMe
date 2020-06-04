package com.garageprojects.colorme.colorutil;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.RadialGradient;
import android.graphics.Shader;

import androidx.annotation.ColorInt;
import androidx.palette.graphics.Palette;
import com.garageprojects.colorme.api.ColorInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ColorUtils {

    private static Random mRandom = new Random();

    /**
     * Calculates and returns the text color based on the background color
     */
    @ColorInt
    public static int getTextColor(@ColorInt int color) {
        double a = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        return a < 0.5 ? Color.BLACK : Color.WHITE;
    }

    //Displays hex representation of displayed color
    public static String toHex(int color) {
        String hex = Integer.toHexString(color & 0xffffff);
        while (hex.length() < 6) {
            hex = "0" + hex;
        }
        hex = "#" + hex.toUpperCase();
        return hex;
    }

    public static List<ColorInfo> mapColor(List<Palette.Swatch> swatches) {
        List<ColorInfo> colorInfoList = new ArrayList<>();
        for(Palette.Swatch swatch: swatches){
            ColorInfo  info = new ColorInfo(swatch);
            colorInfoList.add(info);
        }
        return colorInfoList;
    }

    //Original source => https://android--examples.blogspot.com/2015/11/android-how-to-apply-textview-text.html
    public static RadialGradient getRandomRadialGradient(Context context, Point size){

        RadialGradient gradient = new RadialGradient(
                mRandom.nextInt(size.x),
                mRandom.nextInt(size.y),
                mRandom.nextInt(size.x),
                getMatColor(context),
                null, // Stops position is undefined
                Shader.TileMode.CLAMP // Shader tiling mode

        );
        // Return the RadialGradient
        return gradient;
    }


    private  static int [] getMatColor(Context context)
    {
        int  [] colorArray = new int [5];
        int arrayId = context.getResources().getIdentifier("mdcolor_500", "array", context.getPackageName());

        if (arrayId != 0)
        {
            TypedArray colors = context.getResources().obtainTypedArray(arrayId);
            for(int i = 0 ; i < colorArray.length; i++) {
                int index = (int) (Math.random() * colors.length());
                colorArray[i] = colors.getColor(index, Color.BLACK);
            }
            colors.recycle();
        }
        return colorArray;
    }



}