package com.garageprojects.colorme;


import android.graphics.Point;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.garageprojects.colorme.colorutil.ColorUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.gallery)
    Button gallery;
    @BindView(R.id.camera)
    Button camera;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewTreeObserver vto = view.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width  = title.getMeasuredWidth();
                int height = title.getMeasuredHeight();

                Point size = new Point(width,height);

                Shader shader = ColorUtils.getRandomRadialGradient(requireContext(),size);
                title.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
                title.getPaint().setShader(shader);

            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();



    }

    private void navigateToImageViewer(int source) {

        MainFragmentDirections.ToImageViewer directions = MainFragmentDirections.toImageViewer(source);
        Navigation.findNavController(getView()).navigate(directions);

    }


}
