package org.pipeman.mcnbmm;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import org.pipeman.mcnbmm.gui.GUI;

public class MCNBMM extends ApplicationAdapter {
    SpriteBatch batch;
    Texture test;
    private static GUI gui;
    InputMultiplexer inputMultiplexer;
    OrthographicCamera camera;
    public static final int sheetLineDistance = 300;

    @Override
    public void create() {
        batch = new SpriteBatch();
        test = new Texture("test.png");
        gui = new GUI();
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(new InputProcessor());
        Gdx.input.setInputProcessor(inputMultiplexer);

        camera = new OrthographicCamera();
    }

    @Override
    public void resize (int width, int height) {
        camera.setToOrtho(false, width, height);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.WHITE);
        gui.draw(batch);
    }

    @Override
    public void dispose() {
        gui.dispose();
    }

    public static GUI getGui() {
        return gui;
    }
}
