package com.krld.mandelbrot;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.krld.mandelbrot.calc.Calcer;
import com.krld.mandelbrot.calc.MandelbrotCalcer;

import static com.krld.mandelbrot.Directions.UP;

public class Core extends ApplicationAdapter {
    private static final double START_X = -2;
    private static final double START_Y = -1.2;
    private static final double END_X = 3.2;
    public static final int ITERATION_COUNT = 5;
    public static final int mDetailLevel = -1;
    private static final double ZOOM_DETAIL_LEVEL = 2;
    SpriteBatch batch;
    private Pixmap pixMap;


    private Texture texture;
    private Calcer mandelbrot;
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private double zoomFactor = 1.3f;
    private double mIteration;
    private boolean mZooming = false;

    private double zoomX;
    private double zoomY;
    private boolean zoomIn;
    private double mTranslateProcent = 0.1d;

    @Override
    public void create() {
        batch = new SpriteBatch();

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
        batch.draw(texture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    private void calcTexture() {
        if (mIteration == mDetailLevel) return;
        double currentDivider = Math.pow(2, mIteration);
        int width = (int) (Gdx.graphics.getWidth() / currentDivider);
        int height = (int) (Gdx.graphics.getHeight() / currentDivider);
        Pixmap tmpPixMap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        mandelbrot.calcPixmap(tmpPixMap, startX, startY, endX, endY);
        if (texture != null) {
            texture.dispose();
        }
        texture = new Texture(tmpPixMap, Pixmap.Format.RGBA8888, false);
        tmpPixMap.dispose();
        mIteration--;
        if (mZooming && mIteration < ZOOM_DETAIL_LEVEL) {
            zooming();
            //mZooming = false; //TODO tmp fix
        }
    }

    private void zooming() {
        double realX, realY;
        realX = startX + ((endX - startX) / Gdx.graphics.getWidth()) * zoomX;
        realY = startY + ((endY - startY) / Gdx.graphics.getHeight()) * zoomY;
        System.out.println("realX : " + realX + "; realY: " + realY);
        System.out.println("zoomX : " + zoomX + "; zoomY: " + zoomY);
        double deltaX = endX - startX;
        double deltaY = endY - startY;
        if (zoomIn) {
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
        mIteration = ITERATION_COUNT;
    }


    public void zoom(int x, int y, boolean in) {
        mZooming = true;
        zoomX = x;
        zoomY = y;
        zoomIn = in;
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

    public void cancelZoom() {
        mZooming = false;
    }

    public void zoom(int screenX, int screenY) {
        zoomX = screenX;
        zoomY = screenY;
    }

    public void moveCamera(Directions directions) {
        double deltaX = (endX - startX) * mTranslateProcent;
        double deltaY = (endY - startY) * mTranslateProcent;
        switch (directions) {
            case DOWN:
                startY -= deltaY;
                endY -= deltaY;
                break;
            case UP:
                startY += deltaY;
                endY += deltaY;
                break;
            case RIGHT:
                startX += deltaX;
                endX += deltaX;
                break;
            case LEFT:
                startX -= deltaX;
                endX -= deltaX;
                break;
        }
        updateTexture();

    }
}
