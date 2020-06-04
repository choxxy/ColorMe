package com.garageprojects.colorme.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Colors {

    @SerializedName("colors")
    private List<ColorInfo> colors;

    public Colors() {
    }

    public List<ColorInfo> getColors() {
        return colors;
    }

    public void setColorInfoList(List<ColorInfo> colors) {
        this.colors = colors;
    }
}
