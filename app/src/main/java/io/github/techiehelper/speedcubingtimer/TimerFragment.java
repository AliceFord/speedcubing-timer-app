package io.github.techiehelper.speedcubingtimer;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class TimerFragment extends Fragment {

    private final SecureRandom rand = new SecureRandom();
    private boolean timerRunning = false;
    private boolean timerReady = false;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss.SS", Locale.UK);

    private File scramblesFile;

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

    private void writeTime() {
        try {
            DocumentBuilderFactory objFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder objBuilder = objFactory.newDocumentBuilder();
            Document objDocument = objBuilder.parse(scramblesFile);
            Element root = objDocument.getDocumentElement();
            Node recordsNode = objDocument.getElementsByTagName("records").item(0);
            Node recordNode = objDocument.createElement("record");
            recordsNode.appendChild(recordNode);

            Node timeNode = objDocument.createElement("time");
            timeNode.setTextContent(String.valueOf(time));
            recordNode.appendChild(timeNode);

            Node dateNode = objDocument.createElement("date");
            dateNode.setTextContent(String.valueOf(System.currentTimeMillis()));
            recordNode.appendChild(dateNode);

            Node scrambleNode = objDocument.createElement("scramble");
            scrambleNode.setTextContent(currentScramble);
            recordNode.appendChild(scrambleNode);

            Node resultNode = objDocument.createElement("result");
            resultNode.setTextContent("OK");
            recordNode.appendChild(resultNode);

            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.transform(new DOMSource(objDocument), new StreamResult(new FileOutputStream(scramblesFile)));

        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
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

        scramblesFile = new File(requireContext().getExternalFilesDir(null), "scrambles.xml");

        try {
            if (scramblesFile.createNewFile()) {
                    FileWriter writer = new FileWriter(scramblesFile);
                    writer.write("<records></records>");
                    writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("", "Write Failed");
        }


        TextView timerText = view.findViewById(R.id.timerText);

        regenerateScramble(view);

        final Handler handler = new Handler();
        Runnable longPress = new Runnable() {
            @Override
            public void run() {
                timerText.setTextColor(Color.GREEN);
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

                    timerText.setText(dateFormat.format(calendar.getTime()));

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
                        countdownTimer.interrupt();
                        timerRunning = false;
                        writeTime();
                        regenerateScramble(view);
                    } else {
                        timerText.setTextColor(Color.RED);
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
                    timerText.setTextColor(Color.BLACK);
                }

                return true;
            }
        });
    }
}