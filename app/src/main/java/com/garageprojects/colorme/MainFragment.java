package com.garageprojects.colorme;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.prompt)
    TextView prompt;
    @BindView(R.id.gallery)
    Button gallery;
    @BindView(R.id.camera)
    Button camera;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);


        gallery.setOnClickListener(v -> navigateToImageViewer(ImageColorFragment.SOURCE_GALLERY));

        camera.setOnClickListener(v -> navigateToImageViewer(ImageColorFragment.SOURCE_CAMERA));

        return view;
    }

    private void navigateToImageViewer(int source) {

        MainFragmentDirections.ToImageViewer directions = MainFragmentDirections.toImageViewer(source);
        Navigation.findNavController(getView()).navigate(directions);

    }


}
