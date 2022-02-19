package org.pipeman.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import org.pipeman.MCNBMM;

public class PlayCursor {
    final int noteDistance = 15;
    float deltaTime = 0;
    int oldTick = 0;

    public void draw(ShapeRenderer renderer, int xOffset, int tick, boolean playing) {
        deltaTime += Gdx.graphics.getDeltaTime();
        if (tick != oldTick || !playing) {
            oldTick = tick;
            deltaTime = 0;
        }
        float x = xOffset + (Math.max(tick, 1) - 1) * noteDistance + 307 + deltaTime * noteDistance * MCNBMM.getGui().tps;
        if (x < 290) return;
        renderer.setColor(ColorPalette.playCursor);
        Gdx.gl.glLineWidth(2);
        Vector2 start = new Vector2(x, Gdx.graphics.getHeight() - 50);
        Vector2 end = new Vector2(x, 0);
        renderer.line(start, end);
    }
}
