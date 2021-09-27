package io.github.techiehelper.speedcubingtimer;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class TimerFragment extends Fragment {

    private final SecureRandom rand = new SecureRandom();
    private boolean timerRunning = false;
    private boolean timerReady = false;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss.SS", Locale.UK);

    private Thread countdownTimer;
    private String currentScramble = "";
    private long time = 0;

    private ScrambleMove getMove(ScrambleMove lastMove) {
        List<ScrambleMove> validMoves = new LinkedList<>();
        for (ScrambleMove move : ScrambleMove.values()) {
            if (move.ordinal() != lastMove.ordinal()) {
                validMoves.add(move);
            }
        }

        ScrambleMove chosenMove = validMoves.get(rand.nextInt(5));
        if (rand.nextInt(3) == 0) chosenMove.isDouble = true;
        else {
            if (rand.nextInt(3) == 0) chosenMove.isPrime = true;
        }

        return chosenMove;
    }

    private String generateMoveSequence() {
        StringBuilder newSequence = new StringBuilder();
        ScrambleMove prevMove = ScrambleMove.UNUSED;
        for (int i = 1; i <= rand.nextInt(8) + 17; i++) {
            prevMove = getMove(prevMove);
            newSequence.append(prevMove.toString()).append(" ");
        }
        return newSequence.toString();
    }

    private void setScramble(View view, String scramble) {
        ((TextView)view.findViewById(R.id.scrambleText)).setText(scramble);
    }

    private void regenerateScramble(View view) {
        currentScramble = generateMoveSequence();

        setScramble(view, currentScramble);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timer, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        regenerateScramble(view);

        final Handler handler = new Handler();
        Runnable longPress = new Runnable() {
            @Override
            public void run() {
                ((TextView)view.findViewById(R.id.timerText)).setTextColor(Color.GREEN);
                timerReady = true;
            }
        };

        Runnable timerRunner = new Runnable() {
            @Override
            public void run() {
                Calendar calendar = Calendar.getInstance();
                long startTime = System.nanoTime();
                while (true) {
                    long currentTime = System.nanoTime();

                    time = (currentTime - startTime) / 1000000;
                    calendar.setTimeInMillis(time);

                    ((TextView)view.findViewById(R.id.timerText)).setText(dateFormat.format(calendar.getTime()));

                    try {
                        Thread.sleep(10);
                    }
                    catch (InterruptedException e) {
                        break;
                    }
                }
            }
        };

        view.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    if (timerRunning) {
                        regenerateScramble(view);
                        countdownTimer.interrupt();
                        timerRunning = false;
                    } else {
                        ((TextView) view.findViewById(R.id.timerText)).setTextColor(Color.RED);
                        handler.postDelayed(longPress, 500);
                    }

                    v.performClick();
                } else if ((event.getActionMasked() == MotionEvent.ACTION_UP)) {
                    if (timerReady) {
                        timerRunning = true;
                        timerReady = false;
                        countdownTimer = new Thread(timerRunner);
                        countdownTimer.start();
                    } else {
                        handler.removeCallbacks(longPress);
                    }
                    ((TextView)view.findViewById(R.id.timerText)).setTextColor(Color.BLACK);
                }

                return true;
            }
        });
    }
}