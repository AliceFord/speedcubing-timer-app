package io.github.techiehelper.speedcubingtimer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class LearnAlgsPage extends Fragment {

    private void doClick(AlgorithmSet algorithmSet) {
        ((CustomApplication) requireActivity().getApplication()).setCurrentAlgorithm(algorithmSet);

        NavHostFragment.findNavController(LearnAlgsPage.this)
                .navigate(R.id.action_LearnAlgs_to_AlgDisplayer);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.page_learn_algs, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.twolookpll).setOnClickListener(view1 -> doClick(AlgorithmSet.TWO_LOOK_PLL));
        view.findViewById(R.id.twolookoll).setOnClickListener(view1 -> doClick(AlgorithmSet.TWO_LOOK_OLL));
        view.findViewById(R.id.pll).setOnClickListener(view1 -> doClick(AlgorithmSet.PLL));
    }
}