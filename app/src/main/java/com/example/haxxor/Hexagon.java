package com.example.haxxor;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.HashMap;
import java.util.Map;

public class Hexagon {
    private final float centerX;
    private final float centerY;
    private final float radius;
    private final Paint fillPaint;
    private final Paint strokePaint;
    private int trapColor = Color.parseColor("#40ff00");
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

    public Paint getFillPaint() {
        return fillPaint;
    }

    public Paint getStrokePaint() {
        return strokePaint;
    }

    public boolean isClickable() {
        return isClickable;
    }

    public int getTrapColor() {
        return trapColor;
    }

    public void setTrapColor(int trapColor) {
        if (trapColor == Color.parseColor("#131122")){
            this.trapColor = Color.parseColor("#40ff00");
        }
        else {
            this.trapColor = trapColor;
        }
    }


    @SuppressLint("ResourceAsColor")
    public void haxxorToggleColor() {
        if (fillPaint.getColor() == Color.parseColor("#131122")) {
            fillPaint.setColor(trapColor);
        } else {
            fillPaint.setColor(Color.parseColor("#131122"));
        }
    }

    @SuppressLint("ResourceAsColor")
    public void sentinelToggleColor() {
        // Define a map to store color mappings
        Map<Integer, Integer> colorMap = new HashMap<>();
        colorMap.put(Color.parseColor("#131122"), Color.parseColor("#C05BB6")); // Default -> Decomposition
        colorMap.put(Color.parseColor("#C05BB6"), Color.parseColor("#1a65bc")); // Decomposition -> Deception
        colorMap.put(Color.parseColor("#1a65bc"), Color.parseColor("#0fb2eb")); // Deception -> Constriction
        colorMap.put(Color.parseColor("#0fb2eb"), Color.parseColor("#f0d6ec")); // Constriction -> Incision
        colorMap.put(Color.parseColor("#f0d6ec"), Color.parseColor("#4b8e7f")); // Incision -> Prescription
        colorMap.put(Color.parseColor("#4b8e7f"), Color.parseColor("#73116c")); // Prescription -> Landmine
        colorMap.put(Color.parseColor("#73116c"), Color.parseColor("#f57a29")); // Landmine -> Nullification
        colorMap.put(Color.parseColor("#f57a29"), Color.parseColor("#d9442d")); // Nullification -> Inspiration
        colorMap.put(Color.parseColor("#d9442d"), Color.parseColor("#bd1523")); // Inspiration -> Erasion
        colorMap.put(Color.parseColor("#bd1523"), Color.parseColor("#faa97a")); // Erasion -> Conclusion
        colorMap.put(Color.parseColor("#faa97a"), Color.parseColor("#131122")); // Conclusion -> Default

        int currentColor = fillPaint.getColor();
        int defaultColor = Color.parseColor("#131122");

        // Replace colors from map
        trapColor = colorMap.getOrDefault(currentColor, defaultColor);
        fillPaint.setColor(trapColor);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Hexagon)) {
            return false;
        }
        Hexagon otherHexagon = (Hexagon) obj;
        return Float.compare(centerX, otherHexagon.centerX) == 0
                && Float.compare(centerY, otherHexagon.centerY) == 0;
    }

}
