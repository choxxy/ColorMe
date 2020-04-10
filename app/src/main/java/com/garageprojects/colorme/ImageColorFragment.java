package com.garageprojects.colorme;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImageColorFragment extends Fragment {

    public static final int SOURCE_GALLERY = 0;
    public static final int SOURCE_CAMERA = 1;

    @BindView(R.id.preview)
    ImageView preview;
    @BindView(R.id.list)
    RecyclerView list;
    private ArrayList<Image> images = new ArrayList<>();


    public ImageColorFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageColorFragmentArgs args = ImageColorFragmentArgs.fromBundle(getArguments());

        if (args.getSource() == SOURCE_CAMERA) {

            ImagePicker.with(this)
                    .setFolderMode(false)
                    .setCameraOnly(true)
                    .setFolderTitle("Album")
                    .setMultipleMode(false)
                    .setSelectedImages(images)
                    .setMaxSize(10)
                    .setBackgroundColor("#212121")
                    .setAlwaysShowDoneButton(true)
                    .setRequestCode(100)
                    .setSavePath("ColorMe")
                    .setKeepScreenOn(true)
                    .start();
        } else {
            ImagePicker.with(this)
                    .setFolderMode(true)
                    .setCameraOnly(false)
                    .setFolderTitle("Album")
                    .setMultipleMode(false)
                    .setSelectedImages(images)
                    .setMaxSize(10)
                    .setBackgroundColor("#212121")
                    .setAlwaysShowDoneButton(true)
                    .setRequestCode(100)
                    .setSavePath("ColorMe")
                    .setKeepScreenOn(true)
                    .start();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image_color, container, false);
        ButterKnife.bind(this, view);

        list.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == Config.RC_PICK_IMAGES && resultCode == RESULT_OK && data != null) {
            images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);

            Glide.with(this)
                    .asBitmap()
                    .load(images.get(0).getPath())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.mipmap.ic_launcher)
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            if (resource != null) {
                                Palette p = Palette.from(resource).generate();
                                extractColors(p.getSwatches());
                            }
                            return false;
                        }
                    })
                    .into(preview);


        }
    }


    private void extractColors(List<Palette.Swatch> swatches) {

        List<Integer> colors = new ArrayList<>();

        for (Palette.Swatch swatch : swatches) {
            colors.add(swatch.getRgb());
        }

        list.setAdapter(new ImageColorAdapter(getContext(), colors));
    }
}
