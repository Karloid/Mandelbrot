package com.krld.mandelbrot;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Core extends ApplicationAdapter {
    private static final double START_X = -2;
    private static final double START_Y = -1.2;
    private static final double END_X = 3.2;
    SpriteBatch batch;
    private Pixmap pixMap;
    private int width;
    private int height;
    private Texture texture;
    private Calcer mandelbrot;
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private double zoomFactor = 2;

    @Override
    public void create() {
        batch = new SpriteBatch();
        initTexture();

        startX = START_X;
        startY = START_Y;
        endX = END_X;
        endY = START_Y + ((END_X - START_X) / 16) * 9;

        mandelbrot = new MandelbrotCalcer();
        updateTexture();
        Gdx.input.setInputProcessor(new MyInputCalcer(this));
    }

    void updateTexture() {

        mandelbrot.calcPixmap(pixMap, startX, startY, endX, endY);
        texture = new Texture(pixMap, Pixmap.Format.RGBA8888, false);
    }

    private void initTexture() {
        pixMap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixMap.setColor(Color.WHITE);
        pixMap.fill();
        pixMap.setColor(Color.RED);
        pixMap.drawCircle(10, 20, 300);
        texture = new Texture(pixMap, Pixmap.Format.RGBA8888, false);

    }

    @Override
    public void render() {
        batch.begin();
        batch.draw(texture, 0, 0);
        batch.end();
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void zoom(int x, int y, boolean in) {
        double realX, realY;
        realX = startX + ((endX - startX) / width) * x;
        realY = startY + ((endY - startY) / height) * y;
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
