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
    private final Paint fillPaint;
    private final Paint strokePaint;
    private boolean isClickable;

    public Hexagon(float centerX, float centerY, float radius, Paint fillPaint, Paint strokePaint, boolean isClickable) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.fillPaint = fillPaint;
        this.strokePaint = strokePaint;
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
        canvas.drawPath(path, fillPaint);

        // Draw stroke
        if (strokePaint != null) {
            canvas.drawPath(path, strokePaint);
        }
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
        if (fillPaint.getColor() == Color.parseColor("#131122")) {
            fillPaint.setColor(Color.parseColor("#d631d1"));
        } else {
            fillPaint.setColor(Color.parseColor("#131122"));
        }
    }

}
