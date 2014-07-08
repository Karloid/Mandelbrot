package com.krld.mandelbrot;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;

/**
 * Created by Andrey on 7/8/2014.
 */
public class MandelbrotCalcer implements Calcer {
    public static final int MAX_ITER_INITIAL = 100;
    private static int maxIteration = MAX_ITER_INITIAL;
    private static final double MAX_RES = 4;

    @Override
    public void calcPixmap(Pixmap pixMap, double startX, double startY, double endX, double endY) {
        pixMap.setColor(Color.WHITE);
        pixMap.fill();
        int xPix = pixMap.getWidth(), yPix = pixMap.getHeight();
        for (double x = startX; x <= endX; x += (endX - startX) / pixMap.getWidth()) {
            for (double y = startY; y <= endY; y += (endY - startY) / pixMap.getHeight()) {
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
                Color color;
                if (res >= MAX_RES) {
                    color = getColor(iter);
                } else {
                    color = Color.BLACK;
                }
                pixMap.setColor(color);
                pixMap.drawPixel(xPix, yPix);
                yPix--;
            }
            yPix = pixMap.getHeight();
            xPix--;
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
        Color color;
        float iterFloat = iter;
        float k = 1f - iterFloat / maxIteration;
        float red = 1f * k;
        float green =  1f * k;
        float blue =  1 - red/1.1f;
        color = new Color(red, green, blue, 1f);
      /*  if (iter < 10) {
            color = Color.BLUE;
        } else {
            color = Color.RED;
        }*/
        return color;
    }
}
