package io.github.techiehelper.speedcubingtimer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import io.github.techiehelper.speedcubingtimer.customviews.AlgorithmDisplayView;

public class AlgDisplayer extends Fragment {
    private File twoLookPllFile;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.page_alg_displayer, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            Log.i("", Arrays.toString(requireContext().getAssets().list("")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        LinearLayout algs = requireActivity().findViewById(R.id.algDisplayerLayout);

        String fileName;

        switch (((CustomApplication) requireActivity().getApplication()).getCurrentAlgorithm()) {
            case TWO_LOOK_PLL:
                fileName = "twoLookPll.csv";
                break;
            case TWO_LOOK_OLL:
                fileName = "twoLookOll.csv";
                break;
            case PLL:
                fileName = "pll.csv";
                break;
            case UNUSED:
            default:
                fileName = "empty.csv";
                break;
        }

        List<List<String>> parsedData = null;
        try {
            parsedData = AlgorithmFileParser.parse(requireContext().getAssets().open(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (parsedData == null) return; // Error!

        for (List<String> perm : parsedData) {
            AlgorithmSetupDescriptor descriptor = new AlgorithmSetupDescriptor(perm.get(0), AlgType.PLL, perm.get(1), new String[]{perm.get(2)});
            AlgorithmDisplayView displayView = new AlgorithmDisplayView(getActivity(), descriptor, perm.subList(3, perm.size()));
            algs.addView(displayView);
        }
    }
}
