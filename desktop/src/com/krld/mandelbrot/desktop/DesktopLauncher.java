package com.krld.mandelbrot.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.krld.mandelbrot.Core;

public class DesktopLauncher {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = WIDTH;
        config.height = HEIGHT;
        Core listener = new Core();
        new LwjglApplication(listener, config);
	}
}
