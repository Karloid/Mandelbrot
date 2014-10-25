package com.krld.mandelbrot;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Core extends ApplicationAdapter {
    private static final double START_X = -2;
    private static final double START_Y = -1.2;
    private static final double END_X = 3.2;
    public static final int ITERATION_COUNT = 6;
    public static final int mDetailLevel = -1;
    SpriteBatch batch;
    private Pixmap pixMap;

    private int mWidth;
    private int mHeight;

    private Texture texture;
    private Calcer mandelbrot;
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private double zoomFactor = 2;
    private double mIteration;

    @Override
    public void create() {
        batch = new SpriteBatch();
        mWidth = Gdx.graphics.getWidth();
        mHeight = Gdx.graphics.getHeight();

        startX = START_X;
        startY = START_Y;
        endX = END_X;
        endY = START_Y + ((END_X - START_X) / 16) * 9;

        mandelbrot = new MandelbrotCalcer();

        mIteration = ITERATION_COUNT;

        Gdx.input.setInputProcessor(new MyInputProcessor(this));
    }

    void updateTexture() {
        mIteration = ITERATION_COUNT;
    }

    @Override
    public void render() {
        batch.begin();
        calcTexture();
        batch.draw(texture, 0, 0, mWidth, mHeight);
        batch.end();
    }

    private void calcTexture() {
        if (mIteration == mDetailLevel) return;
        double currentDivider = Math.pow(2, mIteration);
        int width = (int) (mWidth / currentDivider);
        int height = (int) (mHeight / currentDivider);
        Pixmap tmpPixMap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        mandelbrot.calcPixmap(tmpPixMap, startX, startY, endX, endY);
        texture = new Texture(tmpPixMap, Pixmap.Format.RGBA8888, false);
        mIteration--;
    }


    public void zoom(int x, int y, boolean in) {
        double realX, realY;
        realX = startX + ((endX - startX) / mWidth) * x;
        realY = startY + ((endY - startY) / mHeight) * y;
        System.out.println("realX : " + realX + "; realY: " + realY);
        System.out.println("x : " + x + "; y: " + y);
        double deltaX = endX - startX;
        double deltaY = (deltaX / 16) * 9;
        if (in) {
            deltaX /= zoomFactor;
            deltaY /= zoomFactor;
        } else {
            deltaX *= zoomFactor;
            deltaY *= zoomFactor;
        }
        startX = realX - deltaX / 2;
        startY = realY - deltaY / 2;
        endX = startX + deltaX;
        endY = startY + deltaY;
        updateTexture();
    }

    public void detailUp() {
        mandelbrot.increaseIteration();
        updateTexture();
    }

    public void detailDown() {
        mandelbrot.decreaseMaxIterations();
        updateTexture();
    }
}
