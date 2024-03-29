package com.example.haxxor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class HexagonalGridView extends View {

    private static final int NUM_ROWS = 7;
    private static final int[] HEXAGONS_IN_ROWS = {3, 4, 5, 6, 5, 4, 3};
    private static final int HEX_RADIUS = 100; // Adjust this to increase the hexagon size
    private static final int HEX_WIDTH = (int) (HEX_RADIUS * Math.sqrt(3));
    private static final int HEX_HEIGHT = HEX_RADIUS * 2;
    private static final int HEX_MARGIN = 1;
    private static final int STROKE_WIDTH = 3;
    private static final int BOARD_TOP_MARGIN = 100;
    private static final int BOARD_START_MARGIN = 18;
    private List<List<Hexagon>> hexagons;

    public HexagonalGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeHexagons();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initializeHexagons();
    }

    @SuppressLint("ResourceAsColor")
    private void initializeHexagons() {
        hexagons = new ArrayList<>();
        int startY = HEX_MARGIN + BOARD_TOP_MARGIN;

        for (int i = 0; i < NUM_ROWS; i++) {
            List<Hexagon> rowHexagons = new ArrayList<>();
            int numHexagonsInRow = HEXAGONS_IN_ROWS[i];

            //int startX = HEX_MARGIN + (HEX_WIDTH / 2) * (NUM_ROWS - numHexagonsInRow) + BOARD_START_MARGIN;
            float startX = (getWidth() - getRowWidth(i) + HEX_WIDTH) / 2;

            for (int j = 0; j < numHexagonsInRow; j++) {
                Paint fillPaint = new Paint();
                if (i == 0) {
                    fillPaint.setColor(Color.parseColor("#8DA6CA"));
                } else if (i == NUM_ROWS - 1) {
                    fillPaint.setColor(Color.parseColor("#E65454"));
                } else {
                    fillPaint.setColor(Color.parseColor("#131122"));
                }
                fillPaint.setStyle(Paint.Style.FILL);

                Paint strokePaint = new Paint();
                strokePaint.setColor(Color.parseColor("#B9C9DF")); // Set stroke color
                strokePaint.setStyle(Paint.Style.STROKE);
                strokePaint.setStrokeWidth(STROKE_WIDTH); // Set stroke width

                boolean isClickable = (i >= 1 && i <= 5);

                // Adjust startY and startX to include HEX_HEIGHT and HEX_WIDTH, respectively
                float x = startX + j * (HEX_WIDTH + HEX_MARGIN);
                float y = startY + i * ((3f / 4f) * HEX_HEIGHT + HEX_MARGIN);

                Hexagon hexagon = new Hexagon(x, y, HEX_RADIUS, fillPaint, strokePaint, isClickable);
                rowHexagons.add(hexagon);
            }

            hexagons.add(rowHexagons);
        }
    }

    private float getRowWidth(int row) {
        int numHexagons = HEXAGONS_IN_ROWS[row];
        return (float) (numHexagons * HEX_RADIUS * Math.sqrt(3));
    }


    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        for (List<Hexagon> row : hexagons) {
            for (Hexagon hexagon : row) {
                hexagon.draw(canvas);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();

            for (List<Hexagon> row : hexagons) {
                for (Hexagon hexagon : row) {
                    if (hexagon.isClickable() && isPointInHexagon(x, y, hexagon)) {
                        hexagon.toggleColor();
                        invalidate();
                        break;
                    }
                }
            }
        }

        return true;
    }

    private boolean isPointInHexagon(float x, float y, Hexagon hexagon) {
        float dx = x - hexagon.getCenterX();
        float dy = y - hexagon.getCenterY();

        return (Math.abs(dx) + Math.abs(dy)) <= HEX_RADIUS
                && Math.abs(dx * 0.5) <= HEX_RADIUS
                && Math.abs(dy * 0.86602540378) <= HEX_RADIUS;
    }
}