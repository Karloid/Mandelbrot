package com.krld.mandelbrot;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * Created by Andrey on 7/8/2014.
 */
public class MyInputCalcer implements InputProcessor {
    private final Core core;

    public MyInputCalcer(Core core) {
        this.core = core;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.PLUS) {
            core.detailUp();
        }
        if (keycode == Input.Keys.MINUS) {
            core.detailDown();
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
        core.zoom(1280 - screenX, 720 - screenY, button == Input.Buttons.LEFT);
        core.updateTexture();
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
