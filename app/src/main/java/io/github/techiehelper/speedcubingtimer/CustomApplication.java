package io.github.techiehelper.speedcubingtimer;

import android.app.Application;

public class CustomApplication extends Application {
    public AlgorithmSet getCurrentAlgorithm() {
        return currentAlgorithm;
    }

    public void setCurrentAlgorithm(AlgorithmSet currentAlgorithm) {
        this.currentAlgorithm = currentAlgorithm;
    }

    private AlgorithmSet currentAlgorithm = AlgorithmSet.UNUSED;


}
