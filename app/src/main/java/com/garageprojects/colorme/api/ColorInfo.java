package com.garageprojects.colorme.api;

import android.graphics.Color;

import androidx.palette.graphics.Palette;

import com.garageprojects.colorme.colorutil.ColorUtils;

public class ColorInfo {

    private String hex;
    private String name;
    private RGB rgb;
    private String requestedHex;
    private double luminance;
    private double distance;
    private int textColor;

    public ColorInfo() {
    }

    public ColorInfo(Palette.Swatch swatch) {
         this.requestedHex = ColorUtils.toHex(swatch.getRgb());
         this.textColor = swatch.getBodyTextColor();
    }

    public String getHex() {
        return hex;
    }

    public String getName() {
        return name;
    }

    public RGB getRgb() {
        return rgb;
    }

    public String getRequestedHex() {
        return requestedHex;
    }

    public double getLuminance() {
        return luminance;
    }

    public double getDistance() {
        return distance;
    }

    public int getTextColor() {
        if(textColor == 0)
            textColor =  ColorUtils.getTextColor(getColor());

        return textColor;
    }

    public  int  getColor(){
        return Color.parseColor(requestedHex);
    }


    private static class RGB{
        int r;
        int g;
        int b;
        int rgb;

        public RGB() {

        }

        public RGB(int rgb) {
             r = (rgb>>16)&0xFF;
             g = (rgb>>8)&0xFF;
             b = (rgb)&0xFF;
        }

        public int getR() {
            return r;
        }

        public int getG() {
            return g;
        }

        public int getB() {
            return b;
        }
    }
}
