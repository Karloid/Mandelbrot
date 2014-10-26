package com.krld.mandelbrot.calc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Andrey on 7/8/2014.
 */
public class MandelbrotCalcer implements Calcer {
    public static final int MAX_ITER_INITIAL = 100;
    private static int maxIteration = MAX_ITER_INITIAL;
    private static final double MAX_RES = 4;
    private int nThreads = 4;

    private Pixmap mPixMap;

    @Override
    public void calcPixmap(Pixmap pixMap, double startX, double startY, double endX, double endY) {
        try {
            mPixMap = pixMap;
            ExecutorService executor = Executors.newFixedThreadPool(nThreads);
            int pixelWidth = pixMap.getWidth() / nThreads;
            double realWidth = (endX - startX) / nThreads;
            for (int i = 0; i < nThreads; i++) {
                int xOffset = pixelWidth * i;
                double startXpart = startX + realWidth * i;
                double endXpart = startX + realWidth * (i + 1);
                executor.execute(new CalcerRunnable(pixMap, startXpart, startY, endXpart, endY, xOffset, pixelWidth));
            }
            executor.shutdown();
            executor.awaitTermination(90000, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void increaseIteration() {
        maxIteration *= 1.1f;
    }

    @Override
    public void decreaseMaxIterations() {
        maxIteration *= 0.9f;
    }

    private Color getColor(int iter) {
        float iterFloat = iter;

        float k = ((iterFloat / myLog(maxIteration, iterFloat)) / maxIteration);

        if (k > 1f) {
            k = 1f;
        }
        float hueAngle = 1f / 360f;
        hueAngle = hueAngle * 360 * k;
        float endAngle = hueAngle + 0.01f;
        float startAngle = hueAngle - 0.01f;
        if (endAngle > 1f)
            endAngle = 0.99f;
        if (startAngle < 0f)
            startAngle = 0f;
        String hexColor = getColor(startAngle, endAngle, 0.55f, 0.55f, 0.9f, 0.9f);
        return Color.valueOf(hexColor);
    }

    private float myLog(float base, float value) {
        return (float) (Math.log(value) / Math.log(base));
    }

    public String getColor(float minHue, float maxHue, float minSaturation, float maxSaturation, float minValue, float maxValue) {
        // hsv[0] is Hue [0 .. 360) hsv[1] is Saturation [0...1] hsv[2] is Value [0...1]
        float[] hsv = new float[3];
        hsv[0] = Utils.getFloat(minHue, maxHue);
        hsv[1] = Utils.getFloat(minSaturation, maxSaturation);
        hsv[2] = Utils.getFloat(minValue, maxValue);
        return Utils.hsvToRgb(hsv[0], hsv[1], hsv[2]);
    }

    private synchronized void drawPixels(int[][] pixArray, int xOffset) {
        for (int x = xOffset; x < xOffset + pixArray.length; x++) {
            for (int y = 0; y < pixArray[0].length; y++) {
                Color color;
                int iter = pixArray[x - xOffset][y];
                if (iter != -1) {
                    color = getColor(iter);
                } else {
                    color = Color.BLACK;
                }
                mPixMap.setColor(color);
                mPixMap.drawPixel(x, y);
            }
        }
    }

    private class CalcerRunnable implements Runnable {
        private final Pixmap mPixmap;
        private final double mStartX;
        private final double mStartY;
        private final double mEndX;
        private final double mEndY;

        private int mWidth;
        private int mXOffset;

        public CalcerRunnable(Pixmap pixMap, double startX, double startY, double endX, double endY, int xOffset, int width) {
            mPixmap = pixMap;
            mStartX = startX;
            mStartY = startY;
            mEndX = endX;
            mEndY = endY;

            mWidth = width;
            mXOffset = xOffset;
        }

        @Override
        public void run() {
            int[][] pixArray = new int[mWidth][mPixmap.getHeight()];
            calcArray(pixArray, mStartX, mStartY, mEndX, mEndY);
            drawPixels(pixArray, mXOffset);
        }

        private void calcArray(int[][] pixArray, double startX, double startY, double endX, double endY) {
            int width = pixArray.length;
            int height = pixArray[0].length;
            int xPix = width - 1;
            int yPix = height - 1;
            double deltaX = (endX - startX) / width;
            double deltaY = (endY - startY) / height;
            for (double x = startX; x <= endX; x += deltaX) {
                for (double y = startY; y <= endY; y += deltaY) {
                    int res = someRealMathCalc(x, y);
                    if (yPix < 0) break;
                    pixArray[width - 1 - xPix][yPix] = res;
                    yPix--;
                }
                yPix = height - 1;
                xPix--;
                if (xPix < 0) break;
            }
        }

    }

    private int someRealMathCalc(double x, double y) {
        int iter = 0;
        double xTemp = x;
        double yTemp = y;
        double res = (x * x) + (y * y);
        while (res < MAX_RES && iter < maxIteration) {
            double xTemp2 = (xTemp * xTemp) - (yTemp * yTemp) - x;
            yTemp = (2 * xTemp * yTemp) - y;
            xTemp = xTemp2;
            res = (xTemp * xTemp) + (yTemp * yTemp);
            iter++;
        }
        if (res >= MAX_RES) {
            return iter;
        } else {
            return -1;
        }
    }
}
