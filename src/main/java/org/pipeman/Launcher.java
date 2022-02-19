package org.pipeman;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Launcher extends ApplicationAdapter {
    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "MCNBMM";
        config.height = 480;
        config.width = 800;
//        config.vSyncEnabled = false;
//        config.foregroundFPS = -1;
//        config.foregroundFPS = 2;
        config.backgroundFPS = 10;
        new LwjglApplication(new MCNBMM(), config);
    }
}
