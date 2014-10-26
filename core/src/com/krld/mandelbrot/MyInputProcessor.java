package com.krld.mandelbrot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import static com.badlogic.gdx.Input.Keys.*;

import com.badlogic.gdx.InputProcessor;

/**
 * Created by Andrey on 7/8/2014.
 */
public class MyInputProcessor implements com.badlogic.gdx.InputProcessor {
    private final Core core;

    public MyInputProcessor(Core core) {
        this.core = core;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == PLUS) {
            core.detailUp();
        }
        if (keycode == MINUS) {
            core.detailDown();
        }
        switch (keycode) {
            case UP:
                core.moveCamera(Directions.UP);
                break;
            case DOWN:
                core.moveCamera(Directions.DOWN);
                break;
            case LEFT:
                core.moveCamera(Directions.LEFT);
                break;
            case RIGHT:
                core.moveCamera(Directions.RIGHT);
                break;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        core.zoom(screenX, Gdx.graphics.getHeight() - screenY, button == Input.Buttons.LEFT);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        core.cancelZoom();
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        core.zoom(screenX, Gdx.graphics.getHeight() - screenY);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
