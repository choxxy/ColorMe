package com.garageprojects.colorme.ui.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.garageprojects.colorme.R;
import com.garageprojects.colorme.api.ColorInfo;
import com.garageprojects.colorme.colorutil.ColorUtils;
import com.garageprojects.colorme.ui.callbacks.OnListItemSelected;

import java.util.ArrayList;
import java.util.List;

public class ImageColorAdapter extends RecyclerView.Adapter<ImageColorAdapter.ImageViewHolder> {

    private final OnListItemSelected callback;
    private Context context;
    private List<ColorInfo> colors = new ArrayList<>();
    private LayoutInflater inflater;

    private final ColorUtils colorUtils;

    public ImageColorAdapter(Context context, OnListItemSelected callback) {
        this.context = context;
        this.callback = callback;
        inflater = LayoutInflater.from(context);
        colorUtils = new ColorUtils();

    }

    public void setItems(List<ColorInfo> items){
        this.colors = items;
        notifyDataSetChanged();
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(inflater.inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        final ColorInfo colorInfo = colors.get(position);
        holder.slot.setBackgroundColor(colorInfo.getColor());
        holder.hexValue.setText(ColorUtils.toHex(colorInfo.getColor()));
        holder.hexValue.setTextColor(colorInfo.getTextColor());
        if (TextUtils.isEmpty(colorInfo.getName()))
            holder.colorName.setVisibility(View.GONE);
        else {
            holder.colorName.setText(colorInfo.getName());
            holder.colorName.setTextColor(colorInfo.getTextColor());
        }

        holder.itemView.setOnClickListener( v -> callback.onItemSelected(colorInfo.getColor()));
    }

    @Override
    public int getItemCount() {
        return colors.size();
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