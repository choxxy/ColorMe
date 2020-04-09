package com.garageprojects.colorme;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.recyclerview.widget.RecyclerView;

import com.garageprojects.colorme.colorutil.ColorUtils;

import java.util.List;

public class ImageColorAdapter extends RecyclerView.Adapter<ImageColorAdapter.ImageViewHolder> {

    private Context context;
    private List<Integer> images;
    private LayoutInflater inflater;

    private final ColorUtils colorUtils;

    public ImageColorAdapter(Context context, List<Integer> colors) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        images = colors;
        colorUtils = new ColorUtils();

    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(inflater.inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        final int color = images.get(position);
        holder.slot.setBackgroundColor(color);
        holder.hexValue.setText(toHex(color));
        holder.hexValue.setTextColor(getContrastColor(color));
        String colorName = colorUtils.getColorNameFromRgb(toHex(color));
        if (TextUtils.isEmpty(colorName))
            holder.colorName.setVisibility(View.GONE);
        else {
            holder.colorName.setText(colorName);
            holder.colorName.setTextColor(getContrastColor(color));
        }
    }

    @Override
    public int getItemCount() {
        return images.size();
    }


    @ColorInt
    public static int getContrastColor(@ColorInt int color) {
        // Counting the perceptive luminance - human eye favors green color...
        double a = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        return a < 0.5 ? Color.BLACK : Color.WHITE;
    }

    //Displays hex representation of displayed color
    private String toHex(int color) {
        String hex = Integer.toHexString(color & 0xffffff);
        while (hex.length() < 6) {
            hex = "0" + hex;
        }
        hex = "#" + hex.toUpperCase();
        return hex;
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {

        LinearLayout slot;
        TextView hexValue;
        TextView colorName;

        public ImageViewHolder(View itemView) {
            super(itemView);
            slot = itemView.findViewById(R.id.color_slot);
            hexValue = itemView.findViewById(R.id.color_hex);
            colorName = itemView.findViewById(R.id.color_name);
        }
    }
}