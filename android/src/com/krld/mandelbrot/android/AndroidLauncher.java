package com.krld.mandelbrot.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.krld.mandelbrot.Core;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        Core listener1 = new Core();
        listener1.setWidth(1280);
        listener1.setHeight(720);
        initialize(listener1, config);
	}
}
