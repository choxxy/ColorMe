package com.garageprojects.colorme;


import android.graphics.Point;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.garageprojects.colorme.colorutil.ColorUtils;
import com.garageprojects.colorme.databinding.FragmentMainBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    FragmentMainBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);


        binding.gallery.setOnClickListener(v -> navigateToImageViewer(ImageColorFragment.SOURCE_GALLERY));

        binding.camera.setOnClickListener(v -> navigateToImageViewer(ImageColorFragment.SOURCE_CAMERA));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewTreeObserver vto = view.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width  = binding.title.getMeasuredWidth();
                int height = binding.title.getMeasuredHeight();

                Point size = new Point(width,height);

                Shader shader = ColorUtils.getRandomRadialGradient(requireContext(),size);
                binding.title.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
                binding.title.getPaint().setShader(shader);

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
