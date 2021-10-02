package io.github.techiehelper.speedcubingtimer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class LearnAlgsPage extends Fragment {

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

        view.findViewById(R.id.twolookpll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((CustomApplication) getActivity().getApplication()).setCurrentAlgorithm(AlgorithmSet.TWO_LOOK_PLL);

                NavHostFragment.findNavController(LearnAlgsPage.this)
                        .navigate(R.id.action_LearnAlgs_to_AlgDisplayer);
            }
        });
    }
}