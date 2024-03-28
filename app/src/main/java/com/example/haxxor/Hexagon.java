package com.example.haxxor;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class Hexagon {
    private final float centerX;
    private final float centerY;
    private final float radius;
    private final Paint paint;
    private boolean isClickable;
    public Hexagon(float centerX, float centerY, float radius, Paint paint, boolean isClickable) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.paint = paint;
        this.isClickable = isClickable;
    }

    public void draw(Canvas canvas) {
        Path path = new Path();
        float angle = (float) Math.toRadians(60);
        float startAngle = (float) Math.toRadians(-30);

        for (int i = 0; i < 6; i++) {
            float x = (float) (centerX + radius * Math.cos(startAngle + angle * i));
            float y = (float) (centerY + radius * Math.sin(startAngle + angle * i));

            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }

        path.close();
        canvas.drawPath(path, paint);
    }

    public float getCenterX() {
        return centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public float getRadius() {
        return radius;
    }

    public boolean isClickable() {
        return isClickable;
    }

    @SuppressLint("ResourceAsColor")
    public void toggleColor() {
        if (paint.getColor() == R.color.hexagon_color) {
            paint.setColor(Color.RED);
        } else {
            paint.setColor(Color.BLUE);
        }
    }

}
