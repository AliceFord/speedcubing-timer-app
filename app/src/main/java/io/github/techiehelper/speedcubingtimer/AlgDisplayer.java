package io.github.techiehelper.speedcubingtimer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import io.github.techiehelper.speedcubingtimer.customviews.AlgorithmDisplayView;

public class AlgDisplayer extends Fragment {
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

        LinearLayout algs = getActivity().findViewById(R.id.algDisplayerLayout);
        AlgorithmSetupDescriptor descriptor = new AlgorithmSetupDescriptor("H Perm", AlgType.PLL, "OROGBGRORBGB 2-8 8-2 5-11 11-5", new String[]{"Unused"});
        AlgorithmDisplayView temp = new AlgorithmDisplayView(getActivity(), descriptor, new String[]{"M2' U' M2' U2' M2' U' M2'"});
        algs.addView(temp);

        TextView text = new TextView(getActivity());
        text.setText("Hi");
        algs.addView(text);

        //getActivity().recreate();
    }
}
