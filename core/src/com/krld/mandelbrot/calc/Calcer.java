package com.krld.mandelbrot.calc;

import com.badlogic.gdx.graphics.Pixmap;

/**
 * Created by Andrey on 7/8/2014.
 */
public interface Calcer {
    void calcPixmap(Pixmap pixMap, double startX, double startY, double endX, double endY);

    void increaseIteration();

    void decreaseMaxIterations();
}
