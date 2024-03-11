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
    private static final int HEX_RADIUS = 65; // Adjust this to increase the hexagon size
    private static final int HEX_WIDTH = (int) (HEX_RADIUS * Math.sqrt(3));
    private List<Hexagon> hexagons;

    public HexagonalGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeHexagons();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initializeHexagons();
    }

    private void initializeHexagons() {
        hexagons = new ArrayList<>();

        int startY = 1;
        for (int i = 0; i < NUM_ROWS; i++) {
            int startX = (getWidth() - getRowWidth(i)) / 2;

            for (int j = 0; j < HEXAGONS_IN_ROWS[i]; j++) {
                int centerX = startX + HEX_WIDTH / 2;
                int centerY = startY + HEX_RADIUS;

                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setColor(Color.BLUE);

                boolean isClickable = (i >= 1 && i <= 5);

                Hexagon hexagon = new Hexagon(centerX, centerY, HEX_RADIUS, paint, isClickable);
                hexagons.add(hexagon);

                startX += HEX_RADIUS * 3;
            }

            startY += HEX_RADIUS * 2;
        }
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        for (Hexagon hexagon : hexagons) {
            hexagon.draw(canvas);
        }
    }

    private int getRowWidth(int row) {
        return HEX_RADIUS * 3 * HEXAGONS_IN_ROWS[row];
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();

            for (Hexagon hexagon : hexagons) {
                if (hexagon.isClickable() && isPointInHexagon(x, y, hexagon)) {
                    hexagon.toggleColor();
                    invalidate();
                    break;
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
