package io.github.techiehelper.speedcubingtimer;

public enum ScrambleMove {
    U,
    D,
    L,
    R,
    F,
    B,
    UNUSED;

    public boolean isPrime = false;
    public boolean isDouble = false;

    @Override
    public String toString() {
        String strVal = "";
        if (this.ordinal() == ScrambleMove.UNUSED.ordinal()) return "";
        else if (this.ordinal() == ScrambleMove.U.ordinal()) strVal = "U";
        else if (this.ordinal() == ScrambleMove.D.ordinal()) strVal = "D";
        else if (this.ordinal() == ScrambleMove.L.ordinal()) strVal = "L";
        else if (this.ordinal() == ScrambleMove.F.ordinal()) strVal = "F";
        else if (this.ordinal() == ScrambleMove.B.ordinal()) strVal = "B";
        else strVal = "R";

        if (this.isPrime) strVal = strVal + "'";
        else if (this.isDouble) strVal = strVal + "2";

        return strVal;
    }
}
