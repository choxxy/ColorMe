package com.garageprojects.colorme;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.garageprojects.colorme.colorutil.Colour;
import com.garageprojects.colorme.databinding.LayoutColorDialogBinding;

public class ColorFragment extends DialogFragment {

    private static final String COLOR =  "selected_color";
    LayoutColorDialogBinding binding;
    private int selectedColor;


    public static ColorFragment newInstance(int color) {
        ColorFragment fragment = new ColorFragment();
        Bundle args = new Bundle();
        args.putInt(COLOR, color);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = LayoutColorDialogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Create color schemes
        createAnalagousColors();
        createMonochromaticColors();
        createTriadColors();
        createComplementaryColors();

        //Contrasting colors
        createLightContrastingColor();
        createDarkContrastingColor();

        //Complementary Colors
        createComplementaryColorLight();
        createComplementaryColorDark();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        selectedColor =  Colour.RED;//getArguments().getInt(COLOR);


    }

    // Setup Functions

    private void createAnalagousColors() {

        // Go create an analagous color scheme based on the selected color
        int[] analagousColors = Colour.colorSchemeOfType(selectedColor,
                Colour.ColorScheme.ColorSchemeAnalagous);

        // Apply those colors to the analagous scheme
        binding.AnalagousSchemeBaseColor.setBackgroundColor(selectedColor);
        binding.AnalagousSchemeBelow2.setBackgroundColor(analagousColors[0]);
        binding.AnalagousSchemeBelow1.setBackgroundColor(analagousColors[1]);
        binding.AnalagousSchemeAbove1.setBackgroundColor(analagousColors[2]);
        binding.AnalagousSchemeAbove2.setBackgroundColor(analagousColors[3]);
    }

    private void createMonochromaticColors() {

        // Go create an analagous color scheme based on the selected color
        int[] monochromaticColors = Colour.colorSchemeOfType(
                selectedColor, Colour.ColorScheme.ColorSchemeMonochromatic);

        // Apply those colors to the analagous scheme
        binding.MonochromaticSchemeBaseColor.setBackgroundColor(selectedColor);
        binding.MonochromaticSchemeBelow2.setBackgroundColor(monochromaticColors[0]);
        binding.MonochromaticSchemeBelow1.setBackgroundColor(monochromaticColors[1]);
        binding.MonochromaticSchemeAbove1.setBackgroundColor(monochromaticColors[2]);
        binding.MonochromaticSchemeAbove2.setBackgroundColor(monochromaticColors[3]);
    }

    private void createTriadColors() {

        // Go create an analagous color scheme based on the selected color
        int[] triadColors = Colour.colorSchemeOfType(selectedColor,
                Colour.ColorScheme.ColorSchemeTriad);

        // Apply those colors to the analagous scheme
        binding.TriadSchemeBaseColor.setBackgroundColor(selectedColor);
        binding.TriadSchemeBelow2.setBackgroundColor(triadColors[0]);
        binding.TriadSchemeBelow1.setBackgroundColor(triadColors[1]);
        binding.TriadSchemeAbove1.setBackgroundColor(triadColors[2]);
        binding.TriadSchemeAbove2.setBackgroundColor(triadColors[3]);
    }

    private void createComplementaryColors() {

        // Go create an analagous color scheme based on the selected color
        int[] complementaryColors = Colour.colorSchemeOfType(
                selectedColor, Colour.ColorScheme.ColorSchemeComplementary);

        // Apply those colors to the analagous scheme
        binding.ComplementarySchemeBaseColor.setBackgroundColor(selectedColor);
        binding.ComplementarySchemeBelow2.setBackgroundColor(complementaryColors[0]);
        binding.ComplementarySchemeBelow1.setBackgroundColor(complementaryColors[1]);
        binding.ComplementarySchemeAbove1.setBackgroundColor(complementaryColors[2]);
        binding.ComplementarySchemeAbove2.setBackgroundColor(complementaryColors[3]);
    }

    private void createLightContrastingColor() {

        // Define background color
        int backgroundColor = selectedColor; // Change this to see a
        // new contrasting color
        binding.LightContrastingColorTextView.setBackgroundColor(backgroundColor);

        // Set contrasting color
        binding.LightContrastingColorTextView.setTextColor(Colour
                .blackOrWhiteContrastingColor(backgroundColor));

    }

    private void createDarkContrastingColor() {

        // Define background color
        int backgroundColor = selectedColor; // Change this to see a
        // new contrasting color
        binding.DarkContrastingColorTextView.setBackgroundColor(backgroundColor);

        // Set contrasting color
        binding.DarkContrastingColorTextView.setTextColor(Colour
                .blackOrWhiteContrastingColor(backgroundColor));

    }

    //Complementary Colors
    private void createComplementaryColorLight() {

        //Set complement color
        binding.ComplementaryColor1.setBackgroundColor(Colour.complementaryColor(selectedColor));
    }

    private void createComplementaryColorDark() {

        //Set complement color
        binding.ComplementaryColor2.setBackgroundColor(Colour.complementaryColor(selectedColor));
    }


}