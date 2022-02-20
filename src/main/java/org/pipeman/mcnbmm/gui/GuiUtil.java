package org.pipeman.mcnbmm.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import org.pipeman.mcnbmm.MCNBMM;

public class GuiUtil {
    static final int yDistance = MCNBMM.sheetLineDistance;
    static final int xDistance = 221;

    public static void drawCross(ShapeRenderer renderer, int yOffset, int numberOfInstruments) {
        int y = Gdx.graphics.getHeight() - yOffset - numberOfInstruments * yDistance - 50;

        Vector2 start = new Vector2(xDistance + 32, y + 12.5f); // 36.5
        Vector2 end = new Vector2(xDistance + 32, y + 60.5f);
        renderer.line(start, end);
        start.set(xDistance + 8, y + 36.5f);
        end.set(xDistance + 56, y + 36.5f);
        renderer.line(start, end);
    }

    public static int coordToInstrumentIndex(Vector2 loc, int yOffset) {
        return (int) (loc.y - yOffset) / yDistance;
    }

    public static Vector2 coordToNotePos(Vector2 loc, int yOffset, int xOffset, int instrumentIndex) {
        int note = (int) ((260 - loc.y + yOffset + instrumentIndex * yDistance) / 10) - 1; // 233 > loc.y
        int tick = (int) ((loc.x - 300 - xOffset) / 15);
        return new Vector2(tick, note);
    }
}
