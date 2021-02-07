package com.garageprojects.colorme;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.garageprojects.colorme.api.ColorNameService;
import com.garageprojects.colorme.databinding.FragmentImageColorBinding;
import com.garageprojects.colorme.ui.adapters.ImageColorAdapter;
import com.garageprojects.colorme.ui.callbacks.OnListItemSelected;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImageColorFragment extends Fragment implements OnListItemSelected {

    public static final int SOURCE_GALLERY = 0;
    public static final int SOURCE_CAMERA = 1;


    private FragmentImageColorBinding binding;

    private ArrayList<Image> images = new ArrayList<>();

    ColorNameService nameService;
    ImageColorAdapter colorAdapter;

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
                    .setDirectoryName("ColorMe")
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
                    .setDirectoryName("ColorMe")
                    .start();
        }

        nameService = new ColorNameService();
        colorAdapter =  new ImageColorAdapter(requireContext(), this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentImageColorBinding.inflate(inflater, container, false);

        binding.list.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.list.setAdapter(colorAdapter);
        return binding.getRoot();
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
                    .into(binding.preview);
        }

    }

    private void extractColors(List<Palette.Swatch> swatches) {

        binding.progressbar.setVisibility(View.VISIBLE);

        nameService .getColorNames(swatches, result ->{
            binding.progressbar.setVisibility(View.GONE);
                    colorAdapter.setItems(result);
                }
        );

    }

    @Override
    public void onItemSelected(int color) {
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        Fragment prev = getChildFragmentManager().findFragmentByTag("dialog");

        if (prev != null) {
            ft.remove(prev);
        }

        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = ColorFragment.newInstance(color);
        newFragment.show(ft, "dialog");
    }
}
