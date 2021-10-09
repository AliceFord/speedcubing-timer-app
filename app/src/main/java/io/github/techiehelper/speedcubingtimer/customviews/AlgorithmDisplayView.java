package io.github.techiehelper.speedcubingtimer.customviews;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.HashMap;

import androidx.annotation.Nullable;
import io.github.techiehelper.speedcubingtimer.AlgType;
import io.github.techiehelper.speedcubingtimer.AlgorithmSetupDescriptor;

public class AlgorithmDisplayView extends View {
    private static final HashMap<Character, Integer> colorMap = new HashMap<Character, Integer>(){{
        put('O', Color.rgb(255, 128, 0)); // !
        put('R', Color.RED);
        put('G', Color.GREEN);
        put('B', Color.BLUE);
        put('U', Color.GRAY);
    }};

    private final AlgorithmSetupDescriptor setupDescriptor;
    private final String[] algs; // 1st is default

    private Paint paint;

    public AlgorithmDisplayView(Context context, AlgorithmSetupDescriptor setupDescriptor, String[] algs) {
        super(context);

        this.setLayoutParams(new ActionBar.LayoutParams(1440, 390));

        this.setupDescriptor = setupDescriptor;
        this.algs = algs;

        setupPaint();
    }

    private void setupPaint() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(60.0f);

        canvas.drawText(setupDescriptor.getName(), 30, 70, paint);

        paint.setTextSize(70.0f);

        canvas.drawText(algs[0], 20, 225, paint);

        if (setupDescriptor.getAlgType() == AlgType.PLL) {
            paint.setColor(Color.YELLOW);
            canvas.drawRect(1080, 60, 1350, 330, paint);

            String instructions = setupDescriptor.getAlgInstructions();
            int edgeLength =  instructions.indexOf(' ') / 4;
            for (int i = 0; i < instructions.indexOf(' '); i++) {
                char current = instructions.charAt(i);
                paint.setColor(colorMap.get(current));
                if (i < edgeLength) {
                    canvas.drawRect(1080 + i * 90, 30, 1170 + i * 90, 60, paint);
                } else if (i < edgeLength * 2) {
                    canvas.drawRect(1350, 60 + (i % edgeLength) * 90, 1380, 150 + (i % edgeLength) * 90, paint);
                } else if (i < edgeLength * 3) {
                    canvas.drawRect(1350 - (i % edgeLength) * 90, 330, 1260 - (i % edgeLength) * 90, 360, paint);
                } else {
                    canvas.drawRect(1050, 330 - (i % edgeLength) * 90, 1080, 240 - (i % edgeLength) * 90, paint);
                }

            }

            paint.setColor(Color.BLACK);
            canvas.drawLine(1080, 30, 1080, 360, paint); // Vertical lines
            canvas.drawLine(1170, 30, 1170, 360, paint);
            canvas.drawLine(1260, 30, 1260, 360, paint);
            canvas.drawLine(1350, 30, 1350, 360, paint);

            canvas.drawLine(1050, 60, 1050, 330, paint);
            canvas.drawLine(1380, 60, 1380, 330, paint);

            canvas.drawLine(1050, 60, 1380, 60, paint); // Horizontal lines
            canvas.drawLine(1050, 150, 1380, 150, paint);
            canvas.drawLine(1050, 240, 1380, 240, paint);
            canvas.drawLine(1050, 330, 1380, 330, paint);

            canvas.drawLine(1080, 30, 1350, 30, paint);
            canvas.drawLine(1080, 360, 1350, 360, paint);
        }
    }
}
