package io.github.techiehelper.speedcubingtimer;

public class AlgorithmSetupDescriptor {
    public String getName() {
        return name;
    }

    public AlgType getAlgType() {
        return algType;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setAlgType(AlgType algType) {
        this.algType = algType;
    }

    private String name;
    private AlgType algType;

    public String getAlgInstructions() {
        return algInstructions;
    }

    public void setAlgInstructions(String algInstructions) {
        this.algInstructions = algInstructions;
    }

    public String[] getAlgSetups() {
        return algSetups;
    }

    public void setAlgSetups(String[] algSetups) {
        this.algSetups = algSetups;
    }

    private String algInstructions;
    private String[] algSetups;

    AlgorithmSetupDescriptor(String name, AlgType algType, String algInstructions, String[] algSetups) {
        this.name = name;
        this.algType = algType;
        this.algInstructions = algInstructions;
        this.algSetups = algSetups;
    }
}
